package com.android.app.tikitest.views

import android.util.Log
import com.android.app.tikitest.R
import com.android.app.tikitest.models.Banner
import com.android.app.tikitest.models.BaseResponse
import com.android.app.tikitest.models.QuickLink
import com.android.app.tikitest.server.Api
import com.android.apps.components.lifecycle.ownRx
import com.android.apps.extensions.observeOnMain
import com.android.apps.extensions.subscribeOnIO
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.BiFunction
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_main
    private val apiHomeService by lazy { Api.instance.getApiHomeService() }

    override fun initializeVariable() {
        loadBannerAndQuickLink()
    }

    private fun loadBannerAndQuickLink() {
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
                BaseResponse(null,null)
            },
            apiHomeService.getQuickLink().onErrorReturn {
                BaseResponse(null,null)
            },
            BiFunction { t1, t2 ->
                loadFlashDeal()
                renderBanner(t1.data)
                renderQuickLink(t2.data)
                true
            }
        )

    private fun loadFlashDeal() {
        ownRx(
            Api.instance.getApiHomeService().getFlashDeal()
                .subscribeOnIO()
                .observeOnMain()
                .subscribe({

                }, {
                    it.printStackTrace()
                })
        )
    }

    private fun renderBanner(bannerList: List<Banner>?) {

    }

    private fun renderQuickLink(list: List<List<QuickLink>>?) {

    }
}