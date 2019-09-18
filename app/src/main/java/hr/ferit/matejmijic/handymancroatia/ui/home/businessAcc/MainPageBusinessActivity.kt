package hr.ferit.matejmijic.handymancroatia.ui.home.businessAcc

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import hr.ferit.matejmijic.handymancroatia.R
import hr.ferit.matejmijic.handymancroatia.common.showFragment
import hr.ferit.matejmijic.handymancroatia.model.BusinessUser
import hr.ferit.matejmijic.handymancroatia.persistence.CreateAccPrefs
import hr.ferit.matejmijic.handymancroatia.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main_page.*

class MainPageBusinessActivity: BaseActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    override fun getLayoutResourceId(): Int = R.layout.activity_main_page


    override fun initUi() {
        getUser()
        setUpNavigationListener()
        showFragment(R.id.mainPageFragmentContainer,
            BusinessFrontPageFragment(), false)
    }

    private fun setUpNavigationListener() {
        bottom_navigation.setOnNavigationItemSelectedListener{

            when(it.itemId){
                R.id.nav_home ->{
                    showFragment(R.id.mainPageFragmentContainer,
                        BusinessFrontPageFragment(), false)
                }

                R.id.nav_profile ->{
                    showFragment(R.id.mainPageFragmentContainer,
                        BusinessUserProfileFragment(), false)
                }
            }
            return@setOnNavigationItemSelectedListener true
        }

    }

    private fun getUser() {
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        val docRef = auth.uid?.let { db.collection("businessUsers").document(it) }
        docRef?.get()?.addOnSuccessListener {documentSnapshot ->
            val user = documentSnapshot.toObject(BusinessUser::class.java)
            user?.email?.let { CreateAccPrefs.store(CreateAccPrefs.KEY_EMAIL, it) }
            user?.nickname?.let { CreateAccPrefs.store(CreateAccPrefs.KEY_NICKNAME, it) }
            user?.location?.let { CreateAccPrefs.store(CreateAccPrefs.KEY_ACCOUNT_ADDRESS, it) }
            user?.phoneNumber?.let { CreateAccPrefs.store(CreateAccPrefs.KEY_PHONENUM, it) }
            user?.accType?.let { CreateAccPrefs.store(CreateAccPrefs.KEY_ACCOUNT_TYPE, it) }
            user?.jobType?.let { CreateAccPrefs.store(CreateAccPrefs.KEY_JOB_TYPE, it) }
            user?.workTerritory?.let { CreateAccPrefs.store(CreateAccPrefs.KEY_WORK_TERRITORY, it) }
        }
    }
}