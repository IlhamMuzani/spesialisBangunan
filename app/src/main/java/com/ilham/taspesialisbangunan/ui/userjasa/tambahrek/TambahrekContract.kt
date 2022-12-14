package com.ilham.taspesialisbangunan.ui.userjasa.tambahrek

import com.ilham.taspesialisbangunan.data.model.tambahrek.DataTambahrek
import com.ilham.taspesialisbangunan.data.model.tambahrek.ResponseTambahrekList
import com.ilham.taspesialisbangunan.data.model.tambahrek.ResponseTambahrekUpdate

interface TambahrekContract {

        interface Presenter {
            fun getTambahrek(kd_user: String)
            fun deleteTambahrek(kd_rekening: Long)
        }

        interface View {
            fun initActivity()
            fun initListener()
            fun onLoadingTambahrek(loading: Boolean)
            fun onResultTambahrek(responseTambahrekList: ResponseTambahrekList)
            fun onResultDelete(responseTambahrekUpdate: ResponseTambahrekUpdate)
            fun showDialogDelete(dataTambahrek: DataTambahrek, position: Int)
            fun showMessage(message: String)
        }
}