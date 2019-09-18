package hr.ferit.matejmijic.handymancroatia.ui.home

import android.os.Bundle
import hr.ferit.matejmijic.handymancroatia.R
import hr.ferit.matejmijic.handymancroatia.ui.base.BaseFragment
import hr.ferit.matejmijic.handymancroatia.ui.home.normalAcc.EXTRA_USER_ID
import hr.ferit.matejmijic.handymancroatia.ui.home.normalAcc.PagerAdapter
import kotlinx.android.synthetic.main.fragment_view_pager.*

class ViewPagerFragment: BaseFragment() {

    private var businessUserId = "No user"
    override fun getLayoutResourceId(): Int = R.layout.fragment_view_pager

    override fun initUi() {
        arguments?.getString(EXTRA_USER_ID)?.let { businessUserId = it }
        viewPager.adapter = PagerAdapter(childFragmentManager, context!!, businessUserId)
        tabs.setupWithViewPager(viewPager)
    }


    companion object{
        fun newInstance(uId: String): ViewPagerFragment {
            val bundle = Bundle().apply { putString(EXTRA_USER_ID, uId) }
            return ViewPagerFragment().apply { arguments = bundle }
        }
    }
}