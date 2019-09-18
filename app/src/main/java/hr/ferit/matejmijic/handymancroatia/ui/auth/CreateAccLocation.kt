package hr.ferit.matejmijic.handymancroatia.ui.auth

import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import hr.ferit.matejmijic.handymancroatia.HandymanApp
import hr.ferit.matejmijic.handymancroatia.R
import hr.ferit.matejmijic.handymancroatia.model.BusinessUser
import hr.ferit.matejmijic.handymancroatia.model.NormalUser
import hr.ferit.matejmijic.handymancroatia.persistence.CreateAccPrefs
import hr.ferit.matejmijic.handymancroatia.ui.home.normalAcc.MainPageActivity
import hr.ferit.matejmijic.handymancroatia.ui.base.BaseFragment
import hr.ferit.matejmijic.handymancroatia.ui.home.businessAcc.MainPageBusinessActivity
import kotlinx.android.synthetic.main.fragment_createacc_location.*
import kotlinx.android.synthetic.main.fragment_createacc_location.btn_back


class CreateAccLocation: BaseFragment(){

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun getLayoutResourceId(): Int = R.layout.fragment_createacc_location
    private val LOCATION_PERMISSION_CODE =1

    override fun initUi() {
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!.applicationContext)
        showAlertDialog()

        btn_createacc.setOnClickListener {
            Toast.makeText(
                HandymanApp.getAppContext(),
                CreateAccPrefs.getString(CreateAccPrefs.KEY_ACCOUNT_ADDRESS, "NISTA"),
                Toast.LENGTH_SHORT
            ).show()
        }

        btn_back.setOnClickListener {
            activity?.onBackPressed()
        }

        btn_createacc.setOnClickListener {
            auth.createUserWithEmailAndPassword(
                CreateAccPrefs.getString(CreateAccPrefs.KEY_EMAIL,"")!!,
                CreateAccPrefs.getString(CreateAccPrefs.KEY_PASSWORD,"")!!
            ).addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(HandymanApp.getAppContext(),"Registration successful",Toast.LENGTH_SHORT).show()
                    when(CreateAccPrefs.getString(CreateAccPrefs.KEY_ACCOUNT_TYPE,"")){
                        "default" -> {
                            val normalUser = NormalUser(
                                auth.uid,
                                CreateAccPrefs.getString(CreateAccPrefs.KEY_EMAIL,""),
                                CreateAccPrefs.getString(CreateAccPrefs.KEY_NICKNAME,""),
                                CreateAccPrefs.getString(CreateAccPrefs.KEY_PHONENUM,""),
                                CreateAccPrefs.getString(CreateAccPrefs.KEY_ACCOUNT_TYPE,""),
                                CreateAccPrefs.getString(CreateAccPrefs.KEY_ACCOUNT_ADDRESS,"")
                            )
                            db.collection("normalUsers").document(normalUser.userId.toString()).set(normalUser)
                            activity?.supportFragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                            //activity?.showFragment(R.id.fragmentContainer, FrontPageFragment(), false)
                            val intent = Intent(activity, MainPageActivity::class.java).apply {
                                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            }
                            startActivity(intent)
                            activity?.finish()

                        }
                        "business"->{
                            val businessUser = BusinessUser(
                                0,
                                auth.uid,
                                CreateAccPrefs.getString(CreateAccPrefs.KEY_EMAIL,""),
                                CreateAccPrefs.getString(CreateAccPrefs.KEY_NICKNAME,""),
                                CreateAccPrefs.getString(CreateAccPrefs.KEY_PHONENUM,""),
                                CreateAccPrefs.getString(CreateAccPrefs.KEY_ACCOUNT_TYPE,""),
                                CreateAccPrefs.getString(CreateAccPrefs.KEY_ACCOUNT_ADDRESS,""),
                                CreateAccPrefs.getString(CreateAccPrefs.KEY_JOB_TYPE,""),
                                CreateAccPrefs.getString(CreateAccPrefs.KEY_WORK_TERRITORY,"")
                            )
                            db.collection("businessUsers").document(businessUser.userId.toString()).set(businessUser)
                            activity?.supportFragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                            //activity?.showFragment(R.id.fragmentContainer, FrontPageFragment(), false)
                            val intent = Intent(activity, MainPageBusinessActivity::class.java).apply {
                                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            }
                            startActivity(intent)
                            activity?.finish()
                        }
                    }


                } else {
                    Toast.makeText(HandymanApp.getAppContext(),it.exception?.localizedMessage, Toast.LENGTH_LONG).show()
                }
            }
        }

    }

    private fun showAlertDialog() {
        AlertDialog.Builder(activity)
            .setTitle("Location")
            .setMessage("For better user experience we need to access your location.")
            .setPositiveButton("Okay"){
                    dialog, which ->
                if (ContextCompat.checkSelfPermission(HandymanApp.getAppContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),LOCATION_PERMISSION_CODE)
                }else{
                    fusedLocationClient.lastLocation.addOnSuccessListener {
                            location ->
                        Toast.makeText(HandymanApp.getAppContext(),"Location added",Toast.LENGTH_SHORT).show()
                        CreateAccPrefs.store(CreateAccPrefs.KEY_ACCOUNT_ADDRESS,"google.navigation:q=${location?.latitude},${location?.longitude}")
                    }
                }
            }
            .setNegativeButton("No"){
                    dialog, which ->
                CreateAccPrefs.store(CreateAccPrefs.KEY_ACCOUNT_ADDRESS,"No address")
                dialog.dismiss()
            }
            .create().show()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == LOCATION_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(HandymanApp.getAppContext(), "Permission GRANTED",Toast.LENGTH_SHORT).show()
                if (ContextCompat.checkSelfPermission(HandymanApp.getAppContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    fusedLocationClient.lastLocation.addOnSuccessListener {
                            location ->
                        Toast.makeText(HandymanApp.getAppContext(),"Location added",Toast.LENGTH_SHORT).show()
                        CreateAccPrefs.store(CreateAccPrefs.KEY_ACCOUNT_ADDRESS,"google.navigation:q=${location?.latitude},${location?.longitude}")

                    }
                }
            }else{
                Toast.makeText(HandymanApp.getAppContext(), "Permission DENIED.",Toast.LENGTH_SHORT).show()
                CreateAccPrefs.store(CreateAccPrefs.KEY_ACCOUNT_ADDRESS,"No address")
            }
        }
    }
}