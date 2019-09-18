package hr.ferit.matejmijic.handymancroatia.ui.home.normalAcc

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import hr.ferit.matejmijic.handymancroatia.model.BusinessUser
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_user.view.*

class FrontPageHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bindData(user: BusinessUser, onItemSelected: (BusinessUser)->Unit){
        containerView.setOnClickListener { onItemSelected(user) }
        containerView.nickname.text = user.nickname
    }
}