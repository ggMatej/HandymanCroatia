package hr.ferit.matejmijic.handymancroatia.ui.home.normalAcc

import android.os.Bundle
import android.widget.Toast
import hr.ferit.matejmijic.handymancroatia.R
import hr.ferit.matejmijic.handymancroatia.persistence.BusinessUserRepo
import hr.ferit.matejmijic.handymancroatia.persistence.CreateAccPrefs
import hr.ferit.matejmijic.handymancroatia.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_business_user_profile.*
import kotlinx.android.synthetic.main.fragment_business_user_profile.tv_accType
import kotlinx.android.synthetic.main.fragment_business_user_profile.tv_email
import kotlinx.android.synthetic.main.fragment_business_user_profile.tv_location
import kotlinx.android.synthetic.main.fragment_business_user_profile.tv_phonenum
import kotlinx.android.synthetic.main.fragment_createacc_location.*
import kotlinx.android.synthetic.main.fragment_createacc_location.tv_nickname
import kotlinx.android.synthetic.main.fragment_user_profile.*

class BusinessDetailFragment: BaseFragment() {

    private var businessUserId = "No user"
    override fun getLayoutResourceId(): Int = R.layout.fragment_business_detail

    override fun initUi() {
        arguments?.getString(EXTRA_TASK_ID)?.let { businessUserId = it }
        displayBusinessUser(businessUserId)
    }

    private fun displayBusinessUser(businessUserId: String) {
        try {
            val user = BusinessUserRepo.get(businessUserId)
            tv_nickname.text = user.nickname
            tv_territory.text = user.workTerritory
            tv_accType.text = user.accType
            tv_job.text = user.jobType
            tv_email.text = user.email
            if (user.location=="No address"){
                tv_location.text = getString(R.string.address_not_available)
            } else {
                tv_location.text = getString(R.string.address_available)
            }
            tv_phonenum.text = user.phoneNumber
        }catch (e: NoSuchElementException){
            Toast.makeText(context, "No such user found",Toast.LENGTH_LONG).show()
        }

    }

    companion object{
        fun newInstance(uId: String): BusinessDetailFragment {
            val bundle = Bundle().apply { putString(EXTRA_TASK_ID, uId) }
            return BusinessDetailFragment().apply { arguments = bundle }
        }
    }
}