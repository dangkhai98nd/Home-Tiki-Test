package com.android.app.tikitest.views

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.app.tikitest.R
import com.android.app.tikitest.components.recyclerview.FlashDealAdapter
import com.android.app.tikitest.components.recyclerview.QuickLinkAdapter
import com.android.app.tikitest.components.viewpager.BannerAdapter
import com.android.app.tikitest.models.Banner
import com.android.app.tikitest.models.BaseResponse
import com.android.app.tikitest.models.FlashDeal
import com.android.app.tikitest.models.QuickLink
import com.android.app.tikitest.server.Api
import com.android.apps.components.lifecycle.ownRx
import com.android.apps.extensions.*
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.BiFunction
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_banner.view.*
import kotlinx.android.synthetic.main.layout_flash_deal.*
import kotlinx.android.synthetic.main.layout_quick_link.*

class MainActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_main
    private val apiHomeService by lazy { Api.instance.getApiHomeService() }
    private val bannerAdapter by lazy { BannerAdapter(this) }
    private val quickLinkAdapter by lazy { QuickLinkAdapter() }
    private val flashDealAdapter by lazy { FlashDealAdapter() }
    private var flashDealList = mutableListOf<FlashDeal>()
    private var canRenderFlashDeal = false
    private lateinit var bannerView: View
    private lateinit var quickLinkView: View
    private lateinit var flashDealView: View

    override fun initializeVariable() {
        refresh_main.setProgressViewOffset(false,136.toDp ,200.toDp)
        refresh_main.setOnRefreshListener {
            if (progress_bar_main.visibility == View.GONE)
                loadBannerAndQuickLink()
            refresh_main.isRefreshing = false
        }
        loadBannerAndQuickLink()
    }

    private fun loadBannerAndQuickLink() {
        container.removeAllViews()
        canRenderFlashDeal = false
        progress_bar_main.visibility = View.VISIBLE
        ownRx(
            createRxLoadBannerAndQuickLink().subscribeOnIO()
                .observeOnMain()
                .subscribe({
                    Log.w("boolean", it.toString())
                }, Throwable::printStackTrace)
        )
    }

    private fun createRxLoadBannerAndQuickLink() =
        Observable.zip<BaseResponse<List<Banner>>, BaseResponse<List<List<QuickLink>>>, Boolean>(
            apiHomeService.getBanner().onErrorReturn {
                BaseResponse(null, null)
            },
            apiHomeService.getQuickLink().onErrorReturn {
                BaseResponse(null, null)
            },
            BiFunction { t1, t2 ->
                loadFlashDeal()
                runOnUIThread {
                    renderBanner(t1.data, t2.data)
                }
                true
            }
        )

    private fun loadFlashDeal() {
        flashDealList.clear()
        ownRx(
            Api.instance.getApiHomeService().getFlashDeal()
                .subscribeOnIO()
                .observeOnMain()
                .subscribe({
                    if (!it.data.isNullOrEmpty()) {
                        flashDealList.addAll(it.data)
                    }
                    runOnUIThread {
                        renderFlashDeal()
                    }
                }, {
                    it.printStackTrace()
                })
        )
    }

    private fun renderBanner(bannerList: List<Banner>?, quickLinkList: List<List<QuickLink>>?) {
        if (bannerList.isNullOrEmpty()) {
            renderQuickLink(quickLinkList)
            return
        }
        if (::bannerView.isInitialized) {
            bannerAdapter.initialData(bannerList)
            container.addViewOrNot(bannerView)
            bannerView.waitForRendered {
                renderQuickLink(quickLinkList)
            }
            return
        }
        bannerView = (getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
            R.layout.layout_banner,
            null
        )
        with(bannerView) {
            container.addViewOrNot(this)
            indicator_banner.setupWithViewPager(view_pager_banner)
            view_pager_banner.adapter = bannerAdapter
            bannerAdapter.initialData(bannerList)
            view_pager_banner.setAutoScroll(lifecycle)
            waitForRendered {
                renderQuickLink(quickLinkList)
            }
        }
    }

    private fun renderQuickLink(quickLinkList: List<List<QuickLink>>?) {
        if (quickLinkList.isNullOrEmpty()) {
            renderFlashDeal()
            return
        }
        val list = zip(quickLinkList)
        if (::quickLinkView.isInitialized) {
            container.addViewOrNot(quickLinkView)
            quickLinkAdapter.initialData(list)
            quickLinkView.waitForRendered {
                renderFlashDeal()
            }
            return
        }
        quickLinkView =
            (getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
                R.layout.layout_quick_link,
                null
            )
        with(quickLinkView) {
            container.addViewOrNot(this)
            recycler_view_quick_link.layoutManager = GridLayoutManager(
                this@MainActivity,
                quickLinkList.size,
                RecyclerView.HORIZONTAL,
                false
            )
            recycler_view_quick_link.adapter = quickLinkAdapter
            quickLinkAdapter.initialData(list)
            waitForRendered {
                renderFlashDeal()
            }
        }
    }

    private fun renderFlashDeal() {
        if (canRenderFlashDeal) progress_bar_main.visibility = View.GONE
        if (flashDealList.isNullOrEmpty() || canRenderFlashDeal.not()) {
            canRenderFlashDeal = true
            return
        }
        if (::flashDealView.isInitialized) {
            container.addViewOrNot(flashDealView)
            flashDealAdapter.initialData(flashDealList)
            return
        }
        flashDealView =
            (getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
                R.layout.layout_flash_deal,
                null
            )
        with(flashDealView) {
            container.addViewOrNot(this)
            recycler_View_flash_deal.adapter = flashDealAdapter
            flashDealAdapter.initialData(flashDealList)
        }
    }
}