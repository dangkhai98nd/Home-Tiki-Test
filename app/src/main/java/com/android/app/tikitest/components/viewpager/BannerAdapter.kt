package com.android.app.tikitest.components.viewpager

import android.content.Context
import android.view.View
import com.android.app.tikitest.R
import com.android.app.tikitest.models.Banner
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_banner.view.*


class BannerAdapter(context: Context) : BasePagerAdapter<Banner>(context) {
    override fun getItemLayoutId(): Int = R.layout.item_banner

    override fun onBindView(view: View, item: Banner) {
        with(view) {
            Glide.with(context)
                .load(item.mobileUrl)
                .into(image_banner)

        }
    }
}