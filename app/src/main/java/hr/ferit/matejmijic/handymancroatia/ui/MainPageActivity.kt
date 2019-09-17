package hr.ferit.matejmijic.handymancroatia.ui

import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import hr.ferit.matejmijic.handymancroatia.R
import hr.ferit.matejmijic.handymancroatia.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main_page.*

class MainPageActivity: BaseActivity() {
    private lateinit var auth: FirebaseAuth

    override fun getLayoutResourceId(): Int = R.layout.activity_main_page

    override fun initUi() {
        auth = FirebaseAuth.getInstance()

        logut.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            startActivity(intent)
            finish()
        }
    }
}