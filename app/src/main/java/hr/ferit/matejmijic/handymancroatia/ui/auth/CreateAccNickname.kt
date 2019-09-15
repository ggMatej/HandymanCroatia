package hr.ferit.matejmijic.handymancroatia.ui.auth

import android.widget.Toast
import hr.ferit.matejmijic.handymancroatia.HandymanApp
import hr.ferit.matejmijic.handymancroatia.R
import hr.ferit.matejmijic.handymancroatia.common.isValidNickname
import hr.ferit.matejmijic.handymancroatia.common.showFragment
import hr.ferit.matejmijic.handymancroatia.persistence.CreateAccPrefs
import hr.ferit.matejmijic.handymancroatia.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_createacc_nickname.*
import kotlinx.android.synthetic.main.fragment_createacc_nickname.btn_back
import kotlinx.android.synthetic.main.fragment_createacc_nickname.btn_next

class CreateAccNickname: BaseFragment() {
    override fun getLayoutResourceId(): Int = R.layout.fragment_createacc_nickname

    override fun initUi() {
        et_nickname.setText(CreateAccPrefs.getString(CreateAccPrefs.KEY_NICKNAME, ""))

        btn_next.setOnClickListener {
            if (et_nickname.text.toString().isValidNickname()){
                CreateAccPrefs.store(CreateAccPrefs.KEY_NICKNAME, et_nickname.text.toString())
                activity?.showFragment(R.id.fragmentContainer, CreateAccPhoneNumber(), true)
            } else {
                Toast.makeText(HandymanApp.getAppContext(),"Nickname must be at least two letters long!", Toast.LENGTH_SHORT).show()
            }
        }

        btn_back.setOnClickListener {
            activity?.onBackPressed()
        }
    }
}