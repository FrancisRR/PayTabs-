package com.sdk.paytab

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.paytabs.paytabs_sdk.payment.ui.activities.PayTabActivity
import com.paytabs.paytabs_sdk.utils.PaymentParams
import com.sdk.paytab.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {

    internal var amount: String = ""
    internal var title: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btSubmit.setOnClickListener {

            amount = edAmout.text.toString().trim()
            title = edTitle.text.toString().trim()
            val phoneNumber = edPhone.text.toString().trim()
            val street = edStreet.text.toString().trim()
            val city = edCity.text.toString().trim()
            val state = edState.text.toString().trim()
            val country = edCountry.text.toString().trim()
            val postalCode = edPostalCode.text.toString().trim()



            if (amount.isEmpty()) {
                UiUtils.showToast(resources.getString(R.string.enter_amount))
            } else if (title.isEmpty()) {
                UiUtils.showToast(resources.getString(R.string.enter_title))
            } else {
                payment()
            }

        }
    }


    private fun payment() {

        val intent = Intent(applicationContext, PayTabActivity::class.java)
        intent.putExtra(PaymentParams.MERCHANT_EMAIL, AppConstant.PayTabId)
        intent.putExtra(
            PaymentParams.SECRET_KEY,
            AppConstant.PayTabIdSecret
        )//Add your Secret Key Here
        intent.putExtra(PaymentParams.LANGUAGE, PaymentParams.ENGLISH)
        intent.putExtra(PaymentParams.TRANSACTION_TITLE, edTitle.text.toString().trim())

        val amount: Double = (edAmout.text.toString().trim()).toDouble()
        intent.putExtra(PaymentParams.AMOUNT, amount)

        intent.putExtra(PaymentParams.CURRENCY_CODE, AppConstant.CommonCurrency)
        intent.putExtra(PaymentParams.CUSTOMER_PHONE_NUMBER, "009733")
        intent.putExtra(PaymentParams.CUSTOMER_EMAIL, "customer-email@example.com")
        intent.putExtra(PaymentParams.ORDER_ID, "123456")
        intent.putExtra(PaymentParams.PRODUCT_NAME, "Product 1, Product 2")


        //Billing Address
        intent.putExtra(PaymentParams.ADDRESS_BILLING, "Flat 1,Building 123, Road 2345")
        intent.putExtra(PaymentParams.CITY_BILLING, "Manama")
        intent.putExtra(PaymentParams.STATE_BILLING, "Manama")
        intent.putExtra(PaymentParams.COUNTRY_BILLING, "BHR")
        intent.putExtra(
            PaymentParams.POSTAL_CODE_BILLING, "00973"
        ) //Put Country Phone code if Postal code not available '00973'

        //Shipping Address
        intent.putExtra(PaymentParams.ADDRESS_SHIPPING, "Flat 1,Building 123, Road 2345")
        intent.putExtra(PaymentParams.CITY_SHIPPING, "Manama")
        intent.putExtra(PaymentParams.STATE_SHIPPING, "Manama")
        intent.putExtra(PaymentParams.COUNTRY_SHIPPING, "BHR")
        intent.putExtra(
            PaymentParams.POSTAL_CODE_SHIPPING, "00973"
        ) //Put Country Phone code if Postal code not available '00973'

        //Payment Page Style
        intent.putExtra(PaymentParams.PAY_BUTTON_COLOR, "#2474bc")

        //Tokenization
        intent.putExtra(PaymentParams.IS_TOKENIZATION, true)
        startActivityForResult(intent, PaymentParams.PAYMENT_REQUEST_CODE)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == PaymentParams.PAYMENT_REQUEST_CODE) {
            Log.e("ResponseCode", data!!.getStringExtra(PaymentParams.RESPONSE_CODE))
            Log.e("TransactionId", data.getStringExtra(PaymentParams.TRANSACTION_ID))
            Log.e("ResultMessage", data.getStringExtra(PaymentParams.RESULT_MESSAGE))
            if (data.hasExtra(PaymentParams.TOKEN) && !data.getStringExtra(PaymentParams.TOKEN)!!
                    .isEmpty()
            ) {
                Log.e("Tag", data.getStringExtra(PaymentParams.TOKEN))
                Log.e("Tag", data.getStringExtra(PaymentParams.CUSTOMER_EMAIL))
                Log.e("Tag", data.getStringExtra(PaymentParams.CUSTOMER_PASSWORD))

                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra(
                    AppConstant.TRANSACTION_ID,
                    data.getStringExtra(PaymentParams.TRANSACTION_ID)
                )
                intent.putExtra(
                    AppConstant.EMAIL_ID,
                    data.getStringExtra(PaymentParams.CUSTOMER_EMAIL)
                )
                intent.putExtra(
                    AppConstant.RESULT_MESSAGE,
                    data.getStringExtra(PaymentParams.RESULT_MESSAGE)
                )
                intent.putExtra(
                    AppConstant.TITLE,
                    title
                )

                intent.putExtra(AppConstant.AMOUNT, amount)

                startActivity(intent)

            } else {
                UiUtils.showToast(resources.getString(R.string.error_msg))
            }
        }
    }
}