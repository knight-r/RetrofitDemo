package com.example.retrofitdemo

import android.R
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.retrofitdemo.adapter.FragmentAdapter
import com.example.retrofitdemo.adapter.RVMyDataAdapter
import com.example.retrofitdemo.adapter.RVPhotoAdapter
import com.example.retrofitdemo.databinding.ActivityMainBinding
import com.example.retrofitdemo.models.DataClass
import com.example.retrofitdemo.models.DataClassItem
import com.example.retrofitdemo.models.Photo
import com.example.retrofitdemo.models.PhotosList
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    private var photoListObject = arrayListOf<Photo>()
    private var myDataListObject = arrayListOf<DataClassItem>()
    private var listOfTabName = listOf<String>("MyData", "MyPhotos")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        setupViewPager()
        setupTabLayout()
        mainBinding.viewPager.visibility = View.GONE

        mainBinding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                //do stuff here
                if(tab.isSelected && tab.position ==1) {
                    getPhotos()
                } else {
                    getMyData()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        mainBinding.refreshLayout.setOnRefreshListener {
            when(mainBinding.tabLayout.selectedTabPosition){
                0-> getMyData()
                1->getPhotos()
            }
            mainBinding.refreshLayout.isRefreshing = false
        }
        getMyData()
    }

    private fun getMyData() {
        if(myDataListObject!=null && myDataListObject.size>0) {
            setMyDataListInRecyclerView(myDataListObject as DataClass)
            return
        }
        val mProgressDialog = ProgressDialog(this)
        mProgressDialog.setMessage("Loading...")
        mProgressDialog.show()
        val retrofitService = getRetrofitClient().getPost()
        retrofitService.enqueue(object : Callback<DataClass?> {
            override fun onResponse(call: Call<DataClass?>, response: Response<DataClass?>){
                val myDataList = response.body()!!
                if(myDataList.isNotEmpty()) {
                    myDataListObject = myDataList
                }
                setMyDataListInRecyclerView(myDataList)
                mProgressDialog.cancel()

            }

            override fun onFailure(call: Call<DataClass?>, t: Throwable) {
                Log.d("MainActivity","onFailure" +  t.message)
            }
        })

    }

    private fun getPhotos() {
        if((photoListObject != null) && (photoListObject.size > 0)) {
            setPhotoListInRecyclerView(photoListObject as PhotosList)
            return
        }

        val mProgressDialog = ProgressDialog(this)
        mProgressDialog.setMessage("Loading...")
        mProgressDialog.show()

        val retrofitService = getRetrofitClient().getPhotos()
        retrofitService.enqueue(object : Callback<PhotosList?> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<PhotosList?>, photosListResponse: Response<PhotosList?>){
                val photosListBody = photosListResponse.body()!!
                if (photosListBody.isNotEmpty()) {
                    photoListObject = photosListBody as PhotosList
                }
                setPhotoListInRecyclerView(photosListBody)
                mProgressDialog.cancel()

//                var ivPhoto = findViewById<ImageView>(R.id.imageView)
//                ivPhoto.visibility = View.VISIBLE
//
//                Glide.with(applicationContext)
//                    .load(photosListBody?.get(1)?.url).placeholder(androidx.appcompat.R.drawable.abc_ab_share_pack_mtrl_alpha)
//                    .into(ivPhoto)
            }

            override fun onFailure(call: Call<PhotosList?> , t: Throwable) {
                Log.d("MainActivity","onFailure" +  t.message)
            }
        })

    }

    fun setPhotoListInRecyclerView(photosListBody: PhotosList) {
        mainBinding.recyclerView?.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = RVPhotoAdapter(photosListBody)
        }
    }
    fun setMyDataListInRecyclerView(photosListBody: DataClass) {
        mainBinding.recyclerView?.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = RVMyDataAdapter(photosListBody)
        }
    }


    private fun getRetrofitClient(): ApiInterface {
        return Retrofit.Builder().addConverterFactory(
            GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)
    }
    private fun setupTabLayout() {
        TabLayoutMediator(
            mainBinding.tabLayout, mainBinding.viewPager
        ){

                tab, position -> tab.text = listOfTabName.get(position)
        }.attach()
    }

    private fun setupViewPager() {
        val adapter = FragmentAdapter(this, 2)
        mainBinding.viewPager.adapter = adapter
    }

    override fun onBackPressed() {
        val viewPager = mainBinding.viewPager
        if (viewPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }



    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    }
}