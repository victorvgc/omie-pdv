package com.victorvgc.core.data.firebase

object FirestoreConstants {

    const val COLLECTION_CLIENT = "client"
    const val COLLECTION_PRODUCT = "product"
    const val COLLECTION_ORDER = "order"

    const val FIELD_CLIENT_NAME = "name"

    const val FIELD_PRODUCT_NAME = "name"
    const val FIELD_PRODUCT_UNIT_PRICE = "unit_price"

    const val FIELD_ORDER_ID = "id"
    const val FIELD_ORDER_CLIENT = "client_name"
    const val FIELD_ORDER_CREATED_AT = "created_at"
    const val FIELD_ORDER_PRODUCTS_LIST = "products"
    const val FIELD_ORDER_PRODUCTS_LIST_NAME = "product_name"
    const val FIELD_ORDER_PRODUCTS_LIST_UNIT_PRICE = "product_price"
    const val FIELD_ORDER_PRODUCTS_LIST_QUANTITY = "product_quantity"
}
