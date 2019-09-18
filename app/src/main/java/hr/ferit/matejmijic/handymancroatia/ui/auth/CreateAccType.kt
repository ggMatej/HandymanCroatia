package hr.ferit.matejmijic.handymancroatia.ui.auth

import android.widget.Toast
import hr.ferit.matejmijic.handymancroatia.HandymanApp
import hr.ferit.matejmijic.handymancroatia.R
import hr.ferit.matejmijic.handymancroatia.common.showFragment
import hr.ferit.matejmijic.handymancroatia.persistence.CreateAccPrefs
import hr.ferit.matejmijic.handymancroatia.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_createacc_type.*

class CreateAccType: BaseFragment() {
    override fun getLayoutResourceId(): Int = R.layout.fragment_createacc_type

    override fun initUi() {

        if(CreateAccPrefs.getString(CreateAccPrefs.KEY_ACCOUNT_TYPE,"") == "business"){
            rb_business.isChecked = true
        } else {
            rb_default.isChecked = true
        }

        btn_next.setOnClickListener {
            when {
                rb_business.isChecked -> {
                    CreateAccPrefs.store(CreateAccPrefs.KEY_ACCOUNT_TYPE,"business")
                    activity?.showFragment(R.id.fragmentContainer,CreateAccSelectJobs(),true)
                }
                rb_default.isChecked -> {
                    CreateAccPrefs.store(CreateAccPrefs.KEY_ACCOUNT_TYPE,"default")
                    activity?.showFragment(R.id.fragmentContainer, CreateAccEmail(), true)
                }
                else -> {
                    Toast.makeText(HandymanApp.getAppContext(), "Please choose account type!", Toast.LENGTH_LONG).show()
                }
            }
        }

        btn_back.setOnClickListener {
            activity?.onBackPressed()
        }
    }
}