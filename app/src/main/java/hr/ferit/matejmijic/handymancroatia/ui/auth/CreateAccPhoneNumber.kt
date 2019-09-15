package hr.ferit.matejmijic.handymancroatia.ui.auth

import android.widget.Toast
import hr.ferit.matejmijic.handymancroatia.HandymanApp
import hr.ferit.matejmijic.handymancroatia.R
import hr.ferit.matejmijic.handymancroatia.common.EMPTY_STRING
import hr.ferit.matejmijic.handymancroatia.common.isValidNickname
import hr.ferit.matejmijic.handymancroatia.common.showFragment
import hr.ferit.matejmijic.handymancroatia.persistence.CreateAccPrefs
import hr.ferit.matejmijic.handymancroatia.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_createacc_phonenumber.*

class CreateAccPhoneNumber: BaseFragment() {
    override fun getLayoutResourceId(): Int = R.layout.fragment_createacc_phonenumber

    override fun initUi() {
        et_phonenum.setText(CreateAccPrefs.getString(CreateAccPrefs.KEY_PHONENUM, ""))

        btn_next.setOnClickListener {
            if (et_phonenum.text.toString() == EMPTY_STRING){
                Toast.makeText(HandymanApp.getAppContext(),"Nickname must be at least two letters long!", Toast.LENGTH_SHORT).show()
            } else {
                CreateAccPrefs.store(CreateAccPrefs.KEY_PHONENUM, et_phonenum.text.toString())
                activity?.showFragment(R.id.fragmentContainer, CreateAccLocation(), true)
            }
        }

        btn_back.setOnClickListener {
            activity?.onBackPressed()
        }
    }
}