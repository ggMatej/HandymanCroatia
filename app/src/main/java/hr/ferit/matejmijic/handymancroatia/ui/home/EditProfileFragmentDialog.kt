package hr.ferit.matejmijic.handymancroatia.ui.home

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.TextUtils
import android.text.TextUtils.isEmpty
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import hr.ferit.matejmijic.handymancroatia.HandymanApp
import hr.ferit.matejmijic.handymancroatia.R
import hr.ferit.matejmijic.handymancroatia.common.isValidNickname
import hr.ferit.matejmijic.handymancroatia.common.showFragment
import hr.ferit.matejmijic.handymancroatia.persistence.CreateAccPrefs
import kotlinx.android.synthetic.main.fragment_dialog_edit_profile.*

class EditProfileFragmentDialog: DialogFragment() {

    private var profileUpdatedListener: ProfileUpdatedListener? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val LOCATION_PERMISSION_CODE =1





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.FragmentDialogTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dialog_edit_profile, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newEmailInput.setText(CreateAccPrefs.getString(CreateAccPrefs.KEY_EMAIL,""))
        newPhoneInput.setText(CreateAccPrefs.getString(CreateAccPrefs.KEY_PHONENUM,""))
        newNicknameInput.setText(CreateAccPrefs.getString(CreateAccPrefs.KEY_NICKNAME,""))
        initListeners()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
    }

    fun setProfileUpdatedListener(listener: ProfileUpdatedListener) {
        profileUpdatedListener = listener
    }

    private fun initListeners() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!.applicationContext)

        saveChanges.setOnClickListener {
            saveChanges()
        }

        updateLocation.setOnClickListener {
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!.applicationContext)
            showAlertDialog(fusedLocationClient)
        }


    }

    private fun showAlertDialog(fusedLocationClient: FusedLocationProviderClient?) {
        AlertDialog.Builder(activity)
            .setTitle("Location")
            .setMessage("For better user experience we need to access your location.")
            .setPositiveButton("Okay"){
                    dialog, which ->
                if (ContextCompat.checkSelfPermission(HandymanApp.getAppContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),LOCATION_PERMISSION_CODE)
                }else{
                    fusedLocationClient?.lastLocation?.addOnSuccessListener {
                            location ->
                        CreateAccPrefs.store(CreateAccPrefs.KEY_ACCOUNT_ADDRESS,"google.navigation:q=${location?.latitude},${location?.longitude}")
                        Toast.makeText(HandymanApp.getAppContext(),"Location updated", Toast.LENGTH_SHORT).show()

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

    private fun saveChanges(){
        val hashMap: MutableMap<String,Any?> = mutableMapOf()

        if (isEmpty(newEmailInput.text)){
            newEmailInput.error = "Cannot be empty"
        }
        if (isEmpty(newPasswordInput.text)){
            newEmailInput.error = "Cannot be empty"
        }
        if (isEmpty(newNicknameInput.text)){
            newEmailInput.error = "Cannot be empty"
        }
        if (isEmpty(newPhoneInput.text)){
            newEmailInput.error = "Cannot be empty"
        }
        if (!isInputEmpty()){
            val email = newEmailInput.text.toString()
            val pwd = newPasswordInput.text.toString()
            val nickname = newNicknameInput.text.toString()
            val phone = newPhoneInput.text.toString()
            val location = CreateAccPrefs.getString(CreateAccPrefs.KEY_ACCOUNT_ADDRESS,"")




            FirebaseAuth.getInstance().currentUser?.updateEmail(email)?.addOnCompleteListener { task ->
                if (task.isSuccessful){
                    hashMap["email"] = email
                    hashMap["nickname"] = nickname
                    hashMap["phoneNumber"] = phone
                    hashMap["location"] = location

                    FirebaseFirestore.getInstance().collection("normalUsers").document(FirebaseAuth.getInstance().currentUser?.uid.toString()).update(
                        hashMap
                    )

                    CreateAccPrefs.store(CreateAccPrefs.KEY_EMAIL,email)
                    CreateAccPrefs.store(CreateAccPrefs.KEY_NICKNAME,nickname)
                    CreateAccPrefs.store(CreateAccPrefs.KEY_PHONENUM,phone)
                    location?.let { CreateAccPrefs.store(CreateAccPrefs.KEY_ACCOUNT_ADDRESS, it) }

                    FirebaseAuth.getInstance().currentUser?.updatePassword(pwd)?.addOnCompleteListener { task ->
                        if (!task.isSuccessful) Toast.makeText(HandymanApp.getAppContext(),task.exception?.localizedMessage,Toast.LENGTH_LONG).show()
                    }

                    profileUpdatedListener?.onProfileChanged()

                }else{
                    Toast.makeText(HandymanApp.getAppContext(),task.exception?.localizedMessage + " EMAIL",Toast.LENGTH_LONG).show()
                }
            }

            dismiss()
        }
    }

    private fun isInputEmpty(): Boolean =
        TextUtils.isEmpty(newEmailInput.text) || TextUtils.isEmpty(newPasswordInput.text) || TextUtils.isEmpty(
            newNicknameInput.text) || TextUtils.isEmpty(newPhoneInput.text)

    companion object {
        fun newInstance() = EditProfileFragmentDialog()
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
                        Toast.makeText(HandymanApp.getAppContext(),"Location updated",Toast.LENGTH_SHORT).show()
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