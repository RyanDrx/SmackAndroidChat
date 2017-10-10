package com.ryandrx.smack.Services

import android.content.Context
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.ryandrx.smack.Controller.App
import com.ryandrx.smack.Model.Channel
import com.ryandrx.smack.Utilities.URL_GET_CHANNELS
import org.json.JSONException

/**
 * Created by RyanDrx on 10/9/2017.
 */
object MessageService {
    val channels = ArrayList<Channel>()

    fun getChannels(context: Context, complete: (Boolean) -> Unit) {

        val channelsRequest = object : JsonArrayRequest(Method.GET, URL_GET_CHANNELS, null, Response.Listener { response ->

            try {

                for (x in 0 until response.length()) {
                    val channel = response.getJSONObject(x)
                    val name = channel.getString("name")
                    val chanDesc = channel.getString("description")
                    val channelId = channel.getString("_id")

                    val newChannel = Channel(name, chanDesc, channelId)
                    this.channels.add(newChannel)
                }

                complete(true)

            } catch (e: JSONException) {
                Log.d("JSON", "EXC: ${e.localizedMessage}")
            }
        }, Response.ErrorListener { error ->
            Log.d("ERROR", "Could not retrieve channels")
            complete(false)

        }) {
            override fun getBodyContentType(): String {
                return "applications/json; charset=utf-8"
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Authorization", "Bearer ${App.prefs.authToken}")

                return headers
            }
        }

        App.prefs.requestQueue.add(channelsRequest)
    }

}