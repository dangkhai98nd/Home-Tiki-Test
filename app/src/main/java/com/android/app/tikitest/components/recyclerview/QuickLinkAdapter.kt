package com.android.app.tikitest.components.recyclerview

import android.view.View
import com.android.app.tikitest.R
import com.android.app.tikitest.models.QuickLink
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_quick_link.view.*

class QuickLinkAdapter : BaseAdapter<QuickLink?>() {
    override fun onBindViewHolder(view: View, item: QuickLink?, position: Int) {
        if (item == null) {
            view.visibility = View.INVISIBLE
            return
        }
        view.visibility = View.VISIBLE
        with(view) {
            title_quick_link.text = item.title
            Glide.with(context)
                .load(item.imageUrl)
                .into(image_quick_link)
        }
    }

    override fun getItemLayoutId(): Int = R.layout.item_quick_link
}