package com.example.roomdatabindinglivedatakotlinmvvm.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.Callback
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabindinglivedatakotlinmvvm.R
import com.example.roomdatabindinglivedatakotlinmvvm.databinding.BorrowItemBinding
import com.example.roomdatabindinglivedatakotlinmvvm.db.entity.BorrowModel
import com.example.roomdatabindinglivedatakotlinmvvm.view.callback.DeleteItemCallback

import java.util.Objects

class RecyclerViewAdapter(private val deleteItemCallback: DeleteItemCallback?) : RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {

    internal var mBorrowModelList: List<BorrowModel>? = null


    init {
        setHasStableIds(true)
    }

    fun setItemList(borrowModelList: List<BorrowModel>) {
        if (this.mBorrowModelList == null) {
            this.mBorrowModelList = borrowModelList
            notifyItemRangeInserted(0, borrowModelList.size)
        } else {
            val diffUtilCallback: DiffUtil.Callback
            val result = DiffUtil.calculateDiff(object : Callback() {
                override fun getOldListSize(): Int {
                    return mBorrowModelList!!.size
                }

                override fun getNewListSize(): Int {
                    return borrowModelList.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return mBorrowModelList!![oldItemPosition].itemName === borrowModelList[newItemPosition].itemName
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val newProduct = borrowModelList[newItemPosition]
                    val oldProduct = mBorrowModelList!![oldItemPosition]
                    return (newProduct.itemName === oldProduct.itemName
                            && newProduct.personName == oldProduct.personName)
                }
            })
            mBorrowModelList = borrowModelList
            result.dispatchUpdatesTo(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val binding = DataBindingUtil
                .inflate<BorrowItemBinding>(LayoutInflater.from(parent.context), R.layout.borrow_item,
                        parent, false)
        binding.callback = deleteItemCallback
        return RecyclerViewHolder(binding)

    }


    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.binding.borrowModel = mBorrowModelList!![position]
        holder.binding.executePendingBindings()

    }

    override fun getItemCount(): Int {
        return if (mBorrowModelList == null) 0 else mBorrowModelList!!.size
    }


    inner class RecyclerViewHolder(internal val binding: BorrowItemBinding) : RecyclerView.ViewHolder(binding.root)
}
