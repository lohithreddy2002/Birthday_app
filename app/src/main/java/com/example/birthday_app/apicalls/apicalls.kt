import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.location.Location
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.material.progressindicator.LinearProgressIndicator
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okio.IOException
import kotlin.concurrent.thread

class SynchronousGet {

    companion object {
        val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()
    }
    private val client = OkHttpClient()

    @SuppressLint("HardwareIds")
    fun post(context: Context, text : Location,progress:LinearProgressIndicator) {

            val b = text.latitude
            val a = text.longitude
            val id: String =
                Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
            val json = "{\" deivce \":\"$id\", \" longitude \":$a,\" latitude \":$b}";
            val request = Request.Builder()
                .url("http://65.1.236.176:9000/data")
                .post(json.toRequestBody(JSON))
                .build()
            val toast = Toast.makeText(context, "falied", Toast.LENGTH_SHORT)
            Log.d("stop","failed")
            thread {
                try {
                    client.newCall(request).execute().use {
                        if (!it.isSuccessful) {
                            println(it)
                            println("not success")
                            Log.d("stop","failed")

                        } else {
                            println(it.body!!.string())


                        }
                    }
                } catch (e: IOException) {
                    println(e)
                    println("excetion")
                    toast.show()
                    Log.d("stop","failed")

                }

            }

}

    fun get(){
        val request = Request.Builder().url( "http://10.0.2.2:5000")
                .build()

        thread {

            client.newCall(request).enqueue(object :Callback{
                override fun onFailure(call: Call, e: java.io.IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    if(!response.isSuccessful){
                        throw IOException("error")
                    }

                    println(response.body!!.string())
                }

            })
        }
    }
}
