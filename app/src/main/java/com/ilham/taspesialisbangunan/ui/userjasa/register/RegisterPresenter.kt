package com.ilham.taspesialisbangunan.ui.userjasa.produk_materialjasa.create_produk

import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukList
import com.ilham.taspesialisbangunan.data.model.produk.ResponseProdukUpdate
import com.ilham.taspesialisbangunan.network.ApiConfig
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ProdukCreatePresenter(val view: ProdukCreateContract.View) : ProdukCreateContract.Presenter {

    init {
        view.initActivity()
        view.initListener()
        view.onLoading(false)
    }

    override fun insertProduk(
        jasausers_id: String,
        nama_toko: String,
        category: String,
        jenis_pembuatan: String,
        alamat: String,
        kelurahan: String,
        phone: String,
        harga: String,
        latitude: String,
        longitude: String,
        gambar: File,
        deskripsi: String
    ) {

        val requestBody: RequestBody = RequestBody.create(MediaType.parse("image/*"), gambar)
        val multipartBody: MultipartBody.Part = MultipartBody.Part.createFormData(
            "gambar",
            gambar.name, requestBody
        )

        view.onLoading(true)
        ApiConfig.endpoint.insertProduk(
            jasausers_id,
            nama_toko,
            category,
            jenis_pembuatan,
            alamat,
            kelurahan,
            phone,
            harga,
            latitude,
            longitude,
            multipartBody!!,
            deskripsi
        )
            .enqueue(object : Callback<ResponseProdukUpdate> {
                override fun onResponse(
                    call: Call<ResponseProdukUpdate>,
                    response: Response<ResponseProdukUpdate>
                ) {
                    view.onLoading(false)
                    if (response.isSuccessful) {
                        val responseProdukUpdate: ResponseProdukUpdate? = response.body()
                        view.onResult(responseProdukUpdate!!)
                    }
                }

                override fun onFailure(call: Call<ResponseProdukUpdate>, t: Throwable) {
                    view.onLoading(false)
                }

            })
    }

    override fun searchCategoriproduk(keyword: String) {
        view.onLoading(true)
        ApiConfig.endpoint.searchJenisPembuatan(keyword).enqueue(object : Callback<ResponseProdukList>{
            override fun onResponse(
                call: Call<ResponseProdukList>,
                response: Response<ResponseProdukList>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseProdukList: ResponseProdukList? = response.body()
                    view.onResultSearchProduk(responseProdukList!!)
                }
            }

            override fun onFailure(call: Call<ResponseProdukList>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

    override fun searchkecamatan(keyword: String) {
        view.onLoading(true)
        ApiConfig.endpoint.searchkecamatan(keyword).enqueue(object : Callback<ResponseProdukList>{
            override fun onResponse(
                call: Call<ResponseProdukList>,
                response: Response<ResponseProdukList>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseProdukList: ResponseProdukList? = response.body()
                    view.onResultSearchKecamatan(responseProdukList!!)
                }
            }

            override fun onFailure(call: Call<ResponseProdukList>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }
}