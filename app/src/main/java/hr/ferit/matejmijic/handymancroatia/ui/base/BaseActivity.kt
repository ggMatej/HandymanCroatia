package hr.ferit.matejmijic.handymancroatia.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(getLayoutResourceId())
        initUi()
    }

    abstract fun getLayoutResourceId(): Int

    abstract fun initUi()
}