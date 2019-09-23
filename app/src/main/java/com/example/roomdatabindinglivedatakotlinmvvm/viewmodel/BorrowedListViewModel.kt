package com.example.roomdatabindinglivedatakotlinmvvm.viewmodel

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import com.example.roomdatabindinglivedatakotlinmvvm.db.AppDatabase
import com.example.roomdatabindinglivedatakotlinmvvm.db.entity.BorrowModel

class BorrowedListViewModel(application: Application) : AndroidViewModel(application) {


    private val listLiveData: MediatorLiveData<List<BorrowModel>>

    private val appDatabase: AppDatabase

    init {

        this.listLiveData = MediatorLiveData()
        this.listLiveData.value = null

        appDatabase = AppDatabase.getDatabase(this.getApplication())!!

        this.listLiveData.addSource(appDatabase.itemAndPersonelModel().allBorrowedItems, Observer<List<BorrowModel>> { this.listLiveData.setValue(it) })
    }

    fun getListLiveData(): LiveData<List<BorrowModel>> {
        return listLiveData
    }

    fun deleteItemLiveData(borrowModel: BorrowModel) {
        deleteAsyncTask(appDatabase).execute(borrowModel)
    }

    private class deleteAsyncTask(private val appDatabase: AppDatabase) : AsyncTask<BorrowModel, Void, Void>() {

        override fun doInBackground(vararg params: BorrowModel): Void? {
            this.appDatabase.itemAndPersonelModel().deleteBorrow(params[0])
            return null
        }
    }
}
