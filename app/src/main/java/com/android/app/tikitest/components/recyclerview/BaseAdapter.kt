package com.android.app.tikitest.components.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseAdapter<T>.ItemViewHolder>() {
    protected val items = mutableListOf<T>()
    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(getItemLayoutId(),parent,false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        onBindViewHolder(holder.itemView, item,position)
    }
    abstract fun onBindViewHolder(view : View, item : T, position: Int)
    abstract fun getItemLayoutId() : Int

    fun initialData(list : List<T>?) {
        if (list == null) return
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }
    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}