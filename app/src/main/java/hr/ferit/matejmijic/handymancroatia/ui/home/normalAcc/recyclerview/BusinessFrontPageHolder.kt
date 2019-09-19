package hr.ferit.matejmijic.handymancroatia.ui.home.normalAcc.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import hr.ferit.matejmijic.handymancroatia.model.NormalUser
import hr.ferit.matejmijic.handymancroatia.model.Offer
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_user.view.*

class BusinessFrontPageHolder(override val containerView: View): RecyclerView.ViewHolder(containerView),
    LayoutContainer {

    fun bindData(offer: Offer, onItemSelected: (Offer)->Unit){
        containerView.setOnClickListener { onItemSelected(offer) }
        containerView.nickname.text = offer.nickname
    }
}
