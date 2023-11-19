package com.example.balada.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

class Repository (var context: Context) {
    var db: Database = Database(this.context)
    var connection: SQLiteDatabase = this.db.writableDatabase

    fun getExpensesOrIncomes(option: String): Double {
        val cursor = this.connection.query(
            BalanceContract.BalanceEntry.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )

        var total = 0.0

        with(cursor) {
            while (moveToNext()) {
                val amount = getDouble(getColumnIndexOrThrow(BalanceContract.BalanceEntry.COLUMN_AMOUNT))
                val type = getString(getColumnIndexOrThrow(BalanceContract.BalanceEntry.COLUMN_TYPE))

                if (type == option) {
                    total += amount
                }
            }
        }

        return total
    }

    fun getIncomes(): Double {
        return getExpensesOrIncomes("Entrada")
    }

    fun getExpenses(): Double {
        return getExpensesOrIncomes("Saída")
    }

    fun save(text: String, amount: Double, date: String, type: String) {
        val values = ContentValues().apply {
            put(BalanceContract.BalanceEntry.COLUMN_AMOUNT, amount)
            put(BalanceContract.BalanceEntry.COLUMN_DATE, date)
            put(BalanceContract.BalanceEntry.COLUMN_TYPE, type)
            put(BalanceContract.BalanceEntry.COLUMN_DESCRIPTION, text)
        }

        val newRowId = this.connection.insert(BalanceContract.BalanceEntry.TABLE_NAME, null, values)

        if (newRowId == -1L) {
            throw Exception("Erro ao inserir dados")
        }
    }
}