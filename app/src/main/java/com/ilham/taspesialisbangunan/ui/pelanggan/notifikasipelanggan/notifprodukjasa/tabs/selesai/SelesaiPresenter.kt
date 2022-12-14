package com.ilham.taspesialisbangunan.ui.pelanggan.notifikasipelanggan.notifprodukjasa.tabs.selesai

import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanList1
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanUpdate
import com.ilham.taspesialisbangunan.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SelesaiPresenter (var view: SelesaiContract.View) : SelesaiContract.Presenter {

    override fun getPengajuanselesai(kd_userpelenggan: Long) {
        view.onLoadingPengajuanselesai(true)
        ApiConfig.endpoint.Pengajuanuserselesai(kd_userpelenggan).enqueue(object : Callback<ResponsePengajuanList1> {
            override fun onResponse(
                call: Call<ResponsePengajuanList1>,
                response: Response<ResponsePengajuanList1>
            ) {
                view.onLoadingPengajuanselesai(false)
                if (response.isSuccessful) {
                    val responsePengajuanList1: ResponsePengajuanList1? = response.body()
                    view.onResultPengajuanselesai( responsePengajuanList1!! )
                }
            }

            override fun onFailure(call: Call<ResponsePengajuanList1>, t: Throwable) {
                view.onLoadingPengajuanselesai(false)
            }

        })
    }

    override fun pengajuanselesaikehistori(id: Long) {
        view.onLoadingPengajuanselesai(true)
        ApiConfig.endpoint.pengajuanselesaikehistori(id, "PUT").enqueue(object : Callback<ResponsePengajuanUpdate>{
            override fun onResponse(
                call: Call<ResponsePengajuanUpdate>,
                response: Response<ResponsePengajuanUpdate>
            ) {
                view.onLoadingPengajuanselesai(false)
                if (response.isSuccessful) {
                    val responsePengajuanUpdate: ResponsePengajuanUpdate? = response.body()
                    view.onResultSelesaikeHistori(responsePengajuanUpdate!!)
                }
            }

            override fun onFailure(call: Call<ResponsePengajuanUpdate>, t: Throwable) {
                view.onLoadingPengajuanselesai(false)
            }

        })
    }
}