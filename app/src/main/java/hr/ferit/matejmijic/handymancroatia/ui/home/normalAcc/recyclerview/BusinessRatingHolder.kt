package hr.ferit.matejmijic.handymancroatia.ui.home.normalAcc.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.auth.User
import hr.ferit.matejmijic.handymancroatia.model.BusinessUser
import hr.ferit.matejmijic.handymancroatia.model.UserReview
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_review.view.*
import kotlinx.android.synthetic.main.item_user.view.*

class BusinessRatingHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer {
    fun bindData(review: UserReview, onItemSelected: (UserReview)->Unit){
        containerView.setOnClickListener { onItemSelected(review) }
        containerView.title.text = review.title
    }
}