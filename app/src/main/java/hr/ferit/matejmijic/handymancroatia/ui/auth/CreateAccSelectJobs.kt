package hr.ferit.matejmijic.handymancroatia.ui.auth

import hr.ferit.matejmijic.handymancroatia.R
import hr.ferit.matejmijic.handymancroatia.common.showFragment
import hr.ferit.matejmijic.handymancroatia.persistence.CreateAccPrefs
import hr.ferit.matejmijic.handymancroatia.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_createacc_selectjobs.*
import kotlinx.android.synthetic.main.fragment_createacc_selectjobs.btn_back
import kotlinx.android.synthetic.main.fragment_createacc_selectjobs.btn_next

class CreateAccSelectJobs: BaseFragment() {
    override fun getLayoutResourceId(): Int = R.layout.fragment_createacc_selectjobs

    override fun initUi() {
        val jobList: MutableSet<String> = mutableSetOf()

        btn_next.setOnClickListener {
            when{
                rb_administrative.isChecked->{
                    CreateAccPrefs.store(CreateAccPrefs.KEY_JOB_TYPE,"Administrative professions")
                    activity?.showFragment(R.id.fragmentContainer,CreateAccWorkTerritory(),true)
                }
                rb_architecture.isChecked->{
                    CreateAccPrefs.store(CreateAccPrefs.KEY_JOB_TYPE,"Architecture")
                    activity?.showFragment(R.id.fragmentContainer,CreateAccWorkTerritory(),true)
                }
                rb_banking.isChecked->{
                    CreateAccPrefs.store(CreateAccPrefs.KEY_JOB_TYPE,"Banking")
                    activity?.showFragment(R.id.fragmentContainer,CreateAccWorkTerritory(),true)
                }
                rb_construction.isChecked->{
                    CreateAccPrefs.store(CreateAccPrefs.KEY_JOB_TYPE,"Construction")
                    activity?.showFragment(R.id.fragmentContainer,CreateAccWorkTerritory(),true)
                }
                rb_design.isChecked->{
                    CreateAccPrefs.store(CreateAccPrefs.KEY_JOB_TYPE,"Design")
                    activity?.showFragment(R.id.fragmentContainer,CreateAccWorkTerritory(),true)
                }
                rb_electrical.isChecked->{
                    CreateAccPrefs.store(CreateAccPrefs.KEY_JOB_TYPE,"Electrical engineering")
                    activity?.showFragment(R.id.fragmentContainer,CreateAccWorkTerritory(),true)
                }
                rb_finances.isChecked->{
                    CreateAccPrefs.store(CreateAccPrefs.KEY_JOB_TYPE,"Finances")
                    activity?.showFragment(R.id.fragmentContainer,CreateAccWorkTerritory(),true)
                }
                rb_forestry.isChecked->{
                    CreateAccPrefs.store(CreateAccPrefs.KEY_JOB_TYPE,"Forestry")
                    activity?.showFragment(R.id.fragmentContainer,CreateAccWorkTerritory(),true)
                }
                rb_installations.isChecked->{
                    CreateAccPrefs.store(CreateAccPrefs.KEY_JOB_TYPE,"Installations, maintenance and repairs")
                    activity?.showFragment(R.id.fragmentContainer,CreateAccWorkTerritory(),true)
                }
                rb_management.isChecked->{
                    CreateAccPrefs.store(CreateAccPrefs.KEY_JOB_TYPE,"Management")
                    activity?.showFragment(R.id.fragmentContainer,CreateAccWorkTerritory(),true)
                }
                rb_security.isChecked->{
                    CreateAccPrefs.store(CreateAccPrefs.KEY_JOB_TYPE,"Security")
                    activity?.showFragment(R.id.fragmentContainer,CreateAccWorkTerritory(),true)
                }
                rb_tellecommunications.isChecked->{
                    CreateAccPrefs.store(CreateAccPrefs.KEY_JOB_TYPE,"Telecommunications")
                    activity?.showFragment(R.id.fragmentContainer,CreateAccWorkTerritory(),true)
                }
                rb_transportation.isChecked->{
                    CreateAccPrefs.store(CreateAccPrefs.KEY_JOB_TYPE,"Transportation")
                    activity?.showFragment(R.id.fragmentContainer,CreateAccWorkTerritory(),true)
                }
                rb_taxi.isChecked->{
                    CreateAccPrefs.store(CreateAccPrefs.KEY_JOB_TYPE,"Taxi services")
                    activity?.showFragment(R.id.fragmentContainer,CreateAccWorkTerritory(),true)
                }

            }

        }

        btn_back.setOnClickListener {
            activity?.onBackPressed()
        }
    }
}