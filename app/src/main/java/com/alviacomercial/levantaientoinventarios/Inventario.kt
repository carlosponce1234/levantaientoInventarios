package com.alviacomercial.levantaientoinventarios

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator

@Suppress("OVERRIDE_DEPRECATION", "DEPRECATION", "NAME_SHADOWING")
class Inventario : AppCompatActivity() {
    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_inventario)

        val intent = this.intent
        val codUser = intent.getStringExtra("CodUser")
        val sucursal = intent.getStringExtra("Sucursal")
        val loginUser = intent.getStringExtra("UserLogin")
        val rubro = intent.getStringExtra("Rubro")
        val bodega = intent.getStringExtra("Bodega")




        val btnScan = this.findViewById<Button>(R.id.scanqr)
        btnScan.setOnClickListener {
            this.performeAction()
        }

        val btnguardar = this.findViewById<Button>(R.id.btnguardar)
        btnguardar.setOnClickListener {
            val etiqueta = this.findViewById<TextView>(R.id.etiqueta).text.toString()
            val polin = this.findViewById<TextView>(R.id.ubicacion).text.toString()
            val codBodega = Levantamiento().getCodBodega(bodega.toString())
            val codSucursal = Levantamiento().getCodSucursal(sucursal.toString())
            val codRubro = Levantamiento().getCodRubro(rubro.toString())
            val brazo = this.findViewById<TextView>(R.id.brazo).text.toString()
            val codigo = this.findViewById<TextView>(R.id.codigo).text.toString()
            val ancho = this.findViewById<TextView>(R.id.ancho).text.toString()
            val alto = this.findViewById<TextView>(R.id.alto).text.toString()
            val largo = this.findViewById<TextView>(R.id.largo).text.toString()
            val codMedida =
                Levantamiento().getCodMedida(alto.toFloat(), ancho.toFloat(), largo.toFloat())
            val codLev = codUser?.let { it1 ->
                Levantamiento().getCodLevantamiento(
                    polin,
                    codBodega,
                    codSucursal,
                    it1.toInt(),
                    codRubro
                )
            }
            val pconteo = this.findViewById<TextView>(R.id.conteo).text.toString()
            if (pconteo.isEmpty() || pconteo == "0" || pconteo.isBlank()) {
                Toast.makeText(this, "Debe ingresar el conteo", Toast.LENGTH_LONG).show()
            } else {
                if (Levantamiento().piezaCompleta(codMedida, codigo)) {
                    if (!Levantamiento().etiquetaRepetida(
                            etiqueta.toInt(),
                            codLev.toString().toInt(),
                            codBodega
                        )
                    ) {
                        Levantamiento().guardarPconteo(
                            codLev.toString().toInt(),
                            codigo,
                            brazo,
                            codMedida,
                            ancho.toFloat(),
                            alto.toFloat(),
                            largo.toFloat(),
                            pconteo.toFloat(),
                            etiqueta.toInt(),
                            codBodega
                        )
                        this.findViewById<TextView>(R.id.conteo).text = ""
                        this.findViewById<TextView>(R.id.etiqueta).text = ""
                        this.findViewById<TextView>(R.id.ubicacion).text = ""
                        this.findViewById<TextView>(R.id.brazo).text = ""
                        this.findViewById<TextView>(R.id.codigo).text = ""
                        this.findViewById<TextView>(R.id.descArt).text = ""
                        this.findViewById<TextView>(R.id.ancho).text = ""
                        this.findViewById<TextView>(R.id.alto).text = ""
                        this.findViewById<TextView>(R.id.largo).text = ""
                        Toast.makeText(this, "Guardado", Toast.LENGTH_SHORT).show()
                    } else {
                        if (Levantamiento().conteoCompleto(
                                codLev.toString().toInt(),
                                codBodega,
                                codigo,
                                etiqueta.toInt()
                            )
                        ) {
                            Toast.makeText(this, "Etiqueta ya fue levantada", Toast.LENGTH_SHORT)
                                .show()
                            this.findViewById<TextView>(R.id.conteo).text = ""
                            this.findViewById<TextView>(R.id.etiqueta).text = ""
                            this.findViewById<TextView>(R.id.ubicacion).text = ""
                            this.findViewById<TextView>(R.id.brazo).text = ""
                            this.findViewById<TextView>(R.id.codigo).text = ""
                            this.findViewById<TextView>(R.id.descArt).text = ""
                            this.findViewById<TextView>(R.id.ancho).text = ""
                            this.findViewById<TextView>(R.id.alto).text = ""
                            this.findViewById<TextView>(R.id.largo).text = ""
                        } else {
                            if (Levantamiento().tieneSegundoConteo(
                                    codLev.toString().toInt(),
                                    codBodega,
                                    codigo,
                                    etiqueta.toInt()
                                )
                            ) {
                                val tipo = 1
                                Levantamiento().updateConteo(
                                    codLev.toString().toInt(),
                                    codigo,
                                    etiqueta.toInt(),
                                    codBodega,
                                    pconteo.toFloat(),
                                    tipo
                                )
                            } else {
                                val tipo = 2
                                Levantamiento().updateConteo(
                                    codLev.toString().toInt(),
                                    codigo,
                                    etiqueta.toInt(),
                                    codBodega,
                                    pconteo.toFloat(),
                                    tipo
                                )
                            }
                            this.findViewById<TextView>(R.id.conteo).text = ""
                            this.findViewById<TextView>(R.id.etiqueta).text = ""
                            this.findViewById<TextView>(R.id.ubicacion).text = ""
                            this.findViewById<TextView>(R.id.brazo).text = ""
                            this.findViewById<TextView>(R.id.codigo).text = ""
                            this.findViewById<TextView>(R.id.descArt).text = ""
                            this.findViewById<TextView>(R.id.ancho).text = ""
                            this.findViewById<TextView>(R.id.alto).text = ""
                            this.findViewById<TextView>(R.id.largo).text = ""
                            Toast.makeText(this, "Guardado", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Pieza no completa", Toast.LENGTH_SHORT).show()
                }
            }
        }
        val btnFinalizar = this.findViewById<Button>(R.id.finalizar)
        btnFinalizar.setOnClickListener(){
            val intent = Intent(this, RubrosBodegas::class.java)
            intent.putExtra("CodUser", codUser)
            intent.putExtra("UserLogin", loginUser)
            intent.putExtra("Sucursal", sucursal)
            startActivity(intent)
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
                Toast.makeText(this, this.getString(R.string.result_not_found), Toast.LENGTH_LONG).show()
            } else {
                // If QRCode contains data.
                try {
                    // Converting the data to json format
                    val obj = (result.contents).toString()
                    val datos = obj.split(",")
                    // Show values in UI.
                    val etiqueta = this.findViewById<TextView>(R.id.etiqueta)
                    val polin = this.findViewById<TextView>(R.id.ubicacion)
                    val brazo = this.findViewById<TextView>(R.id.brazo)
                    val codigo = this.findViewById<TextView>(R.id.codigo)
                    val descripcion = this.findViewById<TextView>(R.id.descArt)
                    val ancho = this.findViewById<TextView>(R.id.ancho)
                    val alto = this.findViewById<TextView>(R.id.alto)
                    val largo = this.findViewById<TextView>(R.id.largo)
                    etiqueta.text = datos[0]
                    polin.text = datos[1]
                    brazo.text = datos[2]
                    codigo.text = datos[3]
                    ancho.text = datos[5]
                    alto.text = datos[6]
                    largo.text = datos[7]
                    descripcion.text = datos[4]

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
