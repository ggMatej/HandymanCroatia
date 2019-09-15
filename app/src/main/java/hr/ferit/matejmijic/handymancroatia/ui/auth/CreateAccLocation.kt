package hr.ferit.matejmijic.handymancroatia.ui.auth

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import hr.ferit.matejmijic.handymancroatia.HandymanApp
import hr.ferit.matejmijic.handymancroatia.R
import hr.ferit.matejmijic.handymancroatia.persistence.CreateAccPrefs
import hr.ferit.matejmijic.handymancroatia.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_createacc_location.*
import java.util.jar.Manifest


class CreateAccLocation: BaseFragment(){


    private lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun getLayoutResourceId(): Int = R.layout.fragment_createacc_location
    private val LOCATION_PERMISSION_CODE =1

    override fun initUi() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!.applicationContext)
        showAlertDialog()
        btn_createacc.setOnClickListener {
            Toast.makeText(HandymanApp.getAppContext(), CreateAccPrefs.getString(CreateAccPrefs.KEY_ACCOUNT_ADDRESS,"NISTA"),Toast.LENGTH_SHORT).show()
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
                        CreateAccPrefs.store(CreateAccPrefs.KEY_ACCOUNT_ADDRESS,"google.navigation:q=${location?.latitude},${location?.longitude}")

                    }
                }
            }
            .setNegativeButton("No"){
                    dialog, which ->
                dialog.dismiss()
            }
            .create().show()
    }



    //    override fun initUi() {
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!.applicationContext)
//        if (ContextCompat.checkSelfPermission(HandymanApp.getAppContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
//            requestPermission()
//        } else {
//
//        }
//        fusedLocationClient.lastLocation.addOnSuccessListener {
//                location: Location? ->
//            CreateAccPrefs.store(CreateAccPrefs.KEY_ACCOUNT_ADDRESS,"google.navigation:q=${location?.latitude},${location?.longitude}")
//        }
//    }
//

    //
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
                        CreateAccPrefs.store(CreateAccPrefs.KEY_ACCOUNT_ADDRESS,"google.navigation:q=${location?.latitude},${location?.longitude}")

                    }
                }
            }else{
                Toast.makeText(HandymanApp.getAppContext(), "Permission DENIED.",Toast.LENGTH_SHORT).show()

            }
        }
    }
}