package com.codepoint.villagefarms

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import java.lang.Double.parseDouble
import com.codepoint.villagefarms.models.Advance
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*
import java.text.SimpleDateFormat
import java.util.*
import android.R.attr.checked
import android.support.v4.app.SupportActivity
import android.support.v4.app.SupportActivity.ExtraData
import android.support.v4.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class FarmerAddAdvanceActivity : AppCompatActivity() {

    // Declare objectId as global variable
    private var objectId:String? = null

    // Declare Views
    lateinit var rgAdvanceType: RadioGroup
    lateinit var rbCash: RadioButton
    lateinit var rbCommodity: RadioButton

    lateinit var txtAdvanceAmount: EditText

    lateinit var rgCommodityAdvanced: RadioGroup
    lateinit var rbChemicals: RadioButton
    lateinit var rbFertilizer: RadioButton
    lateinit var rbPolytheneTubes: RadioButton
    lateinit var rbSeedlings: RadioButton

    lateinit var txtCommodityValue: EditText

    lateinit var rgPaymentMode: RadioGroup
    lateinit var rbPayCash: RadioButton
    lateinit var rbPayCoffee: RadioButton

    lateinit var txtPricePriceKg: EditText
    lateinit var txtTotalCoffeeWeight: EditText

    lateinit var btnSaveAdvance: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_farmer_add_advance)

        // Get data from intent
        val intent = intent
        objectId = intent.getStringExtra("objectId")

        //to change title of activity programmatically to full name
        val actionBar = supportActionBar
        actionBar!!.title = ("Create Advance")

        //set back button
        actionBar.setDisplayHomeAsUpEnabled(true)

        // Initialize Views
        rgAdvanceType = findViewById(R.id.rg_advance_type)
        rbCash = findViewById(R.id.rb_cash)
        rbCommodity = findViewById(R.id.rb_commodity)

        txtAdvanceAmount = findViewById(R.id.txtAdvanceAmount)

        rgCommodityAdvanced = findViewById(R.id.rg_commodity_advanced)
        rbChemicals = findViewById(R.id.rb_chemicals)
        rbFertilizer = findViewById(R.id.rb_fertilizer)
        rbPolytheneTubes = findViewById(R.id.rb_polythene_tubes)
        rbSeedlings = findViewById(R.id.rb_seedlings)

        txtCommodityValue = findViewById(R.id.txtCommodityValue)

        rgPaymentMode = findViewById(R.id.rg_payment_mode)
        rbPayCash = findViewById(R.id.rb_pay_cash)
        rbPayCoffee = findViewById(R.id.rb_pay_coffee)

        txtPricePriceKg = findViewById(R.id.txtPricePriceKg)
        txtTotalCoffeeWeight = findViewById(R.id.txtTotalCoffeeWeight)

        btnSaveAdvance = findViewById(R.id.btnSaveAdvance)


        /***************** Advance type radio group event listener**********************/
        var advanceType = ""
        rgAdvanceType.setOnCheckedChangeListener { _, _ ->
            if (rgAdvanceType.checkedRadioButtonId != -1) {
                if (rbCash.isChecked) {

                    // Clear corresponding fields based on selection before proceeding
                    txtAdvanceAmount.setText("")
                    rgCommodityAdvanced.clearCheck()
                    txtCommodityValue.setText("")


                    // if advanceType == Cash, disable commodityAdvanced(loop through radio group children) & commodityValue
                    for (i in 0 until rgCommodityAdvanced.childCount) {
                        (rgCommodityAdvanced.getChildAt(i) as RadioButton).isClickable = false
                    }

                    // Disable advanceAmount
                    txtAdvanceAmount.isFocusable = true
                    txtAdvanceAmount.isFocusableInTouchMode = true

                    txtCommodityValue.isFocusable = false
                    txtCommodityValue.isFocusableInTouchMode = false

                    // Set advanceType to 'Cash' value
                    advanceType = "Cash"

                    // Configure/ Display error message
                    rbCommodity.error = null

                } else if (rbCommodity.isChecked) {

                    // Clear corresponding fields based on selection before proceeding
                    txtAdvanceAmount.setText("")
                    rgCommodityAdvanced.clearCheck()
                    txtCommodityValue.setText("")

                    // if advanceType == Commodity, enable commodityAdvanced(loop through radio group children) & commodityValue
                    for (i in 0 until rgCommodityAdvanced.childCount) {
                        (rgCommodityAdvanced.getChildAt(i) as RadioButton).isClickable = true
                    }

                    txtCommodityValue.isFocusable = true
                    txtCommodityValue.isFocusableInTouchMode = true

                    // Disable advanceAmount
                    txtAdvanceAmount.isFocusable = false
                    txtAdvanceAmount.isFocusableInTouchMode = false

                    // Set advanceType to 'Commodity' value
                    advanceType = "Commodity"

                    // Configure/ Display error message
                    rbCommodity.error = null

                } else if (rgAdvanceType.checkedRadioButtonId == -1) {
                    rbCommodity.error = "Please select cash or commodity"

                }
            }
        }

        /***************** Advance type radio group event listener**********************/


        /***************** Commodity Advanced radio group event listener****************/

        var commodityAdvanced = ""
        rgCommodityAdvanced.setOnCheckedChangeListener { _, _ ->
            if (rgCommodityAdvanced.checkedRadioButtonId != -1) {
                if (rbChemicals.isChecked) {
                    commodityAdvanced = "Chemicals"
                    rbSeedlings.error = null
                } else if (rbFertilizer.isChecked) {
                    commodityAdvanced = "Fertilizer"
                    rbSeedlings.error = null
                } else if (rbPolytheneTubes.isChecked) {
                    commodityAdvanced = "Polythene tubes"
                    rbSeedlings.error = null
                } else if (rbSeedlings.isChecked) {
                    commodityAdvanced = "Seedlings"
                    rbSeedlings.error = null
                } else if (rgCommodityAdvanced.checkedRadioButtonId == -1) {
                    rbSeedlings.error = "Please select a commodity"

                }
            }
        }

        /***************** Commodity Advanced radio group event listener****************/

        /**************** Payment mode radio group event listener**********************/

        var paymentMode = ""
        rgPaymentMode.setOnCheckedChangeListener { _, _ ->
            if (rgPaymentMode.checkedRadioButtonId != -1) {
                if (rbPayCash.isChecked) {
                    paymentMode = "Cash"
                    rbPayCoffee.error = null
                } else if (rbPayCoffee.isChecked) {
                    paymentMode = "Coffee"
                    rbPayCoffee.error = null
                } else if (rgPaymentMode.checkedRadioButtonId == -1) {
                    rbPayCoffee.error = "Please select cash or coffee"

                }
            }
        }


        /*************** Payment mode radio group event listener***********************/

        // Attach a click listener to save button
        btnSaveAdvance.setOnClickListener {


            saveAdvance(advanceType, commodityAdvanced, paymentMode)

        }
    }

    private fun saveAdvance(advanceType:String, commodityAdvanced: String, paymentMode: String ){

        // Capture datetime when expense was created and store in created
        val sdf = SimpleDateFormat("yyyy-MM-dd, hh:mm:ss")
        val created = sdf.format(Date())

        // Implement Number format exception in try catch blocks to avoid app crashing
        val advanceAmount: Double? = try {
            parseDouble(txtAdvanceAmount.text.toString())
        } catch (e: NumberFormatException) {
            null
        }

        val commodityValue: Double? = try {
            parseDouble(txtCommodityValue.text.toString())
        } catch (e: NumberFormatException) {
            null
        }

        val pricePerKg: Double? = try {
            parseDouble(txtPricePriceKg.text.toString())
        } catch (e: NumberFormatException) {
            null
        }

        val totalCoffeeWeight: Double? = try {
            parseDouble(txtTotalCoffeeWeight.text.toString())
        } catch (e: NumberFormatException) {
            null
        }

        // Validate advance form before saving to Firebase database
        if (rgAdvanceType.checkedRadioButtonId == -1) {
            // no radio buttons are checked
            rbCommodity.error = "Please select cash or commodity"
            return
        }
        else if (advanceAmount == null) {
            txtAdvanceAmount.error = "Please enter a digit"
            return
        } else if (rgCommodityAdvanced.checkedRadioButtonId == -1) {
            rbSeedlings.error = "Please select a commodity"
            return
        } else if (commodityValue == null) {
            txtCommodityValue.error = "Please enter a digit"
            return
        } else if (rgPaymentMode.checkedRadioButtonId == -1) {
            // no radio buttons are checked
            rbPayCoffee.error = "Please select cash or coffee"
            return
        } else if (pricePerKg == null){
            txtPricePriceKg.error = "Please enter a digit"
            return
        } else {
            // Instantiate new Advance using Advance data class model
            val advance = Advance(
                advanceType,
                advanceAmount,
                commodityAdvanced,
                commodityValue,
                paymentMode,
                pricePerKg,
                totalCoffeeWeight,
                created,
                ""
            )

            val ref = FirebaseDatabase.getInstance().getReference("advances/$objectId")

            // Push the data to Fire base cloud data store
            ref.push().setValue(advance)


            // Clear registration form after saving advance
            txtAdvanceAmount.setText("")
            txtCommodityValue.setText("")
            txtPricePriceKg.setText("")
            txtTotalCoffeeWeight.setText("")

            rgAdvanceType.clearCheck()
            rgCommodityAdvanced.clearCheck()
            rgPaymentMode.clearCheck()


            // Display response message after saving farmer
            Snackbar.make(scroll_layout, "Advance was successfully saved", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()

        }

    }

    // Back arrow click event to go back to the parent Activity
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
