package hr.ferit.matejmijic.handymancroatia.ui.home.normalAcc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import hr.ferit.matejmijic.handymancroatia.HandymanApp
import hr.ferit.matejmijic.handymancroatia.R
import hr.ferit.matejmijic.handymancroatia.model.BusinessUser
import kotlinx.android.synthetic.main.fragment_dialog_search_users.*


class SearchUserFragmentDialog: DialogFragment() {
    private var userSearchListener: UserSearchListener? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.FragmentDialogTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dialog_search_users, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        initListeners()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
    }

    fun setUserSearchListener(listener: UserSearchListener) {
        userSearchListener = listener
    }

    private fun initListeners() {
        thisIsTheOtherSearch.setOnClickListener {
            val query =  db.collection("businessUsers").whereEqualTo("jobType",job.selectedItem).whereEqualTo("workTerritory",places.selectedItem)
            query.get().addOnSuccessListener {
                if (!it.isEmpty){
                    val users = it.toObjects(BusinessUser::class.java)
                    userSearchListener?.onUserFound(users)
                    dismiss()
                }else{
                    Toast.makeText(HandymanApp.getAppContext(),"No users",Toast.LENGTH_LONG ).show()
                }
            }
        }

    }


    companion object {
        fun newInstance() =
            SearchUserFragmentDialog()
    }
}