package com.alviacomercial.levantaientoinventarios

class Usuarios {
    fun obtenerDatosUsuario(login: String,password:String): MutableMap<String, String> {
        val db = LevInvconn().dbConn()
        val sql = "SELECT CodUser, CodSchema,NombreUsuario,UserLogin,Pass FROM UserMovilApp WHERE UserLogin = '${login.trim()}' AND Pass = '${password.trim()}'"
        val stmt = db?.createStatement()
        val rs = stmt?.executeQuery(sql)
        val datosUsuario = mutableMapOf<String, String>()
        while (rs?.next()==true) {
            datosUsuario["CodUser"] = rs.getString("CodUser")
            datosUsuario["CodSchema"] = rs.getString("CodSchema")
            datosUsuario["NombreUsuario"] = rs.getString("NombreUsuario")
            datosUsuario["UserLogin"] = rs.getString("UserLogin")
            datosUsuario["Pass"] = rs.getString("Pass")
        }
        return datosUsuario
    }
}