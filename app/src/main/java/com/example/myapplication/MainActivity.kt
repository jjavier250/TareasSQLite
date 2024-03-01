package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db:Datacenter= Datacenter(this)

        db.insertarTabla()

        //db.actualizarTabla()
        db.leerdatos()
    }
}