package com.ilham.taspesialisbangunan.ui.pelanggan.notifikasipelanggan.notifprodukjasa.tabs.step1.menunggu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.database.PrefsManager
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.pengajuan.DataPengajuan
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanDetail
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanList1
import com.ilham.taspesialisbangunan.data.model.pengajuan.ResponsePengajuanUpdate
import com.ilham.taspesialisbangunan.ui.pelanggan.notifikasipelanggan.notifprodukjasa.tabs.step1.MenungguAdapter

class MenungguFragment : Fragment(), MenungguContract.View {

    lateinit var presenter: MenungguPresenter
    lateinit var menungguAdapter: MenungguAdapter
    lateinit var datapengajuan: DataPengajuan
    lateinit var prefsManager: PrefsManager

    lateinit var rcvMenunggu: RecyclerView
    lateinit var swipeMenunggu: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menunggu, container, false)

        presenter = MenungguPresenter(this)
        prefsManager = PrefsManager(requireActivity())

        initFragment(view)

        return view
    }

    override fun onStart() {
        super.onStart()
        if (prefsManager.prefsIsLogin) {
            presenter.getPengajuanmenunggu(prefsManager.prefsId.toLong())
        }
    }

    override fun initFragment(view: View) {
//        (activity as AppCompatActivity).supportActionBar!!.hide()

        rcvMenunggu = view.findViewById(R.id.rcvMenunggu)
        swipeMenunggu = view.findViewById(R.id.swipeMenunggu)

        menungguAdapter = MenungguAdapter(requireActivity(), arrayListOf()){
                dataPengajuan: DataPengajuan, position: Int, type: String ->
//            Constant.PENGAJUAN_ID = datapengajuan.id!!

            datapengajuan = dataPengajuan

            when (type) {
                "Delete" -> showDialogDelete( dataPengajuan, position )
            }

        }

        rcvMenunggu.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = menungguAdapter
        }

        swipeMenunggu.setOnRefreshListener {
            if (prefsManager.prefsIsLogin) {
                presenter.getPengajuanmenunggu(prefsManager.prefsId.toLong())
            }
        }
    }

    override fun onLoadingPengajuanmenunggu(loading: Boolean) {
        when (loading) {
            true -> swipeMenunggu.isRefreshing = true
            false -> swipeMenunggu.isRefreshing = false
        }
    }

    override fun onResultPengajuanmenunggu(responsePengajuanList1: ResponsePengajuanList1) {
        val pengajuan: List<DataPengajuan> = responsePengajuanList1.pengajuan
        menungguAdapter.setData(pengajuan)
    }

    override fun onResultDelete(responsePengajuanUpdate: ResponsePengajuanUpdate) {
        showMessage( responsePengajuanUpdate.msg )
    }

    override fun showDialogDelete(dataPengajuan: DataPengajuan, position: Int) {
        val dialog = AlertDialog.Builder(requireActivity())
        dialog.setTitle("Konfirmasi")
        dialog.setMessage("Hapus ${dataPengajuan.bukti}?")

        dialog.setPositiveButton ("Hapus") { dialog, which ->
            presenter.deletePengajuanMenunggu(Constant.PENGAJUAN_ID)
            menungguAdapter.removePengajuanmenunggu(position)
            dialog.dismiss()
        }

        dialog.setNegativeButton("Batal") { dialog, which ->
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun showMessage(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

}