package hr.ferit.matejmijic.handymancroatia.ui.home.businessAcc

import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import hr.ferit.matejmijic.handymancroatia.HandymanApp
import hr.ferit.matejmijic.handymancroatia.R
import hr.ferit.matejmijic.handymancroatia.common.showFragment
import hr.ferit.matejmijic.handymancroatia.model.NormalUser
import hr.ferit.matejmijic.handymancroatia.model.Offer
import hr.ferit.matejmijic.handymancroatia.ui.base.BaseFragment
import hr.ferit.matejmijic.handymancroatia.ui.home.normalAcc.recyclerview.BusinessFrontPageAdapter
import hr.ferit.matejmijic.handymancroatia.ui.home.normalAcc.recyclerview.FrontPageAdapter
import kotlinx.android.synthetic.main.fragment_front_page.*

const val EXTRA_USER_ID = "extra_task_id"

class BusinessFrontPageFragment: BaseFragment() {

    private lateinit var  auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private val adapter by lazy {
        BusinessFrontPageAdapter {
            onItemSelected(
                it
            )
        }
    }

    private fun onItemSelected(offer: Offer) {
        activity?.showFragment(R.id.mainPageFragmentContainer,OfferProfileFragment.newInstance(offer.userId.toString()),true)
    }

    override fun getLayoutResourceId(): Int = R.layout.fragment_business_front_page

    override fun initUi() {
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        frontPageRecyclerView.layoutManager = LinearLayoutManager(context)
        frontPageRecyclerView.adapter = adapter
        getOffers()
    }

    private fun getOffers() {
        db.collection("businessUsers").document(auth.currentUser!!.uid).collection("offers").get()
            .addOnSuccessListener {
                if (!it.isEmpty){
                    val offer = it.toObjects(Offer::class.java)
                    adapter.setData(offer)
                }else {
                    Toast.makeText(HandymanApp.getAppContext(),"No offers",Toast.LENGTH_LONG).show()
                }
            }
    }
}