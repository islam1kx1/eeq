package com.example.kursath

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "MotoDB.db"
        private const val DATABASE_VERSION = 1

        // Users table
        const val TABLE_USERS = "users"
        const val COL_USER_ID = "id"
        const val COL_USERNAME = "username"
        const val COL_PASSWORD = "password"

        // ModelLines table
        const val TABLE_MODELS = "ModelLines"
        const val COL_MODEL_ID = "ModelID"
        const val COL_NAME = "Name"
        const val COL_TYPE = "Type"
        const val COL_SPECS = "Specifications"
        const val COL_PRICE = "Price"

        // Dealers table
        const val TABLE_DEALERS = "Dealers"
        const val COL_DEALER_ID = "DealerID"
        const val COL_REGION = "Region"
        const val COL_PHONE = "Phone"
        const val COL_CONTACT = "ContactPerson"

        // Shipments table
        const val TABLE_SHIPMENTS = "Shipments"
        const val COL_SHIPMENT_ID = "ShipmentID"
        const val COL_MODEL_ID_FK = "ModelID"
        const val COL_DEALER_ID_FK = "DealerID"
        const val COL_QUANTITY = "Quantity"
        const val COL_DATE = "Date"
    }

    override fun onCreate(db: SQLiteDatabase) {
        try {
            // Users
            val createUsers = """
                CREATE TABLE $TABLE_USERS (
                    $COL_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                    $COL_USERNAME TEXT UNIQUE,
                    $COL_PASSWORD TEXT
                )
            """.trimIndent()
            db.execSQL(createUsers)

            // ModelLines
            val createModels = """
                CREATE TABLE $TABLE_MODELS (
                    $COL_MODEL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                    $COL_NAME TEXT,
                    $COL_TYPE TEXT,
                    $COL_SPECS TEXT,
                    $COL_PRICE REAL
                )
            """.trimIndent()
            db.execSQL(createModels)

            // Dealers
            val createDealers = """
                CREATE TABLE $TABLE_DEALERS (
                    $COL_DEALER_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                    $COL_NAME TEXT,
                    $COL_REGION TEXT,
                    $COL_PHONE TEXT,
                    $COL_CONTACT TEXT
                )
            """.trimIndent()
            db.execSQL(createDealers)

            // Shipments
            val createShipments = """
                CREATE TABLE $TABLE_SHIPMENTS (
                    $COL_SHIPMENT_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                    $COL_MODEL_ID_FK INTEGER,
                    $COL_DEALER_ID_FK INTEGER,
                    $COL_QUANTITY INTEGER,
                    $COL_DATE TEXT,
                    FOREIGN KEY($COL_MODEL_ID_FK) REFERENCES $TABLE_MODELS($COL_MODEL_ID),
                    FOREIGN KEY($COL_DEALER_ID_FK) REFERENCES $TABLE_DEALERS($COL_DEALER_ID)
                )
            """.trimIndent()
            db.execSQL(createShipments)

            // Insert default data
            insertDefaultData(db)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun insertDefaultData(db: SQLiteDatabase) {
        try {
            // Insert ModelLines
            val models = arrayOf(
                arrayOf("Shadow RS", "Sport Touring", "V-twin 1200cc, 105 hp, ABS, 6-speed", "1850000"),
                arrayOf("Crusier X9", "Cruiser", "V-twin 1800cc, 85 hp, air cooling, leather bags", "2100000"),
                arrayOf("Enduro Pro", "Enduro", "Single 450cc, 55 hp, electric start, off-road", "890000"),
                arrayOf("City Sprint", "Scooter", "250cc, 22 hp, CVT, 3L/100km", "450000"),
                arrayOf("Naked Beast", "Naked", "800cc, 110 hp, minimalist design", "1250000"),
                arrayOf("Adventure X", "Adventure", "1000cc, 95 hp, 25L tank", "1680000"),
                arrayOf("Sport R", "Sportbike", "600cc, 120 hp, fairing", "1450000")
            )
            for (model in models) {
                val values = ContentValues().apply {
                    put(COL_NAME, model[0])
                    put(COL_TYPE, model[1])
                    put(COL_SPECS, model[2])
                    put(COL_PRICE, model[3].toDouble())
                }
                db.insert(TABLE_MODELS, null, values)
            }

            // Insert Dealers
            val dealers = arrayOf(
                arrayOf("Moto-Mir", "Moscow", "+7 (495) 123-45-67", "Ivan Ivanov"),
                arrayOf("Bike Center", "Saint Petersburg", "+7 (812) 234-56-78", "Petr Petrov"),
                arrayOf("Sibir Moto", "Novosibirsk", "+7 (383) 345-67-89", "Alexey Sidorov"),
                arrayOf("Uralbike", "Ekaterinburg", "+7 (343) 456-78-90", "Dmitry Kuznetsov"),
                arrayOf("Yug Moto", "Krasnodar", "+7 (861) 567-89-01", "Mikhail Mikhailov"),
                arrayOf("DV-Moto", "Vladivostok", "+7 (423) 678-90-12", "Sergey Sokolov"),
                arrayOf("Kazan Bike", "Kazan", "+7 (843) 789-01-23", "Nikolay Nikolaev")
            )
            for (dealer in dealers) {
                val values = ContentValues().apply {
                    put(COL_NAME, dealer[0])
                    put(COL_REGION, dealer[1])
                    put(COL_PHONE, dealer[2])
                    put(COL_CONTACT, dealer[3])
                }
                db.insert(TABLE_DEALERS, null, values)
            }

            // Insert Shipments
            val shipments = arrayOf(
                arrayOf("1", "1", "5", "2025-01-15"),
                arrayOf("2", "1", "3", "2025-01-15"),
                arrayOf("3", "2", "7", "2025-01-20"),
                arrayOf("5", "3", "4", "2025-01-25"),
                arrayOf("6", "4", "2", "2025-01-28"),
                arrayOf("1", "5", "6", "2025-02-01"),
                arrayOf("7", "1", "4", "2025-02-05"),
                arrayOf("4", "7", "10", "2025-02-10"),
                arrayOf("2", "6", "2", "2025-02-12"),
                arrayOf("3", "3", "5", "2025-02-15"),
                arrayOf("5", "2", "3", "2025-02-18"),
                arrayOf("6", "5", "3", "2025-02-20"),
                arrayOf("1", "4", "4", "2025-02-22"),
                arrayOf("7", "7", "2", "2025-02-25"),
                arrayOf("4", "1", "8", "2025-02-28")
            )
            for (shipment in shipments) {
                val values = ContentValues().apply {
                    put(COL_MODEL_ID_FK, shipment[0].toInt())
                    put(COL_DEALER_ID_FK, shipment[1].toInt())
                    put(COL_QUANTITY, shipment[2].toInt())
                    put(COL_DATE, shipment[3])
                }
                db.insert(TABLE_SHIPMENTS, null, values)
            }

            // Insert default user
            val userValues = ContentValues().apply {
                put(COL_USERNAME, "admin")
                put(COL_PASSWORD, "123456")
            }
            db.insert(TABLE_USERS, null, userValues)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_MODELS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_DEALERS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_SHIPMENTS")
        onCreate(db)
    }

    // ==================== AUTH ====================
    fun checkUser(username: String, password: String): Boolean {
        try {
            val db = readableDatabase
            val cursor = db.query(
                TABLE_USERS,
                arrayOf(COL_USER_ID),
                "$COL_USERNAME=? AND $COL_PASSWORD=?",
                arrayOf(username, password),
                null, null, null
            )
            val result = cursor.count > 0
            cursor.close()
            return result
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    fun registerUser(username: String, password: String): Boolean {
        try {
            val db = writableDatabase
            val values = ContentValues().apply {
                put(COL_USERNAME, username)
                put(COL_PASSWORD, password)
            }
            val result = db.insert(TABLE_USERS, null, values)
            return result != -1L
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    // ==================== READ ====================
    fun getModels(): List<Model> {
        val list = mutableListOf<Model>()
        try {
            val db = readableDatabase
            val cursor = db.query(TABLE_MODELS, null, null, null, null, null, null)
            while (cursor.moveToNext()) {
                list.add(Model(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getDouble(4).toString()
                ))
            }
            cursor.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }

    fun getDealers(): List<Model> {
        val list = mutableListOf<Model>()
        try {
            val db = readableDatabase
            val cursor = db.query(TABLE_DEALERS, null, null, null, null, null, null)
            while (cursor.moveToNext()) {
                list.add(Model(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)
                ))
            }
            cursor.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }

    fun getShipments(): List<Model> {
        val list = mutableListOf<Model>()
        try {
            val db = readableDatabase
            val query = """
                SELECT s.${COL_SHIPMENT_ID}, m.${COL_NAME}, d.${COL_NAME}, s.${COL_QUANTITY}, s.${COL_DATE}
                FROM $TABLE_SHIPMENTS s
                JOIN $TABLE_MODELS m ON s.${COL_MODEL_ID_FK} = m.${COL_MODEL_ID}
                JOIN $TABLE_DEALERS d ON s.${COL_DEALER_ID_FK} = d.${COL_DEALER_ID}
            """.trimIndent()
            val cursor = db.rawQuery(query, null)
            while (cursor.moveToNext()) {
                list.add(Model(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3).toString(),
                    cursor.getString(4)
                ))
            }
            cursor.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }

    // ==================== INSERT ====================
    fun insertModel(name: String, type: String, specs: String, price: Double): Boolean {
        val values = ContentValues().apply {
            put(COL_NAME, name)
            put(COL_TYPE, type)
            put(COL_SPECS, specs)
            put(COL_PRICE, price)
        }
        return insert(TABLE_MODELS, values)
    }

    fun insertDealer(name: String, region: String, phone: String, contact: String): Boolean {
        val values = ContentValues().apply {
            put(COL_NAME, name)
            put(COL_REGION, region)
            put(COL_PHONE, phone)
            put(COL_CONTACT, contact)
        }
        return insert(TABLE_DEALERS, values)
    }

    fun insertShipment(modelId: Int, dealerId: Int, quantity: Int, date: String): Boolean {
        val values = ContentValues().apply {
            put(COL_MODEL_ID_FK, modelId)
            put(COL_DEALER_ID_FK, dealerId)
            put(COL_QUANTITY, quantity)
            put(COL_DATE, date)
        }
        return insert(TABLE_SHIPMENTS, values)
    }

    private fun insert(table: String, values: ContentValues): Boolean {
        try {
            val db = writableDatabase
            return db.insert(table, null, values) != -1L
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    // ==================== UPDATE ====================
    fun updateModel(id: Int, name: String, type: String, specs: String, price: Double): Boolean {
        val values = ContentValues().apply {
            put(COL_NAME, name)
            put(COL_TYPE, type)
            put(COL_SPECS, specs)
            put(COL_PRICE, price)
        }
        return update(TABLE_MODELS, COL_MODEL_ID, id, values)
    }

    fun updateDealer(id: Int, name: String, region: String, phone: String, contact: String): Boolean {
        val values = ContentValues().apply {
            put(COL_NAME, name)
            put(COL_REGION, region)
            put(COL_PHONE, phone)
            put(COL_CONTACT, contact)
        }
        return update(TABLE_DEALERS, COL_DEALER_ID, id, values)
    }

    fun updateShipment(id: Int, modelId: Int, dealerId: Int, quantity: Int, date: String): Boolean {
        val values = ContentValues().apply {
            put(COL_MODEL_ID_FK, modelId)
            put(COL_DEALER_ID_FK, dealerId)
            put(COL_QUANTITY, quantity)
            put(COL_DATE, date)
        }
        return update(TABLE_SHIPMENTS, COL_SHIPMENT_ID, id, values)
    }

    private fun update(table: String, idCol: String, id: Int, values: ContentValues): Boolean {
        try {
            val db = writableDatabase
            return db.update(table, values, "$idCol=?", arrayOf(id.toString())) > 0
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    // ==================== DELETE ====================
    fun deleteModel(id: Int): Boolean = delete(TABLE_MODELS, COL_MODEL_ID, id)
    fun deleteDealer(id: Int): Boolean = delete(TABLE_DEALERS, COL_DEALER_ID, id)
    fun deleteShipment(id: Int): Boolean = delete(TABLE_SHIPMENTS, COL_SHIPMENT_ID, id)

    private fun delete(table: String, idCol: String, id: Int): Boolean {
        try {
            val db = writableDatabase
            return db.delete(table, "$idCol=?", arrayOf(id.toString())) > 0
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }
}