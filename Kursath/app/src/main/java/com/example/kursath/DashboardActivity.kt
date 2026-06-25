package com.example.kursath

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DashboardActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Adapter
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var dialogHelper: DialogHelper
    private var dataList = mutableListOf<Model>()

    private var currentTable = "ModelLines"
    private var selectedItem: Model? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        dbHelper = DatabaseHelper(this)
        dialogHelper = DialogHelper(this, dbHelper)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = Adapter(emptyList()) { item ->
            selectedItem = item
            Toast.makeText(this, "Выбрано: ID=${item.id}", Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = adapter

        // Кнопки
        val btnModels: Button = findViewById(R.id.btnModels)
        val btnDealers: Button = findViewById(R.id.btnDealers)
        val btnShipments: Button = findViewById(R.id.btnShipments)
        val btnAdd: Button = findViewById(R.id.btnAdd)
        val btnEdit: Button = findViewById(R.id.btnEdit)
        val btnDelete: Button = findViewById(R.id.btnDelete)

        btnModels.setOnClickListener {
            currentTable = "ModelLines"
            loadData(currentTable)
            Toast.makeText(this, "Модельные линейки", Toast.LENGTH_SHORT).show()
        }

        btnDealers.setOnClickListener {
            currentTable = "Dealers"
            loadData(currentTable)
            Toast.makeText(this, "Дилеры", Toast.LENGTH_SHORT).show()
        }

        btnShipments.setOnClickListener {
            currentTable = "Shipments"
            loadData(currentTable)
            Toast.makeText(this, "Отгрузки", Toast.LENGTH_SHORT).show()
        }

        btnAdd.setOnClickListener {
            dialogHelper.showAddDialog(currentTable) {
                loadData(currentTable)
            }
        }

        btnEdit.setOnClickListener {
            if (selectedItem == null) {
                Toast.makeText(this, "Сначала выберите запись", Toast.LENGTH_SHORT).show()
            } else {
                dialogHelper.showEditDialog(currentTable, selectedItem!!) {
                    loadData(currentTable)
                    selectedItem = null
                }
            }
        }

        btnDelete.setOnClickListener {
            if (selectedItem == null) {
                Toast.makeText(this, "Сначала выберите запись", Toast.LENGTH_SHORT).show()
            } else {
                confirmDelete()
            }
        }

        loadData(currentTable)
    }

    private fun loadData(table: String) {
        dataList = when (table) {
            "ModelLines" -> dbHelper.getModels().toMutableList()
            "Dealers" -> dbHelper.getDealers().toMutableList()
            "Shipments" -> dbHelper.getShipments().toMutableList()
            else -> mutableListOf()
        }
        adapter.updateData(dataList)
        selectedItem = null
    }

    private fun confirmDelete() {
        // Получаем название записи
        val itemName = when (currentTable) {
            "ModelLines" -> selectedItem?.col1 ?: "запись"
            "Dealers" -> selectedItem?.col1 ?: "запись"
            "Shipments" -> "${selectedItem?.col1} → ${selectedItem?.col2}" ?: "запись"
            else -> "запись"
        }

        AlertDialog.Builder(this)
            .setTitle("Удалить запись?")
            .setMessage("Вы уверены, что хотите удалить \"$itemName\"?")
            .setPositiveButton("Да") { _, _ ->
                selectedItem?.let {
                    val result = when (currentTable) {
                        "ModelLines" -> dbHelper.deleteModel(it.id)
                        "Dealers" -> dbHelper.deleteDealer(it.id)
                        "Shipments" -> dbHelper.deleteShipment(it.id)
                        else -> false
                    }
                    if (result) {
                        Toast.makeText(this, "Запись удалена", Toast.LENGTH_SHORT).show()
                        selectedItem = null
                        loadData(currentTable)
                    } else {
                        Toast.makeText(this, "Ошибка удаления", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .setNegativeButton("Нет", null)
            .show()
    }
}