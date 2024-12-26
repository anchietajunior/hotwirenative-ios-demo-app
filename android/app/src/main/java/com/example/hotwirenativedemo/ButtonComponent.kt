package com.example.hotwirenativedemo.components

import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.hotwirenativedemo.R
import dev.hotwire.core.bridge.BridgeComponent
import dev.hotwire.core.bridge.BridgeDelegate
import dev.hotwire.core.bridge.Message
import dev.hotwire.navigation.destinations.HotwireDestination
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class ButtonComponent(
    name: String,
    private val delegate: BridgeDelegate<HotwireDestination>
) : BridgeComponent<HotwireDestination>(name, delegate) {

    private val submitButtonItemId = 37
    private val fragment: Fragment
        get() = delegate.destination.fragment
    private val toolbar: Toolbar?
        get() = fragment.view?.findViewById(dev.hotwire.navigation.R.id.toolbar)

    override fun onReceive(message: Message) {
        Log.d("ButtonComponent", "Received message: $message")
        when (message.event) {
            "connect" -> {
                Log.d("ButtonComponent", "Connect event received")
                handleConnectEvent(message)
            }
            else -> Log.w("ButtonComponent", "Unknown event for message: $message")
        }
    }

    private fun handleConnectEvent(message: Message) {
        val data = message.data<MessageData>() ?: return
        val order = 999 // Show as the right-most button
        val menuToolbar = toolbar?.menu ?: return
        Log.d("ButtonComponent", "Handling connect with data: $data")

        menuToolbar.removeItem(submitButtonItemId)
        menuToolbar.add(
            Menu.NONE,
            submitButtonItemId,
            order,
            data.title
        ).apply {
            setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
            setOnMenuItemClickListener {
                performButtonClick()
                true
            }
        }
    }

    private fun performButtonClick(): Boolean {
        Log.d("ButtonComponent", "Performing button click")
        return replyTo("connect")
    }

    @Serializable
    data class MessageData(
        @SerialName("title") val title: String
    )
}