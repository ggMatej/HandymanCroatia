package hr.ferit.matejmijic.handymancroatia.ui.home.normalAcc

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import hr.ferit.matejmijic.handymancroatia.R
import hr.ferit.matejmijic.handymancroatia.model.BusinessUser
import hr.ferit.matejmijic.handymancroatia.persistence.BusinessUserRepo
import hr.ferit.matejmijic.handymancroatia.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_front_page.*

const val EXTRA_USER_ID = "extra_task_id"

class FrontPageFragment: BaseFragment() {
    private lateinit var  auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private val repository = BusinessUserRepo
    private val adapter by lazy { FrontPageAdapter{ onItemSelected(it) }}

    private fun onItemSelected(businessUser: BusinessUser) {
        val detailsIntent = Intent(context, ContainerActivity::class.java).apply {
            putExtra(EXTRA_USER_ID, businessUser.userId)
        }
        startActivity(detailsIntent)
    }

    override fun getLayoutResourceId(): Int =
        R.layout.fragment_front_page

    override fun initUi() {
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        frontPageRecyclerView.layoutManager = LinearLayoutManager(context)
        frontPageRecyclerView.adapter = adapter
        getBusinessUsers()

    }

    private fun getBusinessUsers() {
        val businessUsers = db.collection("businessUsers")
        businessUsers.get().addOnSuccessListener { it ->
            if (!it.isEmpty){
                val users = it.toObjects(BusinessUser::class.java)
                adapter.setData(users)
                repository.deleteALL()
                users.forEach {
                    repository.add(it)
                }
            }
        }
    }


}