package com.ryandrx.smack.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ryandrx.smack.Model.Message
import com.ryandrx.smack.R
import com.ryandrx.smack.Services.UserDataService
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class MessageAdapter(val context: Context, private val messages: ArrayList<Message>) : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return messages.count()
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindMessage(context, messages[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.message_list_view, parent, false)

        return ViewHolder(view)
    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        private val userImage = itemView?.findViewById<ImageView>(R.id.messageUserImage)
        private val timeStamp = itemView?.findViewById<TextView>(R.id.messageTimestampLbl)
        private val userName = itemView?.findViewById<TextView>(R.id.messageUsernameLabel)
        private val messageBody = itemView?.findViewById<TextView>(R.id.messageBodyLabel)

        fun bindMessage(context: Context, message: Message) {
            val resourceId = context.resources.getIdentifier(message.userAvater, "drawable", context.packageName)
            userImage?.setImageResource(resourceId)
            userImage?.setBackgroundColor(UserDataService.returnAvatarColor(message.userAvatarColor))
            userName?.text = message.userName
            timeStamp?.text = returnDateString(message.timeStamp)
            messageBody?.text = message.message
        }

        private fun returnDateString(isoString: String): String {
            val isoFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            isoFormatter.timeZone = TimeZone.getTimeZone("UTC")

            var convertedDate = Date()
            try {
                convertedDate = isoFormatter.parse(isoString)
            } catch (e: ParseException) {
                Log.d("PARSE", "Cannot parse date: ${e.localizedMessage}")
            }

            val outDateString = SimpleDateFormat("E, h:mm a", Locale.getDefault())
            return outDateString.format(convertedDate)
        }
    }
}