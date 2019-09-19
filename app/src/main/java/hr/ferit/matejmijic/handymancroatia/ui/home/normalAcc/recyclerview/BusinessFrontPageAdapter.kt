package hr.ferit.matejmijic.handymancroatia.ui.home.normalAcc.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hr.ferit.matejmijic.handymancroatia.R
import hr.ferit.matejmijic.handymancroatia.model.NormalUser
import hr.ferit.matejmijic.handymancroatia.model.Offer

class BusinessFrontPageAdapter(private val onItemSelected: (Offer) -> Unit): RecyclerView.Adapter<BusinessFrontPageHolder>() {
    private val data: MutableList<Offer> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusinessFrontPageHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return BusinessFrontPageHolder(v)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: BusinessFrontPageHolder, position: Int) {
        holder.bindData(data[position], onItemSelected)
    }

    fun setData(offer: MutableList<Offer>){
        this.data.clear()
        this.data.addAll(offer)
        notifyDataSetChanged()
    }
}
