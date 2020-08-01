package com.android.app.tikitest.server

import com.android.app.tikitest.models.Banner
import com.android.app.tikitest.models.BaseResponse
import com.android.app.tikitest.models.FlashDeal
import com.android.app.tikitest.models.QuickLink
import com.android.app.tikitest.server.Api.Companion.BANNER
import com.android.app.tikitest.server.Api.Companion.FLASH_DEAL
import com.android.app.tikitest.server.Api.Companion.QUICK_LINK
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface HomeService {

    @GET(BANNER)
    fun getBanner() : Observable<BaseResponse<List<Banner>>>

    @GET(QUICK_LINK)
    fun getQuickLink() : Observable<BaseResponse<List<List<QuickLink>>>>

    @GET(FLASH_DEAL)
    fun getFlashDeal() : Observable<BaseResponse<List<FlashDeal>>>
}