package hr.ferit.matejmijic.handymancroatia.ui.home

import com.google.firebase.auth.FirebaseAuth
import hr.ferit.matejmijic.handymancroatia.R
import hr.ferit.matejmijic.handymancroatia.ui.base.BaseFragment

class FrontPageFragment: BaseFragment() {
    private lateinit var  auth: FirebaseAuth

    override fun getLayoutResourceId(): Int =
        R.layout.fragment_front_page

    override fun initUi() {
    }




}