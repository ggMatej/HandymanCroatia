package hr.ferit.matejmijic.handymancroatia

import com.google.firebase.auth.FirebaseAuth
import hr.ferit.matejmijic.handymancroatia.common.showFragment
import hr.ferit.matejmijic.handymancroatia.ui.auth.LoginFragment
import hr.ferit.matejmijic.handymancroatia.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_front_page.*

class FrontPageFragment: BaseFragment() {
    private lateinit var  auth: FirebaseAuth

    override fun getLayoutResourceId(): Int = R.layout.fragment_front_page

    override fun initUi() {
        auth = FirebaseAuth.getInstance()
        logout.setOnClickListener {
            auth.signOut()
            activity?.showFragment(R.id.fragmentContainer, LoginFragment(), false)
        }

        textView.text = auth.currentUser?.email
    }
}