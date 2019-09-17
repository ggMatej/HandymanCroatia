package hr.ferit.matejmijic.handymancroatia.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import hr.ferit.matejmijic.handymancroatia.FrontPageFragment
import hr.ferit.matejmijic.handymancroatia.R
import hr.ferit.matejmijic.handymancroatia.UserProfileFragment
import hr.ferit.matejmijic.handymancroatia.common.hasPermissionCompat
import hr.ferit.matejmijic.handymancroatia.common.showFragment
import hr.ferit.matejmijic.handymancroatia.ui.auth.LoginFragment
import hr.ferit.matejmijic.handymancroatia.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_front_page.*

const val EXTRA_UID = ""

class MainActivity : BaseActivity() {

    private lateinit var auth: FirebaseAuth
    override fun getLayoutResourceId(): Int = R.layout.activity_main

    override fun initUi() {
        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null){
            val intent = Intent(this, MainPageActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            startActivity(intent)
            finish()

        } else {
//                showFragment(R.id.fragmentContainer,
//                    FrontPageFragment(),false)
            showFragment(R.id.fragmentContainer, LoginFragment(),false)
        }

    }




}
