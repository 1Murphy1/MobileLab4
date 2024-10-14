package com.example.internettest

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.SocketException
import java.net.URL

class MainActivity : AppCompatActivity() {

    private val tag = "Flickr cats"
    private val tagOkHTTP = "Flickr okCats"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnHTTP: Button = findViewById(R.id.btnHTTP)
        btnHTTP.setOnClickListener {
            fetchFlickrData()
        }

        val btnOkHTTP: Button = findViewById(R.id.btnOkHTTP)
        btnOkHTTP.setOnClickListener {
            fetchFlickrDataOk()
        }
    }

    private fun fetchFlickrData() {
        CoroutineScope(Dispatchers.IO).launch {
            val responseData = getFlickrData("https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=ff49fcd4d4a08aa6aafb6ea3de826464&tags=cat&format=json&nojsoncallback=")
            withContext(Dispatchers.Main) {
                if (responseData.isNotEmpty()) {
                    Log.i(tag, responseData)
                } else {
                    Toast.makeText(this@MainActivity, "No data received via OkHTTP", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun fetchFlickrDataOk() {
        CoroutineScope(Dispatchers.IO).launch {
            val responseData = getFlickrData("https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=ff49fcd4d4a08aa6aafb6ea3de826464&tags=cat&format=json&nojsoncallback=1")
            withContext(Dispatchers.Main) {
                if (responseData.isNotEmpty()) {
                    Log.i(tagOkHTTP, responseData)
                } else {
                    Toast.makeText(this@MainActivity, "No data received via OkHTTP", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getFlickrData(apiUrl: String): String {
        var responseData = ""
        try {
            val url = URL(apiUrl)
            val urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.requestMethod = "GET"
            urlConnection.connectTimeout = 5000
            urlConnection.readTimeout = 5000

            val responseCode = urlConnection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val inStream = BufferedReader(InputStreamReader(urlConnection.inputStream))
                val responseBuilder = StringBuilder()
                var inputLine: String?

                while (inStream.readLine().also { inputLine = it } != null) {
                    responseBuilder.append(inputLine)
                }
                inStream.close()
                responseData = responseBuilder.toString()
            } else {
                Log.e(tag, "request not successful: $responseCode")
            }
        } catch (e: SocketException) {
            Log.e(tag, "check your internet connection", e)
        } catch (e: IOException) {
            Log.e(tag, "request failed", e)
        }
        return responseData
    }
}
