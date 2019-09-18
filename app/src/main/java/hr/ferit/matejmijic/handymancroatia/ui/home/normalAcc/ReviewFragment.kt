package hr.ferit.matejmijic.handymancroatia.ui.home.normalAcc

import android.os.Bundle
import android.widget.Toast
import hr.ferit.matejmijic.handymancroatia.HandymanApp
import hr.ferit.matejmijic.handymancroatia.R
import hr.ferit.matejmijic.handymancroatia.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_review.*

class ReviewFragment: BaseFragment() {
    private var title = ""
    private var review = ""

    override fun getLayoutResourceId(): Int = R.layout.fragment_review

    override fun initUi() {
        arguments?.getString(EXTRA_REVIEW_TITLE)?.let { title = it }
        arguments?.getString(EXTRA_REVIEW_TEXT)?.let { review = it }
        tv_review_title.text = title
        tv_review_text.text = review
        Toast.makeText(HandymanApp.getAppContext(),review,Toast.LENGTH_LONG).show()
    }

    companion object{
        fun newInstance(title: String, review: String): ReviewFragment {
            val bundle = Bundle().apply {
                putString(EXTRA_REVIEW_TITLE, title)
                putString(EXTRA_REVIEW_TEXT, review)
            }
            return ReviewFragment()
                .apply { arguments = bundle }
        }
    }
}