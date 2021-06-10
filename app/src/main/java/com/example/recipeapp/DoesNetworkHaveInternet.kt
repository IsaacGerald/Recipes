package com.example.recipeapp

import android.util.Log
import java.io.IOException
import java.net.InetSocketAddress
import javax.net.SocketFactory

object DoesNetworkHaveInternet {
    //execute on a background thread
    fun execute(socketFactory: SocketFactory): Boolean{
        return try {
            Log.d(TAG, "execute: PINGING google.")
            val  socket = socketFactory.createSocket() ?:
            throw IOException("Socket is null")
            socket.connect(InetSocketAddress("8.8.8.8", 53), 1500)
            socket.close()
            Log.d(TAG, "execute: PING success.")
            true
        }catch (e: IOException){
            Log.e(TAG, "execute: No Internet connection. $e" )
            false
        }
    }
}