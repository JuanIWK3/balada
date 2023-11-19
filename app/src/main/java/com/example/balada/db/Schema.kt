package com.example.balada.db

import android.provider.BaseColumns

object BalanceContract {
    object BalanceEntry: BaseColumns {
        const val TABLE_NAME = "balada"
        const val COLUMN_ID = "id"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_AMOUNT = "amount"
        const val COLUMN_DATE = "date"
        const val COLUMN_TYPE = "type"
    }
}

const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${BalanceContract.BalanceEntry.TABLE_NAME}"

val SQL_CREATE_ENTRIES = """
        CREATE TABLE ${BalanceContract.BalanceEntry.TABLE_NAME} (
            ${BalanceContract.BalanceEntry.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${BalanceContract.BalanceEntry.COLUMN_DESCRIPTION} TEXT,
            ${BalanceContract.BalanceEntry.COLUMN_AMOUNT} REAL,
            ${BalanceContract.BalanceEntry.COLUMN_DATE} TEXT,
            ${BalanceContract.BalanceEntry.COLUMN_TYPE} TEXT
        )
    """.trimIndent()



