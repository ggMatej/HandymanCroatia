package hr.ferit.matejmijic.handymancroatia.ui.home.normalAcc

import android.view.LayoutInflater
import android.view.ViewGroup
import hr.ferit.matejmijic.handymancroatia.model.BusinessUser
import androidx.recyclerview.widget.RecyclerView.Adapter
import hr.ferit.matejmijic.handymancroatia.R


class FrontPageAdapter(private val onItemSelected: (BusinessUser) -> Unit): Adapter<FrontPageHolder>() {
    private val data: MutableList<BusinessUser> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FrontPageHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return FrontPageHolder(v)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: FrontPageHolder, position: Int) {
        holder.bindData(data[position], onItemSelected)
    }

    fun setData(data: MutableList<BusinessUser>){
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }
}