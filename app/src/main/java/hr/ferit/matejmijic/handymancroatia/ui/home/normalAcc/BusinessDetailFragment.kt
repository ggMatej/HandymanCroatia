package hr.ferit.matejmijic.handymancroatia.ui.home.normalAcc

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import hr.ferit.matejmijic.handymancroatia.HandymanApp
import hr.ferit.matejmijic.handymancroatia.R
import hr.ferit.matejmijic.handymancroatia.persistence.BusinessUserRepo
import hr.ferit.matejmijic.handymancroatia.persistence.CreateAccPrefs
import hr.ferit.matejmijic.handymancroatia.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_business_detail.*
import kotlinx.android.synthetic.main.fragment_business_user_profile.tv_accType
import kotlinx.android.synthetic.main.fragment_business_user_profile.tv_email
import kotlinx.android.synthetic.main.fragment_business_user_profile.tv_job
import kotlinx.android.synthetic.main.fragment_business_user_profile.tv_location
import kotlinx.android.synthetic.main.fragment_business_user_profile.tv_phonenum
import kotlinx.android.synthetic.main.fragment_business_user_profile.tv_territory
import kotlinx.android.synthetic.main.fragment_createacc_location.tv_nickname
import kotlinx.android.synthetic.main.item_user.*
import kotlinx.android.synthetic.main.item_user.view.*

class BusinessDetailFragment: BaseFragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private var businessUserId = "No user"
    override fun getLayoutResourceId(): Int = R.layout.fragment_business_detail

    override fun initUi() {
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        initListeners()
        arguments?.getString(EXTRA_USER_ID)?.let { businessUserId = it }
        displayBusinessUser(businessUserId)
    }

    private fun initListeners() {
        ib_phone.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_DIAL, Uri.parse(
                    "tel:${
                    BusinessUserRepo.get(businessUserId).phoneNumber}"
                )
            )
            startActivity(intent)
        }


        ib_map.setOnClickListener {
            val mapUri = Uri.parse(BusinessUserRepo.get(businessUserId).location)
            val intent = Intent(Intent.ACTION_VIEW, mapUri)
            intent.setPackage("com.google.android.apps.maps")
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(HandymanApp.getAppContext(), "No address", Toast.LENGTH_LONG).show()
            }
        }




        sendOffer.setOnClickListener {
           // Toast.makeText(HandymanApp.getAppContext(),CreateAccPrefs.getString(CreateAccPrefs.KEY_NICKNAME,""),Toast.LENGTH_LONG).show()
            val offer = hashMapOf(
                "userId" to auth.uid,
                "nickname" to CreateAccPrefs.getString(CreateAccPrefs.KEY_NICKNAME,"")
            )
            db.collection("businessUsers").document(businessUserId).collection("offers").document(auth.uid!!).set(offer)
        }



    }




    private fun displayBusinessUser(businessUserId: String) {
        try {
            val user = BusinessUserRepo.get(businessUserId)
            tv_nickname.text = user.nickname
            tv_territory.text = user.workTerritory
            tv_accType.text = user.accType
            tv_job.text = user.jobType
            tv_email.text = user.email
            if (user.location=="No address"){
                tv_location.text = getString(R.string.address_not_available)
            } else {
                tv_location.text = getString(R.string.address_available)
            }
            tv_phonenum.text = user.phoneNumber
        }catch (e: NoSuchElementException){
            Toast.makeText(context, "No such user found",Toast.LENGTH_LONG).show()
        }

    }

    companion object{
        fun newInstance(uId: String): BusinessDetailFragment {
            val bundle = Bundle().apply { putString(EXTRA_USER_ID, uId) }
            return BusinessDetailFragment().apply { arguments = bundle }
        }
    }
}