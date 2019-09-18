package hr.ferit.matejmijic.handymancroatia.ui.home.normalAcc

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import hr.ferit.matejmijic.handymancroatia.R

class PagerAdapter(fm: FragmentManager, context: Context,id: String): FragmentPagerAdapter(fm) {

    private var mContext = context
    private var id = id

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> BusinessDetailFragment.newInstance(id)
            1 -> BusinessRatingFragment.newInstance(id)
            else -> FrontPageFragment.newInstance(id)
        }
    }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence{
        when(position){
            0 ->return  mContext.getString(R.string.tab_business_details)
            1 ->return  mContext.getString(R.string.tab_business_rating)
        }
        return ""
    }


}