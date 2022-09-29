package com.alviacomercial.levantaientoinventarios

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import java.sql.SQLException

@Suppress("NAME_SHADOWING")
class RubrosBodegas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rubros_bodegas)

        val intent = intent
        val codUser = intent.getStringExtra("CodUser")
        val loginUser = intent.getStringExtra("UserLogin")
        val sucursal = intent.getStringExtra("Sucursal")

        val listaRubros = findViewById<Spinner>(R.id.rubros)
        val listaBodegas = findViewById<Spinner>(R.id.bodegas)
        val txtSucursal = findViewById<TextView>(R.id.textView9)
        txtSucursal.text = sucursal

        val rubros = loadRubros()
        //Toast.makeText(this, "Sucursal es $sucursal", Toast.LENGTH_SHORT).show()

        if (listaRubros != null) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, rubros)
            listaRubros.adapter = adapter
        }

       listaRubros.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val rubro = parent.getItemAtPosition(position).toString()
                val bodegas = loadBodegas(rubro.trim(), txtSucursal.text.toString().trim())
                Toast.makeText(this@RubrosBodegas, "${rubro.trim()} / ${txtSucursal.text.trim()}", Toast.LENGTH_SHORT).show()

                when {
                    rubro != "" -> {
                        if (listaBodegas != null) {
                            val adapter = ArrayAdapter(this@RubrosBodegas, android.R.layout.simple_spinner_item, bodegas)
                            listaBodegas.adapter = adapter
                        }
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // show message when nothing selected
                Toast.makeText(this@RubrosBodegas, "Sin Bodegas", Toast.LENGTH_SHORT).show()
            }
        }



        val btnIniciar = findViewById<Button>(R.id.iniciar)
        btnIniciar.setOnClickListener {
            val bodega = listaBodegas.selectedItem.toString()
            val rubro = listaRubros.selectedItem.toString()
            val intent = Intent(this, Inventario::class.java)
            intent.putExtra("CodUser", codUser)
            intent.putExtra("UserLogin", loginUser)
            intent.putExtra("Sucursal", sucursal)
            intent.putExtra("Rubro", rubro)
            intent.putExtra("Bodega", bodega)
            startActivity(intent)
        }
    }
}

fun loadRubros() : List<String>  {
    val db = SfexConn().dbConn()
    val sql = "SELECT CodRubro,DescRubro FROM Inventario.RubroInv WHERE CodRubro != 5"
    val stmt = db?.createStatement()
    val rs = stmt?.executeQuery(sql)

    val rubrosList = mutableListOf<String>()
    while (rs?.next()==true) {
        rubrosList.add(rs.getString("DescRubro"))
    }
    return rubrosList
}

fun loadBodegas(rubro : String , sucursal : String) : List<String> {
    val db = SfexConn().dbConn()
    val sql = "EXEC Inventario.AppMovil_ListarBodegas @sucursal = ?, @rubro = ?"
    val stmt = db?.prepareStatement(sql)
    stmt?.setString(1, sucursal.trim())
    stmt?.setString(2, rubro.trim())
    return try {
        val rs = stmt?.executeQuery()
        val bodegas = mutableListOf<String>()
        while (rs?.next()==true) {
            bodegas.add(rs.getString("Bodega"))
        }
        if (bodegas.isEmpty()) {
            bodegas.add("No hay bodegas")
        }
        bodegas
    } catch (e: SQLException) {
        val bodegas = mutableListOf<String>()
        bodegas.add("Error al cargar bodegas $e")
        bodegas
    }
}