package com.example.roomdatabindinglivedatakotlinmvvm.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.roomdatabindinglivedatakotlinmvvm.db.entity.BorrowModel

@Dao
interface BorrowModelDao {

    @get:Query("select * from BorrowModel")
    val allBorrowedItems: LiveData<List<BorrowModel>>


    @Query("select * from BorrowModel where id = :id")
    fun getItembyId(id: String): BorrowModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBorrow(borrowModel: BorrowModel)

    @Delete
    fun deleteBorrow(borrowModel: BorrowModel)

}