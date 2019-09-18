package hr.ferit.matejmijic.handymancroatia.ui.home.normalAcc

import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import hr.ferit.matejmijic.handymancroatia.HandymanApp
import hr.ferit.matejmijic.handymancroatia.R
import hr.ferit.matejmijic.handymancroatia.model.UserReview
import hr.ferit.matejmijic.handymancroatia.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_add_review.*

class AddReviewFragment: BaseFragment() {
    private lateinit var  auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private var businessUserId = "No user"

    override fun getLayoutResourceId(): Int = R.layout.fragment_add_review

    override fun initUi() {
        arguments?.getString(EXTRA_USER_ID)?.let { businessUserId = it }
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        publish.setOnClickListener {
            if(title.text.isNotEmpty() && review_text.text!!.isNotEmpty()){
                val review = UserReview(grade.selectedItem.toString().toInt(),title.text.toString(), review_text.text.toString())
                db.collection("businessUsers").document(businessUserId).collection("reviews").document(auth.uid.toString()).set(
                    review
                )
                activity?.onBackPressed()
            }else{
                Toast.makeText(HandymanApp.getAppContext(),"Please input all fields",Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object{
        fun newInstance(uId: String): AddReviewFragment {
            val bundle = Bundle().apply { putString(EXTRA_USER_ID, uId) }
            return AddReviewFragment()
                .apply { arguments = bundle }
        }
    }

}