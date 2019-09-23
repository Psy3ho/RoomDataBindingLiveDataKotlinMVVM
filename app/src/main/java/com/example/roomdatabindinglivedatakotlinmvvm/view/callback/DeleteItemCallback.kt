package com.example.roomdatabindinglivedatakotlinmvvm.view.callback

import com.example.roomdatabindinglivedatakotlinmvvm.db.entity.BorrowModel


interface DeleteItemCallback {
    fun onClick(borrowModel: BorrowModel)
}
