package hr.ferit.matejmijic.handymancroatia.ui.home.businessAcc

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import hr.ferit.matejmijic.handymancroatia.HandymanApp
import hr.ferit.matejmijic.handymancroatia.R
import hr.ferit.matejmijic.handymancroatia.model.NormalUser
import hr.ferit.matejmijic.handymancroatia.persistence.BusinessUserRepo
import hr.ferit.matejmijic.handymancroatia.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_business_detail.*
import kotlinx.android.synthetic.main.fragment_offer_profile.*
import kotlinx.android.synthetic.main.fragment_offer_profile.ib_map
import kotlinx.android.synthetic.main.fragment_offer_profile.ib_phone
import kotlinx.android.synthetic.main.fragment_offer_profile.tv_accType
import kotlinx.android.synthetic.main.fragment_offer_profile.tv_email
import kotlinx.android.synthetic.main.fragment_offer_profile.tv_location
import kotlinx.android.synthetic.main.fragment_offer_profile.tv_nickname
import kotlinx.android.synthetic.main.fragment_offer_profile.tv_phonenum

class OfferProfileFragment: BaseFragment() {
    private var normalUserId = "null"
    private var phone = ""
    private var location = ""
    private lateinit var  auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun getLayoutResourceId(): Int = R.layout.fragment_offer_profile

    override fun initUi() {
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        arguments?.getString(EXTRA_USER_ID)?.let { normalUserId = it }
        displayUser(normalUserId)
        initListeners()
    }

    private fun initListeners() {
        ib_phone.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_DIAL, Uri.parse(
                    "tel:${phone}"
                )
            )
            startActivity(intent)
        }


        ib_map.setOnClickListener {
            val mapUri = Uri.parse(location)
            val intent = Intent(Intent.ACTION_VIEW, mapUri)
            intent.setPackage("com.google.android.apps.maps")
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(HandymanApp.getAppContext(), "No address", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun displayUser(userId: String) {
        db.collection("normalUsers").document(userId).get()
                .addOnSuccessListener {
                    if (it.exists()){
                        val user = it.toObject(NormalUser::class.java)

                        if(user?.location != "No address"){
                            tv_location.text = getString(R.string.address_available)
                        }else tv_location.text = getString(R.string.address_not_available)
                        tv_accType.text = user?.accType
                        tv_email.text = user?.email
                        tv_phonenum.text = user?.phoneNumber
                        tv_nickname.text = user?.nickname
                        phone = user?.phoneNumber.toString()
                        location = user?.location.toString()
                    }else{
                        Toast.makeText(HandymanApp.getAppContext(),"No user",Toast.LENGTH_LONG).show()
                    }
                }
    }

    companion object{
        fun newInstance(uId: String): OfferProfileFragment {
            val bundle = Bundle().apply { putString(EXTRA_USER_ID, uId) }
            return OfferProfileFragment()
                .apply { arguments = bundle }
        }
    }
}