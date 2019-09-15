package com.codepoint.villagefarms

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import com.codepoint.villagefarms.models.Sale
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*
import java.text.SimpleDateFormat
import java.util.*

class SalesActivity : AppCompatActivity() {

    // Declare Views
    lateinit var txtSaleFname: EditText
    lateinit var txtSaleLname: EditText
    lateinit var txtSaleLAddress: EditText

    lateinit var spGoodsPurchased: Spinner
    lateinit var txtSalePhone: EditText

    lateinit var txtSaleUnitPrice: EditText
    lateinit var txtSaleQty: EditText

    lateinit var txtSaleTotalPrice: EditText

    lateinit var btnSaveSale: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sales)

        //actionbar
        val actionbar = supportActionBar

        //set back button
        actionbar!!.setDisplayHomeAsUpEnabled(true)

        // Initialize Views
        txtSaleFname = findViewById(R.id.txtSaleFname)
        txtSaleLname = findViewById(R.id.txtSaleLname)
        txtSaleLAddress = findViewById(R.id.txtSaleLAddress)

        spGoodsPurchased = findViewById(R.id.spGoodsPurchased)


        txtSaleUnitPrice = findViewById(R.id.txtSaleUnitPrice)
        txtSaleQty = findViewById(R.id.txtSaleQty)
        txtSaleTotalPrice = findViewById(R.id.txtSaleTotalPrice)

        txtSalePhone = findViewById(R.id.txtSalePhone)

        /***************** Goods purchased options Spinner ****************/

        // Initializing a String Array
        val goodsPurchasedList = resources.getStringArray(R.array.goodsPurchasedList)

        // Initializing an ArrayAdapter for goods purchased
        var goodsPurchasedAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, goodsPurchasedList)

        // Finally, data bind the spinner object with adapter
        spGoodsPurchased.setAdapter(goodsPurchasedAdapter)

        spGoodsPurchased.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Set value for goods purchased
                spGoodsPurchased.setSelection(position)
            }
        }

        /***************** Goods purchased options Spinner ****************/


        /***************** Dynamically setting total price ****************/

        txtSaleUnitPrice.addTextChangedListener(object :
            TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                var num1 = 0
                var num2 = 0

                try {
                    num1 = Integer.parseInt(txtSaleUnitPrice.text.toString())
                } catch (e: NumberFormatException) {
                    null
                }


                try {
                    num2 = Integer.parseInt(txtSaleQty.text.toString())
                } catch (e: NumberFormatException) {
                    null
                }

                val result = num1 * num2

                txtSaleTotalPrice.setText(java.lang.String.format("%d", result))

            }

        })

        txtSaleQty.addTextChangedListener(object :
            TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                var num3 = 0
                var num4 = 0

                try {
                    num3 = Integer.parseInt(txtSaleUnitPrice.text.toString())
                } catch (e: NumberFormatException) {
                    null
                }


                try {
                    num4 = Integer.parseInt(txtSaleQty.text.toString())
                } catch (e: NumberFormatException) {
                    null
                }

                val result = num3 * num4

                txtSaleTotalPrice.setText(java.lang.String.format("%d", result))

            }

        })

        /***************** Dynamically setting total price ****************/

        btnSaveSale = findViewById(R.id.btnSaveSale)

        btnSaveSale.setOnClickListener {
            saveSale()
        }
    }

    private fun saveSale(

    ) {
        val firstname = txtSaleFname.text.toString().toLowerCase().capitalize().trim()
        val lastname = txtSaleLname.text.toString().toLowerCase().capitalize().trim()
        val address = txtSaleLAddress.text.toString().toLowerCase().capitalize().trim()
        val phone = txtSalePhone.text.toString().trim()
        val goodsPurchased = spGoodsPurchased.selectedItem.toString().capitalize().trim()


        // Capture datetime when expense was created and store in created
        val sdf = SimpleDateFormat("dd/M/yyyy, hh:mm:ss")
        val created = sdf.format(Date())

        /*** Implement Number format exception in try catch blocks to avoid app crashing ******/

        val unitPrice: Int? = try {
            Integer.parseInt(txtSaleUnitPrice.text.toString())
        } catch (e: NumberFormatException) {
            null
        }

        val quantity: Int? = try {
            Integer.parseInt(txtSaleQty.text.toString())
        } catch (e: NumberFormatException) {
            null
        }

        val totalPrice: Int? = try {
            Integer.parseInt(txtSaleTotalPrice.text.toString())
        } catch (e: NumberFormatException) {
            null
        }

        /*** Implement Number format exception in try catch blocks to avoid app crashing ******/


        // Validate registration form before saving to Firebase database
        if (firstname.isEmpty()) {
            txtSaleFname.error = "Please enter a first name"
            return
        } else if (lastname.isEmpty()) {
            txtSaleLname.error = "Please enter a last name"
            return
        } else if (address.isEmpty()) {
            txtSaleLAddress.error = "Please enter an address"
            return
        } else if (phone.isEmpty()) {
            txtSalePhone.error = "Please enter a phone number"
            return
        } else if (unitPrice == null) {
            txtSaleUnitPrice.error = "Please enter a digit"
            return
        } else if (quantity == null) {
            txtSaleQty.error = "Please enter a digit"
            return
        } else if (totalPrice == null) {
            txtSaleTotalPrice.error = "Please enter a digit"
            return
        } else {
            // Instantiate new sale using sale data class model
            val sale = Sale(
                firstname,
                lastname,
                address,
                phone,
                goodsPurchased,
                unitPrice,
                quantity,
                totalPrice,
                created
            )



            val ref = FirebaseDatabase.getInstance().getReference("sales")

            // Push the data to Fire base cloud data store
            ref.push().setValue(sale)

            // Clear sales form after saving sale record
            txtSaleFname.setText("")
            txtSaleLname.setText("")
            txtSaleLAddress.setText("")
            txtSalePhone.setText("")
            txtSaleUnitPrice.setText("")
            txtSaleQty.setText("")
            txtSaleTotalPrice.setText("")

            // Display response message after saving farmer
            Snackbar.make(scroll_layout, "Sale was successfully recorded", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()

            // Send SMS using Native SMS Composer
            sendSMS(phone, totalPrice, goodsPurchased)
        }

    }

    private fun sendSMS(phone: String, totalPrice: Int, goodsPurchased: String) {
        val uri = Uri.parse("smsto:${phone}")
        val intent = Intent(Intent.ACTION_SENDTO, uri)
        intent.putExtra(
            "sms_body",
            "This is to acknowledge receipt of cash amounting to MK $totalPrice for the " +
                    "purchase of ${goodsPurchased}. Thank you for your business. " +
                    "If you have any complaint about this transaction, please call " +
                    "(+265) 991 210 444 or send an email to Info@villagefarms.org."
        )
        startActivity(intent)
    }

    // Back arrow click event to go back to the parent Activity
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
