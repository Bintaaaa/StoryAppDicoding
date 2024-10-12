package com.bintaaaa.storyappdicoding.presentation.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bintaaaa.storyappdicoding.R
import com.bintaaaa.storyappdicoding.data.models.resposne.StoriesResponse

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.bintaaaa.storyappdicoding.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private var stories: StoriesResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        stories = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra<StoriesResponse>(EXTRA_MAP, StoriesResponse::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<StoriesResponse>(EXTRA_MAP)
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Add a marker in Sydney and move the camera
       for (story in stories!!.listStory!!){
           val mark = LatLng(story!!.lat!!, story.lon!!)
           mMap.addMarker(MarkerOptions().position(mark))
       }
    }

    companion object{
        const val EXTRA_MAP = "extra_map"
    }
}