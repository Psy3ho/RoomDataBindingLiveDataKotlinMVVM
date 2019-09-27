package com.example.roomdatabindinglivedatakotlinmvvm.viewmodel

import android.app.Application
import android.os.AsyncTask
import androidx.annotation.UiThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.roomdatabindinglivedatakotlinmvvm.db.AppDatabase
import com.example.roomdatabindinglivedatakotlinmvvm.db.entity.BorrowModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddBorrowItemViewModel(application: Application) : AndroidViewModel(application) {
    var itemName = MutableLiveData<String>()
    var personName = MutableLiveData<String>()

    var appDatabase: AppDatabase

    private var borrowModelMutableLiveData: MutableLiveData<BorrowModel>? = null

    val borrowModel: MutableLiveData<BorrowModel>
        get() {
            if (borrowModelMutableLiveData == null) {
                borrowModelMutableLiveData = MutableLiveData()
            }
            return borrowModelMutableLiveData!!
        }

    init {

        appDatabase = AppDatabase.getDatabase(this.getApplication())!!

    }

    fun addNewItemInDB() {

        viewModelScope.launch(Dispatchers.IO) {
            addCoroutine()
        }
    }

    fun addCoroutine() {
        val borrowModel = BorrowModel(itemName.value, personName.value)
        appDatabase.itemAndPersonelModel().addBorrow(borrowModel)
    }

//
//    private class addAsyncTask internal constructor(private val db: AppDatabase) : AsyncTask<BorrowModel, Void, Void>() {
//
//        override fun doInBackground(vararg params: BorrowModel): Void? {
//            db.itemAndPersonelModel().addBorrow(params[0])
//            return null
//        }
//    }


}
