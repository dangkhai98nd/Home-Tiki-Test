package com.android.app.tikitest.components.recyclerview

import android.view.View
import com.android.app.tikitest.R
import com.android.app.tikitest.models.FlashDeal
import com.android.apps.extensions.parseMoneyStringVND
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_flash_deal.view.*

class FlashDealAdapter : BaseAdapter<FlashDeal>() {
    override fun onBindViewHolder(view: View, item: FlashDeal, position: Int) {
        with(view){
            Glide.with(context)
                .load(item.product.thumbnailUrl)
                .into(image_flash_deal)
            text_price.text = context.parseMoneyStringVND(item.specialPrice)
            progress_bar_flash_deal.progress = (100f - item.progress.percent).toFloat()
            text_progress_bar.text = context.getString(R.string.text_progress_bar_flash_deal, item.progress.qtyOrdered.toString())
            text_discount.text = context.getString(R.string.text_discount_flash_deal, item.discountPercent.toString())
        }
    }

    override fun getItemLayoutId(): Int = R.layout.item_flash_deal
}