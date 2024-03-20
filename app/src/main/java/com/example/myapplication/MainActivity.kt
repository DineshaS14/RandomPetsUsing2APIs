package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class MainActivity : AppCompatActivity() {
    var petImageURL = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.getButton)
        val catButton = findViewById<Button>(R.id.getCatButton)
        val newimageView = findViewById<ImageView>(R.id.imageView)
        getNextImage(button, newimageView)
        getNextCatImage(catButton, newimageView)
    }
    private fun getDogImageURL() {
        val client = AsyncHttpClient()
        client["https://dog.ceo/api/breeds/image/random", object : JsonHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                petImageURL = json.jsonObject.getString("message")
                Log.d("petImageURL", "pet image URL set")
            } // onSuccess

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Dog Error", errorResponse)
            } // onFailure
        }]
    } // getDogImageURL
    private fun getCatImageURL() {
        val client = AsyncHttpClient()
        client["https://api.thecatapi.com/v1/images/search", object : JsonHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                var resultsJSON = json.jsonArray.getJSONObject(0)
                petImageURL = resultsJSON.getString("url")
                Log.d("petImageURL", "pet image URL set")
            } // onSuccess

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Dog Error", errorResponse)
            } // onFailure
        }]
    } // getCatImageURL
    private fun getNextImage(button: Button, imageView: ImageView) {
        button.setOnClickListener {
            getDogImageURL()
            Glide.with(this)
                .load(petImageURL)
                .fitCenter()
                .into(imageView)

    }

    } // getNextImage
    private fun getNextCatImage(button: Button, imageView: ImageView) {
        button.setOnClickListener {
            getCatImageURL()
            Glide.with(this)
                .load(petImageURL)
                .fitCenter()
                .into(imageView)

        }
    } // getNextCatImage
}