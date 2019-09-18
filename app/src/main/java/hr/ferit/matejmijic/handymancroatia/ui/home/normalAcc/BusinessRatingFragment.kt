package hr.ferit.matejmijic.handymancroatia.ui.home.normalAcc

import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import hr.ferit.matejmijic.handymancroatia.HandymanApp
import hr.ferit.matejmijic.handymancroatia.R
import hr.ferit.matejmijic.handymancroatia.common.showFragment
import hr.ferit.matejmijic.handymancroatia.model.BusinessUser
import hr.ferit.matejmijic.handymancroatia.model.UserReview
import hr.ferit.matejmijic.handymancroatia.persistence.BusinessUserRepo
import hr.ferit.matejmijic.handymancroatia.ui.base.BaseFragment
import hr.ferit.matejmijic.handymancroatia.ui.home.normalAcc.recyclerview.BusinessRatingAdapter
import hr.ferit.matejmijic.handymancroatia.ui.home.normalAcc.recyclerview.FrontPageAdapter
import kotlinx.android.synthetic.main.fragment_business_rating.*

class BusinessRatingFragment: BaseFragment() {

    private lateinit var  auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private val repository = BusinessUserRepo
    private val adapter by lazy { BusinessRatingAdapter { onItemSelected(it) } }
    private var businessUserId = "No user"

    private fun onItemSelected(review: UserReview) {
        activity?.showFragment(R.id.fragmentContainer, ReviewFragment.newInstance(review.title.toString(), review.review.toString()), true)
    }


    override fun getLayoutResourceId(): Int = R.layout.fragment_business_rating

    override fun initUi() {
        arguments?.getString(EXTRA_USER_ID)?.let { businessUserId = it }
        initListeners()
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        reviewPageRecyclerView.layoutManager = LinearLayoutManager(context)
        reviewPageRecyclerView.adapter = adapter
        getReviews()
    }

    private fun initListeners() {
        add_review.setOnClickListener {
            activity?.showFragment(R.id.fragmentContainer,AddReviewFragment.newInstance(businessUserId),true)
        }
    }

    private fun getReviews() {
        val businessUsers = db.collection("businessUsers").document(businessUserId).collection("reviews")
        businessUsers.get().addOnSuccessListener { it ->
            if (!it.isEmpty){
                var count = 0
                var rating = 0
                val reviews = it.toObjects(UserReview::class.java)
                adapter.setData(reviews)
                reviews.forEach {
                    count++
                    rating += it.rating
                }
               tv_rating.text = (rating/count).toString()

            }else{
                Toast.makeText(HandymanApp.getAppContext(),"No reviews", Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object{
        fun newInstance(uId: String): BusinessRatingFragment {
            val bundle = Bundle().apply { putString(EXTRA_USER_ID, uId) }
            return BusinessRatingFragment()
                .apply { arguments = bundle }
        }
    }
}