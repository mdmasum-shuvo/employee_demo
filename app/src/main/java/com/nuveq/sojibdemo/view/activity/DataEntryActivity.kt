package com.nuveq.sojibdemo.view.activity

import android.app.Activity
import android.app.Dialog
import android.view.MenuItem
import android.view.Window
import androidx.cardview.widget.CardView
import com.nuveq.sojibdemo.R
import com.nuveq.sojibdemo.common.BaseActivity
import com.nuveq.sojibdemo.databinding.ActivityDataEntryBinding


class DataEntryActivity : BaseActivity() {

    private lateinit var binding: ActivityDataEntryBinding
    lateinit var dialog: Dialog
    override fun getLayoutResourceFile(): Int {
        return R.layout.activity_data_entry
    }

    override fun initComponent() {
        binding = getBinding() as ActivityDataEntryBinding
        initToolbar()
        setToolbarTitle("Data Entry")
        enableBackButton()
    }

    override fun initFunctionality() {
    }

    override fun initListener() {
        binding.btnAdd.setOnClickListener {
            showDialog()
        }


    }


    fun showDialog() {
        dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.product_dialuge)

        var cardView = dialog.findViewById<CardView>(R.id.btnCross)
        cardView.setOnClickListener {
            dialog.dismiss()
            showToast("Product add to list")

        }

        dialog.show()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
//            onBackPressed();
//            Snackbar.make(floatingbar,"Clicked",Snackbar.LENGTH_SHORT).show();
            //startActivity(new Intent(HomeActivity.this, HistoryActivity.class));
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}