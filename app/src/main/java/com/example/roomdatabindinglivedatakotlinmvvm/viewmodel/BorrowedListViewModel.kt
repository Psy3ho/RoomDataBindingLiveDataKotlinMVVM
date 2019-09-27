package com.example.roomdatabindinglivedatakotlinmvvm.viewmodel

import android.app.Application
import android.os.AsyncTask
import android.widget.Toast
import androidx.lifecycle.*
import com.example.roomdatabindinglivedatakotlinmvvm.db.AppDatabase
import com.example.roomdatabindinglivedatakotlinmvvm.db.entity.BorrowModel
import kotlinx.coroutines.*

class BorrowedListViewModel(application: Application) : AndroidViewModel(application) {


    private val listLiveData: MediatorLiveData<List<BorrowModel>>

    private val appDatabase: AppDatabase

    val uiScope = CoroutineScope(Dispatchers.Main)

    val ioScope = CoroutineScope(Dispatchers.IO)

    private var status = MutableLiveData<Boolean?>()

    init {

        this.listLiveData = MediatorLiveData()
        this.listLiveData.value = null

        appDatabase = AppDatabase.getDatabase(this.getApplication())!!

        this.listLiveData.addSource(appDatabase.itemAndPersonelModel().allBorrowedItems, Observer<List<BorrowModel>> { this.listLiveData.setValue(it) })
    }

    fun getListLiveData(): LiveData<List<BorrowModel>> {
        return listLiveData
    }

    fun getStatusLiveDataDelete(): LiveData<Boolean?> {
        return status
    }

     fun deleteItemLiveData(borrowModel: BorrowModel) {
         uiScope.launch {

             status.value = false

             val task = ioScope.async {
                 delay(3000)
                 deleteCoroutine(borrowModel)

             }
             task.await()
             Toast.makeText(getApplication(), "item was deleted",Toast.LENGTH_LONG).show()


         }



    }

     fun  deleteCoroutine(borrowModel: BorrowModel) {

        appDatabase.itemAndPersonelModel().deleteBorrow(borrowModel)
    }

//    private class deleteAsyncTask(private val appDatabase: AppDatabase) : AsyncTask<BorrowModel, Void, Void>() {
//
//        override fun doInBackground(vararg params: BorrowModel): Void? {
//            this.appDatabase.itemAndPersonelModel().deleteBorrow(params[0])
//            return null
//        }
//    }
}
