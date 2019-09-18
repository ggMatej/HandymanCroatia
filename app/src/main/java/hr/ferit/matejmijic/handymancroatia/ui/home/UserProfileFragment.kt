package hr.ferit.matejmijic.handymancroatia.ui.home

import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import hr.ferit.matejmijic.handymancroatia.HandymanApp
import hr.ferit.matejmijic.handymancroatia.R
import hr.ferit.matejmijic.handymancroatia.common.showFragment
import hr.ferit.matejmijic.handymancroatia.model.NormalUser
import hr.ferit.matejmijic.handymancroatia.persistence.CreateAccPrefs
import hr.ferit.matejmijic.handymancroatia.ui.auth.MainActivity
import hr.ferit.matejmijic.handymancroatia.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_user_profile.*

class UserProfileFragment: BaseFragment(), ProfileUpdatedListener {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val LOCATION_PERMISSION_CODE =1
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun getLayoutResourceId(): Int =
        R.layout.fragment_user_profile


    override fun initUi() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!.applicationContext)
        setHasOptionsMenu(true)
        getInformation()
    }

    private fun getInformation() {
        tv_email.text = CreateAccPrefs.getString(CreateAccPrefs.KEY_EMAIL,"")
        tv_accType.text = CreateAccPrefs.getString(CreateAccPrefs.KEY_ACCOUNT_TYPE,"")
        if (CreateAccPrefs.getString(CreateAccPrefs.KEY_ACCOUNT_ADDRESS,"")=="No address"){
            tv_location.text = getString(R.string.address_not_available)
        } else {
            tv_location.text = getString(R.string.address_available)
        }
        tv_nickname.text = CreateAccPrefs.getString(CreateAccPrefs.KEY_NICKNAME,"")
        tv_phonenum.text = CreateAccPrefs.getString(CreateAccPrefs.KEY_PHONENUM,"")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.profile_menu,menu)
        if (CreateAccPrefs.getString(CreateAccPrefs.KEY_ACCOUNT_ADDRESS,"")=="No address")
            menu.getItem(1).isVisible = true
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.logout -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(HandymanApp.getAppContext(), MainActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                startActivity(intent)
                activity?.finish()
            }
            R.id.location -> {
                val fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!.applicationContext)
                showAlertDialog(fusedLocationClient)

            }
            R.id.edit ->{
                val dialog = EditProfileFragmentDialog.newInstance()
                dialog.setProfileUpdatedListener(this)
                dialog.show(childFragmentManager,dialog.tag)
            }

        }
        return false
    }

    private fun showAlertDialog(fusedLocationClient: FusedLocationProviderClient) {
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
                        Toast.makeText(HandymanApp.getAppContext(),"Location added", Toast.LENGTH_SHORT).show()
                        CreateAccPrefs.store(CreateAccPrefs.KEY_ACCOUNT_ADDRESS,"google.navigation:q=${location?.latitude},${location?.longitude}")
                        activity?.showFragment(R.id.mainPageFragmentContainer,UserProfileFragment(),false)
                        FirebaseFirestore.getInstance().collection("normalUsers").document(FirebaseAuth.getInstance().currentUser?.uid.toString()).update(
                            "location",CreateAccPrefs.getString(CreateAccPrefs.KEY_ACCOUNT_ADDRESS,""))

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
                        activity?.showFragment(R.id.mainPageFragmentContainer,UserProfileFragment(),false)

                    }
                }
            }else{
                Toast.makeText(HandymanApp.getAppContext(), "Permission DENIED.",Toast.LENGTH_SHORT).show()
                CreateAccPrefs.store(CreateAccPrefs.KEY_ACCOUNT_ADDRESS,"No address")
            }
        }
    }

    override fun onProfileChanged() {
        initUi()
    }


}