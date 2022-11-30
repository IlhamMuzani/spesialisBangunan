package com.ilham.taspesialisbangunan.ui.userjasa.updateprofiljasa

import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.alamat.ResponseALamatList
import com.ilham.taspesialisbangunan.data.model.user.*
import com.ilham.taspesialisbangunan.network.ApiConfig
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class UbahProfiljasaPresenter (val view: UbahProfiljasaContract.View) : UbahProfiljasaContract.Presenter {

    init {
        view.initActivity()
        view.initListener()

    }

    override fun getDetailProfiljasa(id: String) {
        view.onLoading(true)
        ApiConfig.endpoint.userDetail(id).enqueue( object :
            Callback<ResponseUserdetail> {
            override fun onResponse(
                call: Call<ResponseUserdetail>,
                response: Response<ResponseUserdetail>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseUserdetail: ResponseUserdetail? = response.body()
                    view.onResultDetailProfiljasa( responseUserdetail!! )
                }
            }

            override fun onFailure(call: Call<ResponseUserdetail>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }

    override fun updateProfiljasa(
        id: Long,
        username: String,
        kecamatan: String,
        kelurahan: String,
        alamat: String,
        phone: String,
        gambar: File?,
    ) {

        val requestBody: RequestBody
        val multipartBody: MultipartBody.Part

        if (gambar != null) {
            requestBody = RequestBody.create(MediaType.parse("image/*"), gambar)
            multipartBody = MultipartBody.Part.createFormData("gambar",
                gambar.name, requestBody)
        } else {
            requestBody = RequestBody.create(MediaType.parse("image/*"), "")
            multipartBody= MultipartBody.Part.createFormData("gambar",
                "", requestBody)
        }

        view.onLoading(true)
        ApiConfig.endpoint.updateJasa(id, username, kecamatan, kelurahan, alamat, phone,multipartBody, "PUT") .enqueue(object : Callback<ResponseUserUpdate> {
            override fun onResponse(
                call: Call<ResponseUserUpdate>,
                response: Response<ResponseUserUpdate>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseUserUpdate: ResponseUserUpdate? = response.body()
                    view.onResultUpdateProfiljasa( responseUserUpdate!! )
                }
            }

            override fun onFailure(call: Call<ResponseUserUpdate>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }
    override fun setPrefs(prefsManager: PrefsManager, dataUser: DataUser) {
        prefsManager.prefsIsLogin = true
        prefsManager.prefsUsername = dataUser.username!!
        prefsManager.prefsEmail = dataUser.email!!
        prefsManager.prefsKecamatan = dataUser.kecamatan!!
        prefsManager.prefsKelurahan = dataUser.kelurahan!!
        prefsManager.prefsAlamat = dataUser.alamat!!
        prefsManager.prefsPhone = dataUser.phone!!
        prefsManager.prefsGambar = dataUser.gambar!!

    }

    override fun searchAlamatkecamatanupdate(keyword: String) {
        ApiConfig.endpoint.searchAlamatkecamatan(keyword).enqueue(object : Callback<ResponseALamatList>{
            override fun onResponse(
                call: Call<ResponseALamatList>,
                response: Response<ResponseALamatList>
            ) {
                view.onLoading(false)
                if (response.isSuccessful) {
                    val responseALamatList: ResponseALamatList? = response.body()
                    view.onResultSearchalamatkecamatanupdate(responseALamatList!!)
                }
            }

            override fun onFailure(call: Call<ResponseALamatList>, t: Throwable) {
                view.onLoading(false)
            }

        })
    }
}