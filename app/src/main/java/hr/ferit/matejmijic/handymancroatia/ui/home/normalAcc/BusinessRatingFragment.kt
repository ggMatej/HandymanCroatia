package hr.ferit.matejmijic.handymancroatia.ui.home.normalAcc

import android.os.Bundle
import hr.ferit.matejmijic.handymancroatia.R
import hr.ferit.matejmijic.handymancroatia.ui.base.BaseFragment
import hr.ferit.matejmijic.handymancroatia.ui.home.ViewPagerFragment

class BusinessRatingFragment: BaseFragment() {
    override fun getLayoutResourceId(): Int = R.layout.fragment_business_rating

    override fun initUi() {

    }

    companion object{
        fun newInstance(uId: String): BusinessRatingFragment {
            val bundle = Bundle().apply { putString(EXTRA_USER_ID, uId) }
            return BusinessRatingFragment().apply { arguments = bundle }
        }
    }
}