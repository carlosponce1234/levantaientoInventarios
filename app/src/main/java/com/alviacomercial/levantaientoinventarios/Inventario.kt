package com.alviacomercial.levantaientoinventarios

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class Inventario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventario)

        val intent = intent
        val codUser = intent.getStringExtra("CodUser")
        val loginUser = intent.getStringExtra("UserLogin")
        val sucursal = intent.getStringExtra("Sucursal")
        val rubro = intent.getStringExtra("Rubro")
        val bodega = intent.getStringExtra("Bodega")

        Toast.makeText(this, "Rubro: $rubro / Bodega: $bodega", Toast.LENGTH_SHORT).show()
        Toast.makeText(this, "Sucursal: $sucursal", Toast.LENGTH_SHORT).show()
        Toast.makeText(this, "Usuario: $loginUser", Toast.LENGTH_SHORT).show()
        Toast.makeText(this, "CodUser: $codUser", Toast.LENGTH_SHORT).show()
    }
}