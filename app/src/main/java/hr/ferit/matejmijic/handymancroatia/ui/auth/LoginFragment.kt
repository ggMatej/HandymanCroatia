package hr.ferit.matejmijic.handymancroatia.ui.auth

import android.content.Intent
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import hr.ferit.matejmijic.handymancroatia.R
import hr.ferit.matejmijic.handymancroatia.common.showFragment
import hr.ferit.matejmijic.handymancroatia.persistence.CreateAccPrefs
import hr.ferit.matejmijic.handymancroatia.ui.home.normalAcc.MainPageActivity
import hr.ferit.matejmijic.handymancroatia.ui.base.BaseFragment
import hr.ferit.matejmijic.handymancroatia.ui.home.businessAcc.MainPageBusinessActivity
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment: BaseFragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun getLayoutResourceId(): Int = R.layout.fragment_login


    override fun initUi() {
        CreateAccPrefs.removeAll()
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()


        login.setOnClickListener {
            login()
        }

        register.setOnClickListener {
            activity?.showFragment(R.id.fragmentContainer, CreateAccType(), true)
        }

    }

    private fun login(){
            if(emailInput.text.toString().isEmpty() || passwordInput.text.toString().isEmpty()){
                Toast.makeText(context, "Email and/or password is empty!", Toast.LENGTH_LONG).show()
            } else {
                auth.signInWithEmailAndPassword(emailInput.text.toString(), passwordInput.text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful){
                            db.collection("normalUsers").document(auth.uid!!).get().addOnSuccessListener {
                                documentSnapshot ->
                                if (documentSnapshot.exists()){
                                    val intent = Intent(activity, MainPageActivity::class.java).apply {
                                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    }
                                    startActivity(intent)
                                    activity?.finish()
                                    }else {
                                    val intent = Intent(activity, MainPageBusinessActivity::class.java).apply {
                                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    }

                                    startActivity(intent)
                                    activity?.finish()
                                }
                        }

                        } else {
                            Toast.makeText(context, task.exception?.localizedMessage, Toast.LENGTH_LONG).show()
                        }
                    }
            }



    }

}