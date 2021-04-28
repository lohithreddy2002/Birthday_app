package com.example.birthday_app.fragment

import SynchronousGet
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.hardware.SensorManager
import android.location.Location
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.birthday_app.R
import com.example.birthday_app.birth_viewmodel
import com.example.birthday_app.birthfactory
import com.example.birthday_app.databinding.ActivityMainBinding.inflate
import com.example.birthday_app.databinding.FragmentMainfragmentBinding
import com.example.birthday_app.db.repository.birth_repository
import com.example.birthday_app.db.repository.birthday_database
import com.example.birthday_app.recyclerview.adapter
import com.example.birthday_app.service
import com.google.android.gms.location.*


@Suppress("DEPRECATION")
class Mainfragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    private var stop:Boolean = false
    private lateinit var sensorManager: SensorManager
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var curr : Location? = null
    // TODO: Rename and change types of parameters
private lateinit var binding: FragmentMainfragmentBinding
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Welcome"
        return inflater.inflate(R.layout.fragment_mainfragment, container, false)


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @SuppressLint("MissingPermission")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.add_birthdaya  -> findNavController().navigate(R.id.action_mainfragment_to_add_to_list_fragment)
            R.id.settings -> Toast.makeText(this.requireContext(), "selected settings", Toast.LENGTH_SHORT).show()
            R.id.stoplocation -> {
                fusedLocationProviderClient.removeLocationUpdates(locationCallback)
                service.stopService(this.requireContext())
            }
            R.id.startloc -> fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
        }
        return super.onOptionsItemSelected(item)
    }





    @SuppressLint("MissingPermission")
    override fun onStart() {
        binding = FragmentMainfragmentBinding.bind(requireView())
        super.onStart()

        val databse = birthday_database(this.requireContext())
        val repo = birth_repository(databse)
        val factory = birthfactory(repo)


        val birthdays = binding.birthdays
        val progress = binding.progress

        val viewModel = ViewModelProviders.of(this, factory).get(birth_viewmodel::class.java)
        val adaper = adapter(listOf(), viewModel)
        birthdays.adapter = adaper
        birthdays.layoutManager = LinearLayoutManager(this.context)

        viewModel.getall().observe(this, Observer {
            adaper.list = it
            adaper.notifyDataSetChanged()
        })

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

        locationRequest = LocationRequest.create().apply {
            interval =  60000
            fastestInterval = 10000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }


        val context_ : Context = this.requireContext()

        locationCallback = object : LocationCallback(){
            @RequiresApi(Build.VERSION_CODES.Q)
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)

                curr = p0.lastLocation

                binding.longitude.text = p0.lastLocation.longitude.toString()
                binding.latitude.text = p0.lastLocation.latitude.toString()
                binding.speed.text = p0.lastLocation.speedAccuracyMetersPerSecond.toString()
                SynchronousGet().post(context_,p0.lastLocation,progress)
                service.startService(context_, "logitude -:- ${p0.lastLocation.longitude.toString()} latitude -:- ${p0.lastLocation.latitude.toString()}")

            }
        }



}




}