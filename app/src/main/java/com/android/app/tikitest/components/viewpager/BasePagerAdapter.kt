package com.android.app.tikitest.components.viewpager

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

abstract class BasePagerAdapter<T> (val context : Context) : PagerAdapter() {
    private val items = mutableListOf<T>()

    abstract fun getItemLayoutId() : Int

    abstract fun onBindView(view :View, item : T)

    override fun getCount(): Int = items.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == (`object` as View)
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        (`object` as View).let {
            container.removeView(it)
        }
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(getItemLayoutId(),null)
        val item = items[position]
        onBindView(view, item)
        container.addView(view)
        return view
    }

    fun initialData(list : List<T>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }
}