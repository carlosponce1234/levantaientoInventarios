package com.alviacomercial.levantaientoinventarios

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val usuarios = findViewById<EditText>(R.id.usuario)
        val contrasena = findViewById<EditText>(R.id.contraseña)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val login = usuarios.text.toString()
            val password = contrasena.text.toString()
            val datosUsuario = Usuarios().obtenerDatosUsuario(login, password)
            if (datosUsuario.isEmpty() ) {
                Toast.makeText(this, "Usuario o Contraseña Incorrecto ", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, "Bienvenido ${datosUsuario["NombreUsuario"]}", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, Sucursales::class.java)
                intent.putExtra("CodUser", datosUsuario["CodUser"])
                intent.putExtra("NombreUsuario", datosUsuario["NombreUsuario"])
                intent.putExtra("UserLogin" , datosUsuario["UserLogin"])
                startActivity(intent)
            }
        }
    }
}