package com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.tabnotif.dp

import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanList1


interface DPjasaContract {

    interface Presenter {
        fun getPengajuanDP(kd_jasa: String)
    }

    interface View {
        fun initFragment(view: android.view.View)
        fun onLoadingPengajuanDP(loading: Boolean)
        fun onResultPengajuanDP(responsePengajuanList1: ResponsePengajuanList1)
        fun showMessage(message: String)
    }
}