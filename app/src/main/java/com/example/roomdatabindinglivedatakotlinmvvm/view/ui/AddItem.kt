package com.example.roomdatabindinglivedatakotlinmvvm.view.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.roomdatabindinglivedatakotlinmvvm.R
import com.example.roomdatabindinglivedatakotlinmvvm.databinding.ActivityAddItemBinding
import com.example.roomdatabindinglivedatakotlinmvvm.view.callback.BackToViewCallback
import com.example.roomdatabindinglivedatakotlinmvvm.viewmodel.AddBorrowItemViewModel

class AddItem : AppCompatActivity() {

    private var activityAddItemBinding: ActivityAddItemBinding? = null
    private var addBorrowItemViewModel: AddBorrowItemViewModel? = null

    private val backToViewCallback = object : BackToViewCallback {
        override fun onClick() {
            addBorrowItemViewModel!!.addNewItemInDB()
            onBackPressed()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addBorrowItemViewModel = ViewModelProviders.of(this).get(AddBorrowItemViewModel::class.java)

        activityAddItemBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_item)


        activityAddItemBinding!!.addBorrowItemViewModel = addBorrowItemViewModel
        activityAddItemBinding!!.callback = backToViewCallback

        addBorrowItemViewModel!!.borrowModel.observe(this, Observer{ borrowModel ->
            activityAddItemBinding!!.itemNameEditText.setText(borrowModel.itemName)
            activityAddItemBinding!!.personNameEditText.setText(borrowModel.personName)
        })


    }
}
