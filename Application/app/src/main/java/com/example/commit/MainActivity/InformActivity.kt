package com.example.commit.MainActivity

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.commit.Adapter.CafeHorizontalAdapter
import com.example.commit.Adapter.CafeVerticalAdapter
import com.example.commit.Fragment.CafeteriaFragment
import com.example.commit.Fragment.MenuFragment
import com.example.commit.Fragment.ReviewFragment
import com.example.commit.Fragment.SummaryFragment
import com.example.commit.ListItem.Homefeed
import com.example.commit.ListItem.Menu
import com.example.commit.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import kotlinx.android.synthetic.main.activity_inform.*
import kotlinx.android.synthetic.main.fragment_menu.*
import kotlinx.android.synthetic.main.fragment_summary.*
import okhttp3.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.intentFor
import org.json.JSONObject
import org.jsoup.Jsoup
import java.io.IOException
import java.net.URL
import java.net.URLEncoder

class InformActivity : AppCompatActivity(), OnMapReadyCallback {

    val ncclientId:String = "4fk2n2jfrm"
    val ncclientSecret:String = "Ov8qSzvnvb4K3D8WGfh5ZP9yojd0e5UJvBJazuH1"

    companion object {
        var mapx: Double? = null
        var mapy: Double? = null
        var phone: String? = null
        var name: String? = null
        var id:String? = null
        var menu:ArrayList<Menu> = arrayListOf()
        var roadAddr:String? = null
    }

    /*fun fetchJson(vararg p0: String) {
        val intent = intent
        val text = intent.getStringExtra("roadAddress")
        val apiURL = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=$text"

        val url = URL(apiURL)

        val request = Request.Builder()
            .url(url)
            .addHeader("X-NCP-APIGW-API-KEY-ID", ncclientId)
            .addHeader("X-NCP-APIGW-API-KEY", ncclientSecret)
            .method("GET", null)
            .build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call?, response: Response?) {

                runOnUiThread {
                    val body = response?.body()?.string()
                    println("Success to execute request : $body")

                    val jObject : JSONObject = JSONObject(body)
                    val jArray = jObject.getJSONArray("addresses")

                    for(i in 0 until jArray.length())
                    {
                        val obj = jArray.getJSONObject(i)
                        mapx = obj.getDouble("x")
                        mapy = obj.getDouble("y")
                    }
                }
            }

            override fun onFailure(call: Call?, e: IOException?) {
                println("Failed to execute request")
            }
        })
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inform)

        var intent=intent
        name =intent.getStringExtra("name")
        mapx = intent.getStringExtra("x").toDouble()
        mapy = intent.getStringExtra("y").toDouble()
        phone = intent.getStringExtra("phone")
        id = intent.getStringExtra("id")
        roadAddr = intent.getStringExtra("roadAddr")

        if (savedInstanceState == null) {
            val fragment = SummaryFragment(roadAddr as String)
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_inform, fragment, fragment.javaClass.simpleName).commit()
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bnv_inform)
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener)

        doAsync {
            val url = "https://store.naver.com/restaurants/detail?id=$id"
            try {
                val doc = Jsoup.connect(url).get()
                val menuData = doc.select("ul[class=list_menu]").select("li")

                menuData.forEachIndexed { index, element ->
                    val menuName = element.select("li span[class=name]").text()
                    val menuPrice = element.select("li em[class=price]").text()
                    menu.add(Menu(menuName, menuPrice))
                    //runOnUiThread {
                    //}
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }



        //Toast.makeText(this, menu.size.toString(), Toast.LENGTH_LONG).show();


        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map, it).commit()
            }
        mapFragment.getMapAsync(this)

        text_cafeteria_title.text = name

        btn_phone.setOnClickListener {
            val builder = AlertDialog.Builder(ContextThemeWrapper(this@InformActivity, R.style.Theme_AppCompat_Light_Dialog))
            builder.setTitle(name)
            builder.setMessage(phone)

            builder.setNegativeButton("통화") { dialog, id->
                Log.d("test", phone)
                val call = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${phone}"))
                try {
                    startActivity(call)
                } catch (e: Exception) {
                    Log.d("test",e.toString())
                    e.printStackTrace()
                }
            }
            builder.setPositiveButton("취소") { dialog, id ->

            }
            builder.show()
        }

        btn_share.setOnClickListener{

        }


    }

    override fun onMapReady(naverMap: NaverMap) {
        val cameraUpdate = CameraUpdate.scrollTo(LatLng(mapy!!,mapx!!))
        val zoomUpdate = CameraUpdate.zoomTo(18.0)
        naverMap.moveCamera(cameraUpdate)
        naverMap.moveCamera(zoomUpdate)

        val marker = Marker()
        marker.position = LatLng(mapy!!, mapx!!)
        marker.map = naverMap
    }

    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.summary -> {
                val fragment = SummaryFragment(roadAddr as String)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_inform, fragment, fragment.javaClass.simpleName).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.menu -> {
                val fragment = MenuFragment(menu)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_inform, fragment, fragment.javaClass.simpleName).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.review -> {
                val fragment = ReviewFragment(name as String)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_inform, fragment, fragment.javaClass.simpleName).commit()
                return@OnNavigationItemSelectedListener true
            }

        }
        false
    }
}
