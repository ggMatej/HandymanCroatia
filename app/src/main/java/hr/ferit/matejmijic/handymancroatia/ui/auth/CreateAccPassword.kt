package hr.ferit.matejmijic.handymancroatia.ui.auth

import android.widget.Toast
import hr.ferit.matejmijic.handymancroatia.HandymanApp
import hr.ferit.matejmijic.handymancroatia.R
import hr.ferit.matejmijic.handymancroatia.common.isValidPassword
import hr.ferit.matejmijic.handymancroatia.common.showFragment
import hr.ferit.matejmijic.handymancroatia.persistence.CreateAccPrefs
import hr.ferit.matejmijic.handymancroatia.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_createacc_password.*

class CreateAccPassword: BaseFragment() {
    override fun getLayoutResourceId(): Int = R.layout.fragment_createacc_password

    override fun initUi() {
        et_password.setText(CreateAccPrefs.getString(CreateAccPrefs.KEY_PASSWORD, ""))

        btn_next.setOnClickListener {
            if (et_password.text.toString().isValidPassword()){
                CreateAccPrefs.store(CreateAccPrefs.KEY_PASSWORD, et_password.text.toString())
                activity?.showFragment(R.id.fragmentContainer, CreateAccNickname(), true)

            } else {
                Toast.makeText(HandymanApp.getAppContext(),"Password must be 6 letters/numbers or more!", Toast.LENGTH_SHORT).show()
            }
        }

        btn_back.setOnClickListener {
            activity?.onBackPressed()
        }

    }
}