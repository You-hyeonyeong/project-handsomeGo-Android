package com.hyeong.handsomego.after_stamp

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.hyeong.handsomego.Idx
import com.hyeong.handsomego.R
import com.hyeong.handsomego.applicationController.ApplicationController
import com.hyeong.handsomego.applicationController.NetworkService
import com.hyeong.handsomego.get.GetPlaceInfoResponse
import kotlinx.android.synthetic.main.activity_simple_info.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SimpleInfoActivity : AppCompatActivity() {
    var networkService : NetworkService= ApplicationController.instance.networkService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_info)
        // Full Screen
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        window.statusBarColor = Color.TRANSPARENT

        // Get Place Info
        val getPlaceInfoResponse = networkService.getPlaceInfo(Idx.place_id)
        getPlaceInfoResponse.enqueue(object : Callback<GetPlaceInfoResponse>{
            override fun onFailure(call: Call<GetPlaceInfoResponse>?, t: Throwable?) {
            }

            override fun onResponse(call: Call<GetPlaceInfoResponse>?, response: Response<GetPlaceInfoResponse>?) {
                if(response!!.isSuccessful){
                    simple_name_tv.text = response.body().data.place_name
                    simple_address_tv.text = response.body().data.place_address
                    simple_info_tv.text = response.body().data.place_content
                    simple_review_tv.text = response.body().data.commentCount.toString()
                    Glide.with(this@SimpleInfoActivity).load(response.body().data.place_pic).into(simple_img_iv)
                }
            }

        })
    }
}