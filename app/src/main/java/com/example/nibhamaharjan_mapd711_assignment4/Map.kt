package com.example.nibhamaharjan_mapd711_assignment4

import android.content.Intent
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.Toast
import java.io.IOException
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class Map : AppCompatActivity(), OnMapReadyCallback {
    private var mGoogleMap: GoogleMap? = null
    var Scarborough1 = LatLng(43.7042981,-79.2577949)
    var Scarborough2 = LatLng(43.7637466,-79.2968345)
    var Scarborough3 = LatLng(43.7757196,-79.2675858)
    var Scarborough4 = LatLng(43.7276045,-79.2350958)
    var Scarborough5 = LatLng(43.7425228,-79.3101898)
    private var locationArrayList: ArrayList<LatLng>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        title="Pizza Show Location"

        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        locationArrayList= ArrayList()

            locationArrayList!!.add(Scarborough1)
            locationArrayList!!.add(Scarborough2)
            locationArrayList!!.add(Scarborough3)
            locationArrayList!!.add(Scarborough4)
            locationArrayList!!.add(Scarborough5)

        val mapOptionButton: ImageButton = findViewById(R.id.mapOptionMenu)
        val popupMenu = PopupMenu(this,mapOptionButton)
        popupMenu.menuInflater.inflate(R.menu.map_options,popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            changeMap(menuItem.itemId)
            true
        }

        mapOptionButton.setOnClickListener{
            popupMenu.show()
        }
    }
    private fun changeMap(itemId: Int){
        when(itemId){
            R.id.normal_map -> mGoogleMap?.mapType = GoogleMap.MAP_TYPE_NORMAL
            R.id.satellite_map -> mGoogleMap?.mapType = GoogleMap.MAP_TYPE_SATELLITE
        }
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap
        val geocoder = Geocoder(this)

        for (i in locationArrayList!!.indices) {
            try {
                val addresses = geocoder.getFromLocation(locationArrayList!![i].latitude, locationArrayList!![i].longitude, 1)
                if (addresses!!.isNotEmpty()) {
                    val address = addresses[0]
                    val markerTitle = address.getAddressLine(0) //Get the first address line
//                    val city = address.locality//Get city
//                    val country = address.countryName//Get country
                    val p=i+1
                    val fullAddress = markerTitle
                    //Pointer Adding
                    mGoogleMap!!.addMarker(MarkerOptions().position(locationArrayList!![i]).title("Pizza store $p").snippet(fullAddress))
                }
            } catch (e: IOException) {
                // Error Handling
                e.printStackTrace()
            }
            //Zoom in onto the pins
            val boundsBuilder = LatLngBounds.builder()
            for (location in locationArrayList!!) {
                boundsBuilder.include(location)
            }
            val bounds = boundsBuilder.build()
            val padding = 100
            val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding)
            mGoogleMap!!.animateCamera(cameraUpdate)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.customer_menu,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent: Intent = when (item.itemId) {
            R.id.logout_cus -> {
                Toast.makeText(this, "Logout Successful", Toast.LENGTH_SHORT).show()
                Intent(this, MainActivity::class.java)
            }
            R.id.customer_home -> {
                Toast.makeText(this, "Customer Home", Toast.LENGTH_SHORT).show()
                Intent(this, CustomerHomePage::class.java)
            }
            R.id.edit_profile -> {
                Toast.makeText(this, "Edit Profile", Toast.LENGTH_SHORT).show()
                Intent(this, EditProfile::class.java)
            }
            R.id.view_order -> {
                Toast.makeText(this, "View Your Order", Toast.LENGTH_SHORT).show()
                Intent(this, ViewCustomerOrder::class.java)
            }

            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
        startActivity(intent)
        return true
    }
}