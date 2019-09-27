package com.example.roomdatabindinglivedatakotlinmvvm.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdatabindinglivedatakotlinmvvm.R
import com.example.roomdatabindinglivedatakotlinmvvm.databinding.ActivityMainBinding
import com.example.roomdatabindinglivedatakotlinmvvm.databinding.BorrowItemBinding
import com.example.roomdatabindinglivedatakotlinmvvm.db.entity.BorrowModel
import com.example.roomdatabindinglivedatakotlinmvvm.view.adapter.RecyclerViewAdapter
import com.example.roomdatabindinglivedatakotlinmvvm.view.callback.AddItemCallback
import com.example.roomdatabindinglivedatakotlinmvvm.view.callback.DeleteItemCallback
import com.example.roomdatabindinglivedatakotlinmvvm.viewmodel.BorrowedListViewModel

class MainActivity : AppCompatActivity() {

    private var recyclerViewAdapter: RecyclerViewAdapter? = null

    private var activityMainBinding: ActivityMainBinding? = null
    private var borrowedListViewModel: BorrowedListViewModel? = null
    private val mAddItemCallback = object : AddItemCallback {
        override fun onClick() {
            val intent = Intent(application, AddItem::class.java)
            startActivity(intent)
        }
    }

    private val deleteItemCallback = object : DeleteItemCallback {
        override  fun onClick(borrowModel: BorrowModel) {
            borrowedListViewModel!!.deleteItemLiveData(borrowModel)
        }
    }




    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        borrowedListViewModel = ViewModelProviders.of(this).get(BorrowedListViewModel::class.java)

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        activityMainBinding!!.recycleView.layoutManager = LinearLayoutManager(this)

        recyclerViewAdapter = RecyclerViewAdapter(deleteItemCallback)
        activityMainBinding!!.recycleView.adapter = recyclerViewAdapter
        activityMainBinding!!.root

        observe()
        observeDelete()


        activityMainBinding!!.callback = mAddItemCallback
    }

    override fun onResume() {
        super.onResume()
        observe()
    }

    private fun observe() {
        borrowedListViewModel!!.getListLiveData().observe(this, Observer{ borrowModelList ->
            if (borrowModelList != null) {
                activityMainBinding!!.deleting = true
                recyclerViewAdapter!!.setItemList(borrowModelList)
                activityMainBinding!!.executePendingBindings()
            } else {
                activityMainBinding!!.deleting = false
            }
        })
    }

    private fun observeDelete() {
        borrowedListViewModel!!.getStatusLiveDataDelete().observe(this , Observer { status ->
            status?.let {
                activityMainBinding!!.deleting = status
              /*  Toast.makeText(this,(if(status) {
                    "vsetko v poradecku"
                }else {
                    "deleting"
                }),Toast.LENGTH_LONG).show()*/
            }
        })
    }

}
