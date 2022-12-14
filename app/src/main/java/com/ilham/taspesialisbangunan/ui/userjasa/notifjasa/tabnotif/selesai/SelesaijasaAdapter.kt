package com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.tabnotif.selesai

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ilham.taspesialisbangunan.R
import com.ilham.taspesialisbangunan.data.model.Constant
import com.ilham.taspesialisbangunan.data.model.pengajuan.DataPengajuan
import com.ilham.taspesialisbangunan.ui.userjasa.notifjasa.detail.DetailPengajuanActivity
import kotlinx.android.synthetic.main.adapter_menunggujasa.view.detailpengajuanjasa
import kotlinx.android.synthetic.main.adapter_selesaijasa.view.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList


class SelesaijasaAdapter (val context: Context, var dataPengajuan: ArrayList<DataPengajuan>,
                          val clickListener: (DataPengajuan, Int, String) -> Unit ):
        RecyclerView.Adapter<SelesaijasaAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.adapter_selesaijasa, parent, false)
    )

    override fun getItemCount() = dataPengajuan.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bing(dataPengajuan[position])

        holder.view.detailpengajuanjasa.setOnClickListener {
            Constant.PENGAJUAN_ID = dataPengajuan[position].id!!
            context.startActivity(Intent(context, DetailPengajuanActivity::class.java ))
        }
//        GlideHelper.setImage(context, Constant.IP_IMAGE + dataPengajuan[position].gambar, holder.imvPengajuan)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val view = view
        fun bing(datapengajuan: DataPengajuan) {
            view.txvJenispembuatanP.text = datapengajuan.produk.model
            view.txvNamajasaSelesai.text = datapengajuan.user.username
            view.txvtanggaljas.text = datapengajuan.created_at
            view.txvHargajas.text = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format (Integer.valueOf(datapengajuan.harga))

        }
//        val imvPengajuan = view.findViewById<ImageView>(R.id.imvPengajuanSelesai)
    }

    fun setData(newDataPengajuan: List<DataPengajuan>) {
        dataPengajuan.clear()
        dataPengajuan.addAll(newDataPengajuan)
        notifyDataSetChanged()
    }
}