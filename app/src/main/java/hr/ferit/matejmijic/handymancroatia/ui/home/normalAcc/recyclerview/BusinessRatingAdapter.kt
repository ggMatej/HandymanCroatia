package hr.ferit.matejmijic.handymancroatia.ui.home.normalAcc.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Adapter
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.auth.User
import hr.ferit.matejmijic.handymancroatia.R
import hr.ferit.matejmijic.handymancroatia.model.BusinessUser
import hr.ferit.matejmijic.handymancroatia.model.UserReview

class BusinessRatingAdapter(private val onItemSelected: (UserReview) -> Unit): RecyclerView.Adapter<BusinessRatingHolder>(){

    private val data: MutableList<UserReview> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusinessRatingHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false)
        return BusinessRatingHolder(v)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: BusinessRatingHolder, position: Int) {
        holder.bindData(data[position], onItemSelected)
    }

    fun setData(data: MutableList<UserReview>){
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }
}