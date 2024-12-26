package com.example.hotwirenativedemo.components

import android.annotation.SuppressLint
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
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

    private val fragment: Fragment
        get() = delegate.destination.fragment

    private var buttonTitle: String? = null
    private var menuItem: MenuItem? = null

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
        Log.d("ButtonComponent", "Handling connect with data: $data")

//        buttonTitle = data.title
//
//        val activity = delegate.destination.fragment.activity as? AppCompatActivity ?: return
//
//        activity.addMenuProvider(object : MenuProvider {
//            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
//                // Adiciona o botão ao toolbar
//                menuItem = menu.add(
//                    Menu.NONE,
//                    MENU_ITEM_ID,        // ID fixo, ex: 9999
//                    Menu.CATEGORY_SECONDARY,
//                    buttonTitle
//                ).apply {
//                    setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
//                }
//            }
//
//            override fun onMenuItemSelected(item: MenuItem): Boolean {
//                return if (item.itemId == MENU_ITEM_ID) {
//                    Log.d("ButtonComponent", "Button clicked")
//                    performButtonClick()
//                } else false
//            }
//        }, activity, Lifecycle.State.RESUMED)
    }

    private fun performButtonClick(): Boolean {
        Log.d("ButtonComponent", "Performing button click")
        return replyTo("connect")
    }

    companion object {
        private const val MENU_ITEM_ID = 9999
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun toobarWithButton() {
        androidx.compose.material3.Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Minha Toolbar") },
                    actions = {
                        IconButton(
                            onClick = {
                                performButtonClick()
                            }
                        ) {
                            Text("New Url")
                        }
                    }
                )
            }
        ) { Text("Aviso") }
    }

    @Serializable
    data class MessageData(
        @SerialName("title") val title: String
    )
}