package com.example.kursath

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.Toast

class DialogHelper(private val context: Context, private val dbHelper: DatabaseHelper) {

    private var modelNames = mutableListOf<String>()
    private var modelIds = mutableListOf<Int>()
    private var dealerNames = mutableListOf<String>()
    private var dealerIds = mutableListOf<Int>()

    private fun loadSpinnerData() {
        modelNames.clear()
        modelIds.clear()
        dealerNames.clear()
        dealerIds.clear()

        val models = dbHelper.getModels()
        for (model in models) {
            modelNames.add(model.col1)
            modelIds.add(model.id)
        }

        val dealers = dbHelper.getDealers()
        for (dealer in dealers) {
            dealerNames.add(dealer.col1)
            dealerIds.add(dealer.id)
        }
    }

    private fun setupAutoComplete(
        autoComplete: AutoCompleteTextView,
        items: List<String>
    ) {
        val adapter = ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, items)
        autoComplete.setAdapter(adapter)
        autoComplete.threshold = 1
    }

    // ==================== ДИАЛОГ ДОБАВЛЕНИЯ ====================
    fun showAddDialog(table: String, onSuccess: () -> Unit) {
        loadSpinnerData()

        val builder = AlertDialog.Builder(context)
        builder.setTitle("Добавить запись")

        val inflater = LayoutInflater.from(context)
        val view = when (table) {
            "ModelLines" -> createModelView(inflater, null)
            "Dealers" -> createDealerView(inflater, null)
            "Shipments" -> createShipmentView(inflater, null)
            else -> null
        }

        if (view == null) {
            Toast.makeText(context, "Ошибка: неизвестная таблица", Toast.LENGTH_SHORT).show()
            return
        }

        builder.setView(view)

        builder.setPositiveButton("Добавить") { _, _ ->
            val success = when (table) {
                "ModelLines" -> {
                    val name = view.findViewById<EditText>(R.id.etName).text.toString()
                    val type = view.findViewById<EditText>(R.id.etType).text.toString()
                    val specs = view.findViewById<EditText>(R.id.etSpecs).text.toString()
                    val price = view.findViewById<EditText>(R.id.etPrice).text.toString().toDoubleOrNull() ?: 0.0
                    if (name.isBlank() || type.isBlank()) {
                        Toast.makeText(context, "Заполните обязательные поля", Toast.LENGTH_SHORT).show()
                        return@setPositiveButton
                    }
                    dbHelper.insertModel(name, type, specs, price)
                }
                "Dealers" -> {
                    val name = view.findViewById<EditText>(R.id.etName).text.toString()
                    val region = view.findViewById<EditText>(R.id.etRegion).text.toString()
                    val phone = view.findViewById<EditText>(R.id.etPhone).text.toString()
                    val contact = view.findViewById<EditText>(R.id.etContact).text.toString()
                    if (name.isBlank() || region.isBlank()) {
                        Toast.makeText(context, "Заполните обязательные поля", Toast.LENGTH_SHORT).show()
                        return@setPositiveButton
                    }
                    dbHelper.insertDealer(name, region, phone, contact)
                }
                "Shipments" -> {
                    val modelAuto = view.findViewById<AutoCompleteTextView>(R.id.etModelId)
                    val dealerAuto = view.findViewById<AutoCompleteTextView>(R.id.etDealerId)
                    val quantity = view.findViewById<EditText>(R.id.etQuantity).text.toString().toIntOrNull() ?: 0
                    val date = view.findViewById<EditText>(R.id.etDate).text.toString()

                    val modelPos = modelNames.indexOf(modelAuto.text.toString())
                    val dealerPos = dealerNames.indexOf(dealerAuto.text.toString())

                    if (modelPos == -1 || dealerPos == -1) {
                        Toast.makeText(context, "Выберите модель и дилера", Toast.LENGTH_SHORT).show()
                        return@setPositiveButton
                    }

                    val modelId = modelIds[modelPos]
                    val dealerId = dealerIds[dealerPos]

                    if (modelId == 0 || dealerId == 0 || date.isBlank()) {
                        Toast.makeText(context, "Заполните все поля", Toast.LENGTH_SHORT).show()
                        return@setPositiveButton
                    }
                    dbHelper.insertShipment(modelId, dealerId, quantity, date)
                }
                else -> false
            }

            if (success) {
                Toast.makeText(context, "Запись добавлена", Toast.LENGTH_SHORT).show()
                onSuccess()
            } else {
                Toast.makeText(context, "Ошибка добавления", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("Отмена", null)
        builder.show()
    }

    // ==================== ДИАЛОГ РЕДАКТИРОВАНИЯ ====================
    fun showEditDialog(table: String, item: Model, onSuccess: () -> Unit) {
        loadSpinnerData()

        val builder = AlertDialog.Builder(context)
        builder.setTitle("Редактировать ID=${item.id}")

        val inflater = LayoutInflater.from(context)
        val view = when (table) {
            "ModelLines" -> createModelView(inflater, item)
            "Dealers" -> createDealerView(inflater, item)
            "Shipments" -> createShipmentView(inflater, item)
            else -> null
        }

        if (view == null) {
            Toast.makeText(context, "Ошибка: неизвестная таблица", Toast.LENGTH_SHORT).show()
            return
        }

        builder.setView(view)

        builder.setPositiveButton("Сохранить") { _, _ ->
            val success = when (table) {
                "ModelLines" -> {
                    val name = view.findViewById<EditText>(R.id.etName).text.toString()
                    val type = view.findViewById<EditText>(R.id.etType).text.toString()
                    val specs = view.findViewById<EditText>(R.id.etSpecs).text.toString()
                    val price = view.findViewById<EditText>(R.id.etPrice).text.toString().toDoubleOrNull() ?: 0.0
                    if (name.isBlank() || type.isBlank()) {
                        Toast.makeText(context, "Заполните обязательные поля", Toast.LENGTH_SHORT).show()
                        return@setPositiveButton
                    }
                    dbHelper.updateModel(item.id, name, type, specs, price)
                }
                "Dealers" -> {
                    val name = view.findViewById<EditText>(R.id.etName).text.toString()
                    val region = view.findViewById<EditText>(R.id.etRegion).text.toString()
                    val phone = view.findViewById<EditText>(R.id.etPhone).text.toString()
                    val contact = view.findViewById<EditText>(R.id.etContact).text.toString()
                    if (name.isBlank() || region.isBlank()) {
                        Toast.makeText(context, "Заполните обязательные поля", Toast.LENGTH_SHORT).show()
                        return@setPositiveButton
                    }
                    dbHelper.updateDealer(item.id, name, region, phone, contact)
                }
                "Shipments" -> {
                    val modelAuto = view.findViewById<AutoCompleteTextView>(R.id.etModelId)
                    val dealerAuto = view.findViewById<AutoCompleteTextView>(R.id.etDealerId)
                    val quantity = view.findViewById<EditText>(R.id.etQuantity).text.toString().toIntOrNull() ?: 0
                    val date = view.findViewById<EditText>(R.id.etDate).text.toString()

                    val modelPos = modelNames.indexOf(modelAuto.text.toString())
                    val dealerPos = dealerNames.indexOf(dealerAuto.text.toString())

                    if (modelPos == -1 || dealerPos == -1) {
                        Toast.makeText(context, "Выберите модель и дилера", Toast.LENGTH_SHORT).show()
                        return@setPositiveButton
                    }

                    val modelId = modelIds[modelPos]
                    val dealerId = dealerIds[dealerPos]

                    if (modelId == 0 || dealerId == 0 || date.isBlank()) {
                        Toast.makeText(context, "Заполните все поля", Toast.LENGTH_SHORT).show()
                        return@setPositiveButton
                    }
                    dbHelper.updateShipment(item.id, modelId, dealerId, quantity, date)
                }
                else -> false
            }

            if (success) {
                Toast.makeText(context, "Запись обновлена", Toast.LENGTH_SHORT).show()
                onSuccess()
            } else {
                Toast.makeText(context, "Ошибка обновления", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("Отмена", null)
        builder.show()
    }

    // ==================== ВИДЫ ДЛЯ МОДЕЛЕЙ ====================
    private fun createModelView(inflater: LayoutInflater, item: Model?): android.view.View {
        val view = inflater.inflate(R.layout.dialog_model, null)
        val etName = view.findViewById<EditText>(R.id.etName)
        val etType = view.findViewById<EditText>(R.id.etType)
        val etSpecs = view.findViewById<EditText>(R.id.etSpecs)
        val etPrice = view.findViewById<EditText>(R.id.etPrice)

        item?.let {
            etName.setText(it.col1)
            etType.setText(it.col2)
            etSpecs.setText(it.col3)
            etPrice.setText(it.col4)
        }

        return view
    }

    // ==================== ВИДЫ ДЛЯ ДИЛЕРОВ ====================
    private fun createDealerView(inflater: LayoutInflater, item: Model?): android.view.View {
        val view = inflater.inflate(R.layout.dialog_dealer, null)
        val etName = view.findViewById<EditText>(R.id.etName)
        val etRegion = view.findViewById<EditText>(R.id.etRegion)
        val etPhone = view.findViewById<EditText>(R.id.etPhone)
        val etContact = view.findViewById<EditText>(R.id.etContact)

        item?.let {
            etName.setText(it.col1)
            etRegion.setText(it.col2)
            etPhone.setText(it.col3)
            etContact.setText(it.col4)
        }

        return view
    }

    // ==================== ВИДЫ ДЛЯ ОТГРУЗОК ====================
    private fun createShipmentView(inflater: LayoutInflater, item: Model?): android.view.View {
        val view = inflater.inflate(R.layout.dialog_shipment, null)
        val etModelId = view.findViewById<AutoCompleteTextView>(R.id.etModelId)
        val etDealerId = view.findViewById<AutoCompleteTextView>(R.id.etDealerId)
        val etQuantity = view.findViewById<EditText>(R.id.etQuantity)
        val etDate = view.findViewById<EditText>(R.id.etDate)

        setupAutoComplete(etModelId, modelNames)
        setupAutoComplete(etDealerId, dealerNames)

        item?.let {
            etQuantity.setText(it.col3)
            etDate.setText(it.col4)
        }

        return view
    }
}