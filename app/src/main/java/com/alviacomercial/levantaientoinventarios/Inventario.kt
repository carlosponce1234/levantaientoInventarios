package com.alviacomercial.levantaientoinventarios

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator

@Suppress("OVERRIDE_DEPRECATION")
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




        val btnScan = findViewById<Button>(R.id.scanqr)
        btnScan.setOnClickListener {
            performeAction()
        }





    }

    private fun performeAction() {
        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
        integrator.setPrompt("Scan")
        integrator.setCameraId(0)
        integrator.setBeepEnabled(true)
        integrator.setOrientationLocked(false)
        integrator.setBarcodeImageEnabled(false)
        integrator.initiateScan()
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            // If QRCode has no data.
            if (result.contents == null) {
                Toast.makeText(this, getString(R.string.result_not_found), Toast.LENGTH_LONG).show()
            } else {
                // If QRCode contains data.
                try {
                    // Converting the data to json format
                    val obj = (result.contents).toString()
                    val datos = obj.split(",")
                    // Show values in UI.


                } catch (e: Exception) {
                    // Data not in the expected format. So, whole object as toast message.
                    Toast.makeText(this, result.contents, Toast.LENGTH_LONG).show()
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
