package com.victorvgc.core.data.data_sources

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.victorvgc.core.data.firebase.FirestoreConstants
import com.victorvgc.core.domain.data_sources.ProductDataSource
import com.victorvgc.domain.core.Product
import com.victorvgc.utils.failures.FailureCodes
import com.victorvgc.utils.result_helper.ResultWrapper
import com.victorvgc.utils.result_helper.toSuccess
import java.math.BigDecimal
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ProductDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore
) : ProductDataSource {
    override suspend fun createProduct(product: Product): ResultWrapper<Unit> =
        suspendCoroutine { continuation ->
            val voidTask =
                db.collection(FirestoreConstants.COLLECTION_PRODUCT).document(product.name).set(
                    mapOf(
                        FirestoreConstants.FIELD_PRODUCT_NAME to product.name,
                        FirestoreConstants.FIELD_PRODUCT_UNIT_PRICE to product.unitPrice.toPlainString()
                    )
                )

            voidTask.addOnCompleteListener {
                if (it.isSuccessful) {
                    continuation.resume(ResultWrapper.Success(Unit))
                } else {
                    continuation.resume(ResultWrapper.Failure(FailureCodes.FAILURE_CODE_CREATE))
                }
            }
        }

    override suspend fun deleteProduct(product: Product): ResultWrapper<Unit> =
        suspendCoroutine { continuation ->
            val voidTask =
                db.collection(FirestoreConstants.COLLECTION_PRODUCT).document(product.name).delete()

            voidTask.addOnCompleteListener {
                if (it.isSuccessful) {
                    continuation.resume(ResultWrapper.Success(Unit))
                } else {
                    continuation.resume(ResultWrapper.Failure(FailureCodes.FAILURE_CODE_DELETE))
                }
            }
        }

    override suspend fun updateProduct(product: Product): ResultWrapper<Unit> =
        suspendCoroutine { continuation ->
            val voidTask =
                db.collection(FirestoreConstants.COLLECTION_PRODUCT).document(product.name).update(
                    mapOf(
                        FirestoreConstants.FIELD_PRODUCT_UNIT_PRICE to product.unitPrice.toPlainString()
                    )
                )

            voidTask.addOnCompleteListener {
                if (it.isSuccessful) {
                    continuation.resume(ResultWrapper.Success(Unit))
                } else {
                    continuation.resume(ResultWrapper.Failure(FailureCodes.FAILURE_CODE_UPDATE))
                }
            }
        }

    override suspend fun getAllProducts(): ResultWrapper<List<Product>> =
        suspendCoroutine { continuation ->
            val snapshotTask = db.collection(FirestoreConstants.COLLECTION_PRODUCT).get()

            snapshotTask.addOnCompleteListener {
                if (it.isSuccessful.not()) {
                    continuation.resume(ResultWrapper.Failure(FailureCodes.FAILURE_CODE_READ_ALL))
                } else if (it.result.isEmpty) {
                    continuation.resume(emptyList<Product>().toSuccess())
                } else {
                    try {
                        val productList = buildList {
                            for (doc in it.result.documents) {
                                add(buildProduct(doc))
                            }
                        }

                        continuation.resume(productList.toSuccess())
                    } catch (e: Exception) {
                        continuation.resume(ResultWrapper.Failure(FailureCodes.FAILURE_CODE_READ_ALL))
                    }
                }

            }
        }

    override suspend fun getProduct(productName: String): ResultWrapper<Product> =
        suspendCoroutine { continuation ->
            val snapshotTask =
                db.collection(FirestoreConstants.COLLECTION_PRODUCT).document(productName).get()

            snapshotTask.addOnCompleteListener {
                if (it.isSuccessful) {
                    try {
                        continuation.resume(buildProduct(it.result).toSuccess())
                    } catch (e: Exception) {
                        continuation.resume(ResultWrapper.Failure(FailureCodes.FAILURE_CODE_READ))
                    }
                } else {
                    continuation.resume(ResultWrapper.Failure(FailureCodes.FAILURE_CODE_READ))
                }
            }
        }

    private fun buildProduct(doc: DocumentSnapshot): Product = Product(
        name = doc.id,
        unitPrice = BigDecimal(
            doc.getString(FirestoreConstants.FIELD_PRODUCT_UNIT_PRICE) ?: "0"
        )
    )
}
