package com.zhengcode.detask.activities.maps

import android.content.Intent
import android.location.Location
import android.location.LocationListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.*
import com.zhengcode.detask.R
import com.zhengcode.detask.activities.tasks.ViewTaskActivity
import com.zhengcode.detask.models.OfferedTask
import com.zhengcode.detask.utils.showToast

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, LocationListener, GoogleMap.OnMarkerClickListener {

    var database = FirebaseDatabase.getInstance()
    private lateinit var mMap: GoogleMap
    lateinit var mapFragment: SupportMapFragment
    lateinit var marker : Marker


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = supportFragmentManager
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
        //googleMap.setOnMarkerClickListener(this)
        googleMap.uiSettings.isZoomControlsEnabled = true
        googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        val databaseRef = database.getReference("task")
        val singaporeCoordinates = LatLng(1.3521, 103.8198)

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(singaporeCoordinates, 12.0f))

        databaseRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                for (h in p0.children) {
                    val tasknow = h.getValue(OfferedTask::class.java)
                    val location : LatLng = LatLng(tasknow!!.locationx, tasknow.locationy)
                    mMap.addMarker(MarkerOptions().position(location).title(tasknow.title).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)))
                }
            }

        })

        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    override fun onLocationChanged(location: Location?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProviderEnabled(provider: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProviderDisabled(provider: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun onMarkerClick(p0: Marker?): Boolean {
        TODO("not implemented")

//        val currentTask = p0.
//        val intent = Intent(this@MapsActivity, ViewTaskActivity::class.java)
//        intent.putExtra("offer", currentTask?.offer)
//        intent.putExtra("title", currentTask?.title)
//        intent.putExtra("description", currentTask?.description)
//        intent.putExtra("locationx", currentTask?.locationx)
//        intent.putExtra("locationy", currentTask?.locationy)
//        intent.putExtra("date", currentTask?.date)
//        intent.putExtra("username", currentTask?.username)
//        intent.putExtra("taskID", currentTask?.taskid)
//        intent.putExtra("requestorId", currentTask?.requestorId)
//        startActivity(intent)
//        showToast("Map View")

    }
}
