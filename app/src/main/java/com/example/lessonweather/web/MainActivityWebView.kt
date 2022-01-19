package com.example.lessonweather.web

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.lessonweather.R
import com.example.lessonweather.databinding.ActivityMainBinding
import com.example.lessonweather.databinding.ActivityMainWebViewBinding
import com.example.lessonweather.view.main.MainFragment
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection
import kotlin.contracts.contract

class MainActivityWebView : AppCompatActivity() {

    lateinit var binding: ActivityMainWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

           binding.buttonOk.setOnClickListener{
               request(binding.editTextUrl.text.toString())
           }

    }


    private fun request(urlString:String){

        val handlerMainUI = Handler(mainLooper)

     /*   try {
            Thread{
                val url = URL(urlString)
                val httpsURLConnection = (url.openConnection() as HttpsURLConnection).apply {
                    requestMethod = "GET"
                    readTimeout = 5000
                }

                val bufferedReader = BufferedReader(InputStreamReader(httpsURLConnection.inputStream))
                val result = convertBufferToResult(bufferedReader)

                handlerMainUI.post{
                    binding.webView.loadData(result,"text/html; charset=utf-8","utf-8")
                }
                httpsURLConnection.disconnect()
            }.start()
        }catch (e: Exception){
            e.printStackTrace()
        }finally {
            //httpsURLConnection.disconnect()
        }*/
        Thread{
            val url = URL(urlString)
            val httpsURLConnection = (url.openConnection() as HttpsURLConnection).apply {
                requestMethod = "GET"
                readTimeout = 5000
            }

            val bufferedReader = BufferedReader(InputStreamReader(httpsURLConnection.inputStream))
            val result = convertBufferToResult(bufferedReader)

            handlerMainUI.post{
                binding.webView.loadDataWithBaseURL(null,result,"text/html; charset=utf-8","utf-8",null)
            }
            httpsURLConnection.disconnect()
        }.start()


    }
    private  fun convertBufferToResult(bufferedReader:BufferedReader):String{
        return bufferedReader.lines().collect(Collectors.joining("\n"))
    }
}