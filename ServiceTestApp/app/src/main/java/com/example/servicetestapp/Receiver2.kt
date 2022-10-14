package com.example.servicetestapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class Receiver2 : BroadcastReceiver(){
    override fun onReceive(p0: Context?, p1: Intent?) {
        val catCall = "android.intent.action.CATCALL"
        val airplaneMode = p1?.getBooleanExtra("state", false) ?: return
        val action = p1.action
        when (action){
            Intent.ACTION_AIRPLANE_MODE_CHANGED -> {
                if (airplaneMode) {
                    Toast.makeText(p0, "Airplane mode is enabled", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(p0, "Airplane mode is disabled", Toast.LENGTH_LONG).show()
                }
            }
            catCall -> {
                val cat = p1.getStringExtra("Cat")
                Log.d("Lmeow", "Hehe $cat")
                Toast.makeText(p0, "$cat", Toast.LENGTH_LONG).show()
            }
        }
    }
}