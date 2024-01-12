package com.victorvgc.core.data.data_sources

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.victorvgc.core.data.firebase.FirestoreConstants
import com.victorvgc.core.domain.data_sources.OrderDataSource
import com.victorvgc.domain.core.Client
import com.victorvgc.domain.core.Order
import com.victorvgc.domain.core.OrderProduct
import com.victorvgc.domain.core.Product
import com.victorvgc.utils.failures.FailureCodes
import com.victorvgc.utils.result_helper.ResultWrapper
import com.victorvgc.utils.result_helper.toSuccess
import java.math.BigDecimal
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class OrderDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore
) : OrderDataSource {
    override suspend fun createOrder(order: Order): ResultWrapper<Unit> =
        suspendCoroutine { continuation ->
            val voidTask =
                db.collection(FirestoreConstants.COLLECTION_ORDER)
                    .document(order.id.toString())
                    .set(
                        mapOf(
                            FirestoreConstants.FIELD_ORDER_ID to order.id,
                            FirestoreConstants.FIELD_ORDER_CLIENT to order.client.name,
                            FirestoreConstants.FIELD_ORDER_CREATED_AT to order.createdAt,
                            FirestoreConstants.FIELD_ORDER_PRODUCTS_LIST to buildList {
                                for (orderProduct in order.productList) {
                                    add(
                                        mapOf(
                                            FirestoreConstants.FIELD_ORDER_PRODUCTS_LIST_NAME to orderProduct.product.name,
                                            FirestoreConstants.FIELD_ORDER_PRODUCTS_LIST_UNIT_PRICE to orderProduct.product.unitPrice.toPlainString(),
                                            FirestoreConstants.FIELD_ORDER_PRODUCTS_LIST_QUANTITY to orderProduct.quantity.toString(),
                                        )
                                    )
                                }
                            }
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

    override suspend fun updateOrder(order: Order): ResultWrapper<Unit> =
        suspendCoroutine { continuation ->
            val voidTask =
                db.collection(FirestoreConstants.COLLECTION_ORDER)
                    .document(order.id.toString())
                    .update(
                        mapOf(
                            FirestoreConstants.FIELD_ORDER_CLIENT to order.client.name,
                            FirestoreConstants.FIELD_ORDER_CREATED_AT to order.createdAt,
                            FirestoreConstants.FIELD_ORDER_PRODUCTS_LIST to buildList {
                                for (orderProduct in order.productList) {
                                    add(
                                        mapOf(
                                            FirestoreConstants.FIELD_ORDER_PRODUCTS_LIST_NAME to orderProduct.product.name,
                                            FirestoreConstants.FIELD_ORDER_PRODUCTS_LIST_UNIT_PRICE to orderProduct.product.unitPrice.toPlainString(),
                                            FirestoreConstants.FIELD_ORDER_PRODUCTS_LIST_QUANTITY to orderProduct.quantity.toString(),
                                        )
                                    )
                                }
                            }
                        )
                    )

            voidTask.addOnCompleteListener {
                if (it.isSuccessful) {
                    continuation.resume(Unit.toSuccess())
                } else {
                    continuation.resume(ResultWrapper.Failure(FailureCodes.FAILURE_CODE_UPDATE))
                }
            }
        }

    override suspend fun deleteOrder(order: Order): ResultWrapper<Unit> =
        suspendCoroutine { continuation ->
            val snapshotTask =
                db.collection(FirestoreConstants.COLLECTION_ORDER)
                    .document(order.id.toString())
                    .delete()

            snapshotTask.addOnCompleteListener {
                if (it.isSuccessful) {
                    continuation.resume(Unit.toSuccess())
                } else {
                    continuation.resume(ResultWrapper.Failure(FailureCodes.FAILURE_CODE_DELETE))
                }
            }
        }

    override suspend fun getAllOrders(): ResultWrapper<List<Order>> =
        suspendCoroutine { continuation ->
            val snapshotTask = db.collection(FirestoreConstants.COLLECTION_ORDER).get()

            snapshotTask.addOnCompleteListener {
                if (it.isSuccessful.not()) {
                    continuation.resume(ResultWrapper.Failure(FailureCodes.FAILURE_CODE_READ_ALL))
                } else if (it.result.isEmpty) {
                    continuation.resume(emptyList<Order>().toSuccess())
                } else {

                    try {
                        val result = buildList {
                            for (doc in it.result.documents) {
                                add(buildOrder(doc))
                            }
                        }

                        continuation.resume(result.toSuccess())
                    } catch (e: Exception) {
                        continuation.resume(ResultWrapper.Failure(FailureCodes.FAILURE_CODE_READ_ALL))
                    }
                }
            }
        }

    override suspend fun getHighestOrderId(): ResultWrapper<Order> =
        suspendCoroutine { continuation ->
            val snapshotTask = db.collection(FirestoreConstants.COLLECTION_ORDER)
                .orderBy(FirestoreConstants.FIELD_ORDER_ID, Query.Direction.DESCENDING)
                .limit(1)
                .get()

            snapshotTask.addOnCompleteListener {
                if (it.isSuccessful.not()) {
                    continuation.resume(ResultWrapper.Failure(FailureCodes.FAILURE_CODE_READ_ALL))
                } else if (it.result.isEmpty) {
                    continuation.resume(Order.Empty.toSuccess())
                } else {

                    try {
                        val result = buildOrder(
                            it.result.documents.first()
                        )

                        continuation.resume(result.toSuccess())
                    } catch (e: Exception) {
                        continuation.resume(ResultWrapper.Failure(FailureCodes.FAILURE_CODE_READ_ALL))
                    }
                }
            }
        }

    override suspend fun getOrdersFromTo(from: Long, to: Long): ResultWrapper<List<Order>> =
        suspendCoroutine { continuation ->
            val snapshotTask = db.collection(FirestoreConstants.COLLECTION_ORDER)
                .orderBy(FirestoreConstants.FIELD_ORDER_ID, Query.Direction.DESCENDING)
                .where(Filter.greaterThan(FirestoreConstants.FIELD_ORDER_CREATED_AT, from))
                .where(Filter.lessThan(FirestoreConstants.FIELD_ORDER_CREATED_AT, to))
                .get()

            snapshotTask.addOnCompleteListener {
                if (it.isSuccessful.not()) {
                    continuation.resume(ResultWrapper.Failure(FailureCodes.FAILURE_CODE_READ_ALL))
                } else if (it.result.isEmpty) {
                    continuation.resume(emptyList<Order>().toSuccess())
                } else {

                    try {
                        val result = buildList {
                            for (doc in it.result.documents) {
                                add(buildOrder(doc))
                            }
                        }

                        continuation.resume(result.toSuccess())
                    } catch (e: Exception) {
                        continuation.resume(ResultWrapper.Failure(FailureCodes.FAILURE_CODE_READ_ALL))
                    }
                }
            }
        }

    override suspend fun getOrder(orderId: Long): ResultWrapper<Order> =
        suspendCoroutine { continuation ->
            val snapshotTask =
                db.collection(FirestoreConstants.COLLECTION_ORDER).document(orderId.toString())
                    .get()

            snapshotTask.addOnCompleteListener {
                if (it.isSuccessful) {
                    try {
                        val order = buildOrder(it.result)

                        continuation.resume(order.toSuccess())
                    } catch (e: Exception) {
                        continuation.resume(ResultWrapper.Failure(FailureCodes.FAILURE_CODE_READ))
                    }
                } else {
                    continuation.resume(ResultWrapper.Failure(FailureCodes.FAILURE_CODE_READ))
                }
            }
        }

    private fun buildOrder(doc: DocumentSnapshot) = Order(
        id = doc.id.toLong(),
        client = Client(
            name = doc.getString(FirestoreConstants.FIELD_ORDER_CLIENT)
                ?: ""
        ),
        productList = buildProductsList(doc)
    )

    private fun buildProductsList(doc: DocumentSnapshot): List<OrderProduct> =
        buildList ProductListBuilder@{
            val productsMap =
                doc[FirestoreConstants.FIELD_ORDER_PRODUCTS_LIST] as? ArrayList<Map<String, String>>
                    ?: return@ProductListBuilder

            for (orderProduct in productsMap) {
                add(
                    OrderProduct(
                        product = Product(
                            name = orderProduct[FirestoreConstants.FIELD_ORDER_PRODUCTS_LIST_NAME]
                                ?: "",
                            unitPrice = BigDecimal(orderProduct[FirestoreConstants.FIELD_ORDER_PRODUCTS_LIST_UNIT_PRICE]),
                        ),
                        quantity = (orderProduct[FirestoreConstants.FIELD_ORDER_PRODUCTS_LIST_QUANTITY]
                            ?: "0").toLong()
                    )
                )
            }
        }

}
