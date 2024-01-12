package com.victorvgc.core.data.data_sources

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.victorvgc.core.data.firebase.FirestoreConstants
import com.victorvgc.core.domain.data_sources.ClientDataSource
import com.victorvgc.domain.core.Client
import com.victorvgc.utils.failures.FailureCodes
import com.victorvgc.utils.result_helper.ResultWrapper
import com.victorvgc.utils.result_helper.toSuccess
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ClientDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore
) : ClientDataSource {
    override suspend fun createClient(client: Client): ResultWrapper<Unit> =
        suspendCoroutine { continuation ->
            val voidTask =
                db.collection(FirestoreConstants.COLLECTION_CLIENT).document(client.name).set(
                    mapOf(
                        FirestoreConstants.FIELD_CLIENT_NAME to client.name
                    )
                )

            voidTask.addOnCompleteListener {
                if (it.isSuccessful) {
                    continuation.resume(Unit.toSuccess())
                } else {
                    continuation.resume(ResultWrapper.Failure(FailureCodes.FAILURE_CODE_CREATE))
                }
            }
        }

    override suspend fun deleteClient(client: Client): ResultWrapper<Unit> =
        suspendCoroutine { continuation ->
            val voidTask =
                db.collection(FirestoreConstants.COLLECTION_CLIENT).document(client.name).delete()

            voidTask.addOnCompleteListener {
                if (it.isSuccessful) {
                    continuation.resume(Unit.toSuccess())
                } else {
                    continuation.resume(ResultWrapper.Failure(FailureCodes.FAILURE_CODE_DELETE))
                }
            }
        }

    override suspend fun getAllClients(): ResultWrapper<List<Client>> =
        suspendCoroutine { continuation ->
            val snapshotTask = db.collection(FirestoreConstants.COLLECTION_CLIENT).get()

            snapshotTask.addOnCompleteListener {
                if (it.isSuccessful.not()) {
                    continuation.resume(ResultWrapper.Failure(FailureCodes.FAILURE_CODE_READ_ALL))
                } else if (it.result.isEmpty) {
                    continuation.resume(emptyList<Client>().toSuccess())
                } else {
                    try {
                        val clientList = buildList {
                            for (doc in it.result.documents) {
                                add(buildClient(doc))
                            }
                        }

                        continuation.resume(clientList.toSuccess())
                    } catch (e: Exception) {
                        continuation.resume(ResultWrapper.Failure(FailureCodes.FAILURE_CODE_READ_ALL))
                    }
                }
            }

        }

    override suspend fun getClient(clientName: String): ResultWrapper<Client> =
        suspendCoroutine { continuation ->
            val snapshotTask =
                db.collection(FirestoreConstants.COLLECTION_CLIENT).document(clientName).get()

            snapshotTask.addOnCompleteListener {
                if (it.isSuccessful) {
                    try {
                        continuation.resume(buildClient(it.result).toSuccess())
                    } catch (e: Exception) {
                        continuation.resume(ResultWrapper.Failure(FailureCodes.FAILURE_CODE_READ))
                    }
                } else {
                    continuation.resume(ResultWrapper.Failure(FailureCodes.FAILURE_CODE_READ))
                }
            }
        }

    private fun buildClient(doc: DocumentSnapshot): Client = Client(
        name = doc.id
    )
}
