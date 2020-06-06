package com.sdk.paytab

import android.content.Intent
import android.os.Bundle
import com.sdk.paytab.base.BaseActivity
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val intent: Intent? = getIntent()

        val transactionId = intent?.getStringExtra(AppConstant.TRANSACTION_ID)
        val emailId = intent?.getStringExtra(AppConstant.EMAIL_ID)
        val amount = intent?.getStringExtra(AppConstant.AMOUNT)
        val title = intent?.getStringExtra(AppConstant.TITLE)
        val message = intent?.getStringExtra(AppConstant.RESULT_MESSAGE)


        transactionIdValue.setText("$transactionId")
        emailIdValue.setText("$emailId")
        resultMessageValue.setText("$message")
        amountValue.setText("$amount")
        titleValue.setText("$title")
    }

}