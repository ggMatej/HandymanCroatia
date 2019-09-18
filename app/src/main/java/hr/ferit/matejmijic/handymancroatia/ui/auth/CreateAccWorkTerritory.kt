package hr.ferit.matejmijic.handymancroatia.ui.auth

import hr.ferit.matejmijic.handymancroatia.R
import hr.ferit.matejmijic.handymancroatia.common.showFragment
import hr.ferit.matejmijic.handymancroatia.persistence.CreateAccPrefs
import hr.ferit.matejmijic.handymancroatia.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_createacc_selectjobs.*
import kotlinx.android.synthetic.main.fragment_createacc_workterritory.*
import kotlinx.android.synthetic.main.fragment_createacc_workterritory.btn_back
import kotlinx.android.synthetic.main.fragment_createacc_workterritory.btn_next

class CreateAccWorkTerritory: BaseFragment() {
    override fun getLayoutResourceId(): Int = R.layout.fragment_createacc_workterritory

    override fun initUi() {
        btn_next.setOnClickListener {
            when{
                rb_istarska.isChecked->{
                    CreateAccPrefs.store(CreateAccPrefs.KEY_WORK_TERRITORY,"Istarska županija")
                    activity?.showFragment(R.id.fragmentContainer, CreateAccEmail(), true)
                }
                rb_osjecka.isChecked->{
                    CreateAccPrefs.store(CreateAccPrefs.KEY_WORK_TERRITORY,"Osječko-baranjska županija")
                    activity?.showFragment(R.id.fragmentContainer, CreateAccEmail(), true)
                }
                rb_karlovacka.isChecked->{
                    CreateAccPrefs.store(CreateAccPrefs.KEY_WORK_TERRITORY,"Karlovačka županija")
                    activity?.showFragment(R.id.fragmentContainer, CreateAccEmail(), true)
                }
                rb_nedefinirano.isChecked->{
                    CreateAccPrefs.store(CreateAccPrefs.KEY_WORK_TERRITORY,"Work territory not defined")
                    activity?.showFragment(R.id.fragmentContainer, CreateAccEmail(), true)
                }
                rb_zagreb.isChecked->{
                    CreateAccPrefs.store(CreateAccPrefs.KEY_WORK_TERRITORY,"Grad Zagreb")
                    activity?.showFragment(R.id.fragmentContainer, CreateAccEmail(), true)
                }
                rb_zagrebacka.isChecked->{
                    CreateAccPrefs.store(CreateAccPrefs.KEY_WORK_TERRITORY,"Zagrebačka županija")
                    activity?.showFragment(R.id.fragmentContainer, CreateAccEmail(), true)
                }
                rb_pozeska.isChecked->{
                    CreateAccPrefs.store(CreateAccPrefs.KEY_WORK_TERRITORY,"Požeško-slavonska županija")
                    activity?.showFragment(R.id.fragmentContainer, CreateAccEmail(), true)
                }
                rb_primorska.isChecked->{
                    CreateAccPrefs.store(CreateAccPrefs.KEY_WORK_TERRITORY,"Primorsko-goranska županija")
                    activity?.showFragment(R.id.fragmentContainer, CreateAccEmail(), true)
                }
                rb_splitksa.isChecked->{
                    CreateAccPrefs.store(CreateAccPrefs.KEY_WORK_TERRITORY,"Splitsko-dalmatinska županija")
                    activity?.showFragment(R.id.fragmentContainer, CreateAccEmail(), true)
                }
                rb_varazdinska.isChecked->{
                    CreateAccPrefs.store(CreateAccPrefs.KEY_WORK_TERRITORY,"Varaždinska županija")
                    activity?.showFragment(R.id.fragmentContainer, CreateAccEmail(), true)
                }
            }
        }

        btn_back.setOnClickListener {
            activity?.onBackPressed()
        }
    }
}