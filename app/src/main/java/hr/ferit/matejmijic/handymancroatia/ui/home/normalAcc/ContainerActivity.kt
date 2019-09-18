package hr.ferit.matejmijic.handymancroatia.ui.home.normalAcc

import hr.ferit.matejmijic.handymancroatia.R
import hr.ferit.matejmijic.handymancroatia.common.showFragment
import hr.ferit.matejmijic.handymancroatia.ui.base.BaseActivity

class ContainerActivity: BaseActivity() {
    override fun getLayoutResourceId(): Int = R.layout.activity_main


    override fun initUi() {
        val id = intent.getStringExtra(EXTRA_USER_ID)
        showFragment(R.id.fragmentContainer, BusinessDetailFragment.newInstance(id), false)
    }
}