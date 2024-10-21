package com.bintaaaa.storyappdicoding.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.bintaaaa.storyappdicoding.R
import com.bintaaaa.storyappdicoding.common.api.Result
import com.bintaaaa.storyappdicoding.data.models.resposne.StoryItem

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.bintaaaa.storyappdicoding.databinding.ActivityMapsBinding
import com.bintaaaa.storyappdicoding.presentation.viewModel.StoryViewModel
import com.bintaaaa.storyappdicoding.presentation.viewModel.ViewModelFactory
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLngBounds

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
    val viewModel: StoryViewModel by viewModels {
        factory
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

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

        viewModel.location().observe(this) {
            if (it != null) {
                when(it) {
                    is Result.Success -> {
                        addManyMarkers(it.data?.listStory!!)
                    }
                    is Result.Loading -> {
                    }
                    is Result.Error -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

    }

    private val boundsBuilder = LatLngBounds.Builder()
    private fun addManyMarkers(stories: List<StoryItem>) {
        stories.forEach { item ->
            val latLng = LatLng(item.lat!!, item.lon!!)
            mMap.addMarker(MarkerOptions().position(latLng).title(item.name))
            boundsBuilder.include(latLng)
        }

        val bounds: LatLngBounds = boundsBuilder.build()

        mMap.animateCamera(
            CameraUpdateFactory.newLatLngBounds(
                bounds,
                resources.displayMetrics.widthPixels,
                resources.displayMetrics.heightPixels,
                30
            )
        )
    }
}