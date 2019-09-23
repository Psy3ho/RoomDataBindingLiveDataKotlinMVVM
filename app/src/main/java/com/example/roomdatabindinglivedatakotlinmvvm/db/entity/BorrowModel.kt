package com.example.roomdatabindinglivedatakotlinmvvm.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.*

@Entity
class BorrowModel(var itemName: String?, var personName: String?) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
