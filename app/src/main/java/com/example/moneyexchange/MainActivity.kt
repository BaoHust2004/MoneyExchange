package com.example.moneyexchange

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var etSourceAmount: EditText
    private lateinit var etTargetAmount: EditText
    private lateinit var spSourceCurrency: Spinner
    private lateinit var spTargetCurrency: Spinner

    
    private val exchangeRates = mapOf(
        "USD" to 1.0,
        "EUR" to 0.85,
        "JPY" to 110.0,
        "VND" to 24000.0,
        "GBP" to 0.77
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etSourceAmount = findViewById(R.id.etSourceAmount)
        etTargetAmount = findViewById(R.id.etTargetAmount)
        spSourceCurrency = findViewById(R.id.spSourceCurrency)
        spTargetCurrency = findViewById(R.id.spTargetCurrency)

        // Thiết lập spinner với danh sách các đồng tiền
        setupSpinner()
    }

    private fun setupSpinner() {

        val currencyList = exchangeRates.keys.toList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencyList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spSourceCurrency.adapter = adapter
        spTargetCurrency.adapter = adapter


        setupListeners()
    }

    private fun setupListeners() {

        etSourceAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                convertCurrency()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })


        spSourceCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                convertCurrency()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }


        spTargetCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                convertCurrency()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun convertCurrency() {
        val sourceCurrency = spSourceCurrency.selectedItem.toString()
        val targetCurrency = spTargetCurrency.selectedItem.toString()
        val sourceAmountText = etSourceAmount.text.toString()

        
        if (sourceAmountText.isNotEmpty()) {
            val sourceAmount = sourceAmountText.toDouble()
            val sourceRate = exchangeRates[sourceCurrency] ?: 1.0
            val targetRate = exchangeRates[targetCurrency] ?: 1.0
            val targetAmount = sourceAmount * (targetRate / sourceRate)
            etTargetAmount.setText(String.format("%.2f", targetAmount))
        } else {
            etTargetAmount.setText("")
        }
    }
}
