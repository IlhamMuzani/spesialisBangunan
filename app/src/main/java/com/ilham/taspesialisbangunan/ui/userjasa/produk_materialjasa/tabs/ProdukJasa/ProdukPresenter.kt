package com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.tabs.ProdukJasa

import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukList
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukUpdate
import com.ilham.taspesialisbangunan.data.model.tambahrek.ResponseTambahrekList
import com.ilham.taspesialisbangunan.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProdukPresenter (var view: ProdukContract.View) : ProdukContract.Presenter {

    override fun getProduk(kd_user : Long) {
        view.onLoadingProduk(true)
        ApiConfig.endpoint.myproduct(kd_user).enqueue(object : Callback<ResponseProdukList> {
            override fun onResponse(
                call: Call<ResponseProdukList>,
                response: Response<ResponseProdukList>
            ) {
                view.onLoadingProduk(false)
                if (response.isSuccessful){
                    val responseProdukList: ResponseProdukList? = response.body()
                    view.onResultProduk(responseProdukList!!)
                }

            }

            override fun onFailure(call: Call<ResponseProdukList>, t: Throwable) {
                view.onLoadingProduk(false)
            }
        } )
    }

    override fun deleteProduk(kd_produkjasa: Long) {
        view.onLoadingProduk(true)
        ApiConfig.endpoint.deleteProduk(kd_produkjasa).enqueue(object : Callback<ResponseProdukUpdate>{
            override fun onResponse(
                call: Call<ResponseProdukUpdate>,
                response: Response<ResponseProdukUpdate>
            ) {
                view.onLoadingProduk(false)
                if (response.isSuccessful) {
                    val responseProdukUpdate: ResponseProdukUpdate? = response.body()
                    view.onResultDelete( responseProdukUpdate!! )
                }
            }

            override fun onFailure(call: Call<ResponseProdukUpdate>, t: Throwable) {
                view.onLoadingProduk(false)
            }

        })
    }

    override fun getTampilrek(kd_user: String) {
        view.onLoadingProduk(true)
        ApiConfig.endpoint.myproducttambahrek(kd_user).enqueue(object : Callback<ResponseTambahrekList>{
            override fun onResponse(
                call: Call<ResponseTambahrekList>,
                response: Response<ResponseTambahrekList>
            ) {
                view.onLoadingProduk(false)
                if (response.isSuccessful){
                    val responseTambahrekList: ResponseTambahrekList? = response.body()
                    view.onResultTampilRek( responseTambahrekList!!)
                }
            }

            override fun onFailure(call: Call<ResponseTambahrekList>, t: Throwable) {
                view.onLoadingProduk(false)
            }
        })
    }
}