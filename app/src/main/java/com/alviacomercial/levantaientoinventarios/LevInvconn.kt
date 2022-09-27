package com.alviacomercial.levantaientoinventarios

import android.os.StrictMode
import android.util.Log
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class LevInvconn {
    private val ip = "192.168.1.3"
    private val db = "LevInventario"
    private val user = "appMovil"
    private val pass = "12345@alvia"

    fun dbConn() : Connection? {
        val policy = StrictMode . ThreadPolicy . Builder () . permitAll () . build ()
        StrictMode . setThreadPolicy ( policy )
        var conn : Connection? = null
        val connString: String?
        try {
            Class.forName ( "net.sourceforge.jtds.jdbc.Driver" )
            connString = "jdbc:jtds:sqlserver://$ip;databaseName=$db;user=$user;password=$pass;"
            conn= DriverManager.getConnection ( connString )
        } catch ( ex : SQLException) {
            Log.e ( "Error/SQL" , ex . message.toString())
        } catch ( ex1 : ClassNotFoundException ) {
            Log.e ( "Error/Class" , ex1 . message.toString())
        } catch ( ex2 : Exception ) {
            Log.e ( "Error/General" , ex2 . message.toString() )
        }
        return conn
    }
}