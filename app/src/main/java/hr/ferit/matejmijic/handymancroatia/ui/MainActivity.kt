package hr.ferit.matejmijic.handymancroatia.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import hr.ferit.matejmijic.handymancroatia.FrontPageFragment
import hr.ferit.matejmijic.handymancroatia.R
import hr.ferit.matejmijic.handymancroatia.common.hasPermissionCompat
import hr.ferit.matejmijic.handymancroatia.common.showFragment
import hr.ferit.matejmijic.handymancroatia.ui.auth.LoginFragment
import hr.ferit.matejmijic.handymancroatia.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {


    override fun getLayoutResourceId(): Int = R.layout.activity_main

    override fun initUi() {
        showFragment(R.id.fragmentContainer, LoginFragment(), false)
    }




}
