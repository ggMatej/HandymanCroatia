package hr.ferit.matejmijic.handymancroatia.ui.auth

import android.widget.Toast
import hr.ferit.matejmijic.handymancroatia.HandymanApp
import hr.ferit.matejmijic.handymancroatia.R
import hr.ferit.matejmijic.handymancroatia.common.isValidEmail
import hr.ferit.matejmijic.handymancroatia.common.showFragment
import hr.ferit.matejmijic.handymancroatia.persistence.CreateAccPrefs
import hr.ferit.matejmijic.handymancroatia.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_createacc_email.*

class CreateAccEmail: BaseFragment() {
    override fun getLayoutResourceId(): Int = R.layout.fragment_createacc_email

    override fun initUi() {
        et_email.setText(CreateAccPrefs.getString(CreateAccPrefs.KEY_EMAIL, ""))

        btn_next.setOnClickListener {
            if (et_email.text.toString().isValidEmail()){
                CreateAccPrefs.store(CreateAccPrefs.KEY_EMAIL,et_email.text.toString())
                activity?.showFragment(R.id.fragmentContainer, CreateAccPassword(), true)
            } else {
            Toast.makeText(HandymanApp.getAppContext(), "Please enter email!", Toast.LENGTH_SHORT).show()
            }
        }

        btn_back.setOnClickListener {
            activity?.onBackPressed()
        }
    }
}