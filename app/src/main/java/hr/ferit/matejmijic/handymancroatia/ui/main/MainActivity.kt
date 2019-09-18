package hr.ferit.matejmijic.handymancroatia.ui.main

import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import hr.ferit.matejmijic.handymancroatia.R
import hr.ferit.matejmijic.handymancroatia.common.showFragment
import hr.ferit.matejmijic.handymancroatia.persistence.CreateAccPrefs
import hr.ferit.matejmijic.handymancroatia.ui.auth.LoginFragment
import hr.ferit.matejmijic.handymancroatia.ui.base.BaseActivity
import hr.ferit.matejmijic.handymancroatia.ui.home.normalAcc.MainPageActivity
import hr.ferit.matejmijic.handymancroatia.ui.home.businessAcc.MainPageBusinessActivity


class MainActivity : BaseActivity() {

    private lateinit var auth: FirebaseAuth
    override fun getLayoutResourceId(): Int = R.layout.activity_main

    override fun initUi() {
        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null){
            if(CreateAccPrefs.getString(CreateAccPrefs.KEY_ACCOUNT_TYPE,"")=="default"){
                val intent = Intent(this, MainPageActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                startActivity(intent)
                finish()
            }else {
                val intent = Intent(this, MainPageBusinessActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                startActivity(intent)
                finish()
            }


        } else {
//                showFragment(R.id.fragmentContainer,
//                    FrontPageFragment(),false)
            showFragment(R.id.fragmentContainer,
                LoginFragment(),false)
        }

    }




}
