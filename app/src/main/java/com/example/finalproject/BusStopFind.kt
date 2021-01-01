package com.example.finalproject

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.activity_bus_map1.*
import kotlinx.android.synthetic.main.activity_show__weather2.*

class BusStopFind : AppCompatActivity() , OnMapReadyCallback {
    private var traceOfMe: ArrayList<LatLng>? = null   //18add
    private val REQUEST_PERMISSIONS=1 //18add
    private lateinit var mMap: GoogleMap //18add
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus_map1)
        showSpinner1()

        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this,arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),REQUEST_PERMISSIONS)
        else{
            val map=supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
            map.getMapAsync(this)
        }

        val mapFragment=supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        btn_backMain.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }



    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isEmpty()) return
        when (requestCode) {
            REQUEST_PERMISSIONS -> {
                for (result in grantResults)
                    if (result != PackageManager.PERMISSION_GRANTED)
                        finish()
                    else {
                        val map = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
                        map.getMapAsync(this)
                    }
            }
        }
    }
    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap   //prepare Map before start
        locationManager()   //definition of function, analyze which GPS can use
        mMap.isMyLocationEnabled = true  //Show the location from where I go(Blue point)
        mMap!!.mapType=GoogleMap.MAP_TYPE_HYBRID //change type


        val marker1= MarkerOptions()
        val marker2= MarkerOptions()
        val marker3= MarkerOptions()
        val marker4= MarkerOptions()
        val marker5= MarkerOptions()
        val marker6= MarkerOptions()


        marker1.position(LatLng(25.04226382928084,121.53444170951843))
        marker1.title("台北科技大學站(忠孝)")
        marker1.snippet("202 262 212 299 600 605 953 忠孝幹線")
        marker1.draggable(false)
        marker1.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
        mMap.addMarker(marker1)
        marker2.position(LatLng(25.043551,121.536293))
        marker2.title("台北科技大學站(建國)")
        marker2.snippet("298 919 紅57")
        marker2.draggable(false)
        marker2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        mMap.addMarker(marker2)
        marker3.position(LatLng(25.044708,121.532934))
        marker3.title("光華商場")
        marker3.snippet("72 109 214 222 280 505 643 668 672 675 676 680 1550 松江新生幹線")
        marker3.draggable(false)
        marker3.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
        mMap.addMarker(marker3)
        marker4.position(LatLng(25.041961,121.532928))
        marker4.title("捷運忠孝新生站")
        marker4.snippet("72 109 214 222 280 505 643 668 672 675 676 680 1550 松江新生幹線")
        marker4.draggable(false)
        marker4.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))
        mMap.addMarker(marker4)
        marker5.position(LatLng(25.044635,121.535815))
        marker5.title("懷生國宅")
        marker5.snippet("669")
        marker5.draggable(false)
        marker5.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
        mMap.addMarker(marker5)
        marker6.position(LatLng(25.043226,121.534565))
        marker6.title("台北科技大學 NTUT")
        marker6.snippet("University Number 1")
        marker6.draggable(false)
        marker6.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        mMap.addMarker(marker6)
    }

    fun showSpinner1(){
        val lunch = arrayListOf("請選擇站牌(點選回到北科大)","台北科技大學站(忠孝)","台北科技大學站(建國)","忠孝新生捷運站","光華商場","懷生國宅")
        val adapter= ArrayAdapter(this@BusStopFind,android.R.layout.simple_spinner_item,lunch)

        spinner1.adapter=adapter



        Log.e("debug", spinner1.childCount.toString())



        //加入事件並追蹤
        spinner1.onItemSelectedListener=object:  AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                tv_showBusStop.setText(lunch[position])  //將text表示所選擇的是什麼你選擇的是
                val spinner_val = position //追蹤使用者搜尋哪個spinner 藉此知道我們要顯示甚麼 表顯該有的功能
                //選擇spinner內的元件
                if (spinner_val == 1 ){
                    tv_showBusNum.setText("202\t262\tt212\n299\t600\t605\n953\t忠孝幹線")
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(25.04226382928084,121.53444170951843), 19.0f))
                } else if (spinner_val == 2) {
                    tv_showBusNum.setText("298\t919\t紅57")
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(25.043551,121.536293), 19.0f))
                } else if (spinner_val == 3) {
                    tv_showBusNum.setText("72\t   109\t  214\n222\t280\t505\n643\t668\t672\n675\t676\t680\n1550\t 松江新生幹線")
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(25.041961,121.532928), 19.0f))
                } else if (spinner_val == 4) {
                    tv_showBusNum.setText("72\t   109\t  214\n222\t280\t505\n643\t668\t672\n675\t676\t680\n1550\t 松江新生幹線")
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(25.044708,121.532934), 19.0f))
                } else if (spinner_val == 5) {
                    tv_showBusNum.setText("669")
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(25.044635,121.535815), 19.0f))
                }else{
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(25.043226,121.534565), 17.0f))
                    tv_showBusNum.setText("請先選擇周遭站牌")
                }
            }

            //若無選擇spinner內的元件
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(this@BusStopFind,"尚未選擇", Toast.LENGTH_LONG).show()
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
    }

    var oriLocation: Location? = null
    fun locationManager(){
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager? //*get the system service of GPS
        var isGPSEnabled = locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)  //Physical GPS
        var isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) //Network GPS

        if (!(isGPSEnabled || isNetworkEnabled)) //Neither of GPS turn on
            Toast.makeText(this, "No GPS,Please turn ON", Toast.LENGTH_LONG).show()  //show the wrong
        else
            try {
                if (isGPSEnabled ) {
                    // requestLocationUpdates: to ask what service to get update information
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0f, locationListener)
                    oriLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                }
                else if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener)
                    oriLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                }
            } catch(ex: SecurityException) {
                Log.d("myTag", "Security Exception, no location available")
            }
        if(oriLocation != null) {
            //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(oriLocation!!.latitude, oriLocation!!.longitude), 20.0f))
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(25.043226,121.534565), 17.0f))
        }
    }


    val locationListener=object : LocationListener { //Used to get Update Location
        override fun onLocationChanged(location: Location) {
            if(oriLocation == null) {
                oriLocation = location
            }
            //如果人移動鏡頭則追蹤人的去向
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude, location.longitude),18.0f))
            //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(25.043226,121.534565), 17.0f))

            //繪畫出步行軌跡
            if (traceOfMe == null) {
                traceOfMe = ArrayList<LatLng>()
            }
            traceOfMe!!.add(LatLng(location.latitude, location.longitude))
            val polylineOpt = PolylineOptions()
            for (latlng in traceOfMe!!) {
                polylineOpt.add(latlng)
            }
            polylineOpt.color(Color.RED)
            val line: Polyline = mMap.addPolyline(polylineOpt)
            line.setWidth(5f)
        }
    }

}