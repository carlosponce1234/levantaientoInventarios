@file:Suppress("NAME_SHADOWING")

package com.alviacomercial.levantaientoinventarios

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView


class Sucursales : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sucursales)
    val intent = intent
    val codUser = intent.getStringExtra("CodUser")
    val nombreUser = intent.getStringExtra("NombreUsuario")
    val loginUser = intent.getStringExtra("UserLogin")
        val listaSucursales = findViewById<ListView>(R.id.listaSucursales)
        val sucursales = loadSucursales()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, sucursales)
        listaSucursales.adapter = adapter

        listaSucursales.setOnItemClickListener { parent, view, position, id ->
            val sucursal = parent.getItemAtPosition(position).toString()
            val intent = Intent(this, RubrosBodegas::class.java)
            intent.putExtra("CodUser", codUser)
            intent.putExtra("NombreUsuario", nombreUser)
            intent.putExtra("UserLogin", loginUser)
            intent.putExtra("Sucursal", sucursal)

            startActivity(intent)
        }
    }
}

fun loadSucursales() : List<String> {
    val db = SfexConn().dbConn()
    val sql = "Select CodSucursal , DescSucursal from Inventario.CatSucursales where CodSucursal < 100"
    val stmt = db?.createStatement()
    val rs = stmt?.executeQuery(sql)

    val sucursalesList = mutableListOf<String>()
    while (rs?.next()==true) {
        sucursalesList.add(rs.getString("DescSucursal"))
    }
    return sucursalesList
}