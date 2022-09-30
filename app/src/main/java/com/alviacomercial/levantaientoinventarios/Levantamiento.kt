package com.alviacomercial.levantaientoinventarios

class Levantamiento {

    fun getCodBodega(bodega:String) : Int {
        val db = SfexConn().dbConn()
        val sql = "SELECT CodBodega FROM Inventario.CatBodegas WHERE DesBodega = CAST( '$bodega' as nvarchar)"
        val stmt = db?.prepareStatement(sql)
        val rs = stmt?.executeQuery()
        var codBodega = 0
        while (rs?.next()==true) {
            codBodega = rs.getInt("CodBodega")
        }
        return codBodega
    }

    fun getCodRubro(rubro:String) : Int {
        val db = SfexConn().dbConn()
        val sql = "SELECT CodRubro FROM Inventario.RubroInv WHERE DescRubro = CAST( '$rubro' as nvarchar)"
        val stmt = db?.prepareStatement(sql)
        val rs = stmt?.executeQuery()
        var codRubro = 0
        while (rs?.next()==true) {
            codRubro = rs.getInt("CodRubro")
        }
        return codRubro
    }

    fun getCodSucursal(sucursal: String) : Int {
        val db = SfexConn().dbConn()
        val sql = "SELECT CodSucursal FROM Inventario.CatSucursales WHERE DescSucursal = CAST( '$sucursal' as nvarchar)"
        val stmt = db?.prepareStatement(sql)
        val rs = stmt?.executeQuery()
        var codSucursal = 0
        while (rs?.next()==true) {
            codSucursal = rs.getInt("CodSucursal")
        }
        return codSucursal
    }

     fun getCodUser(user: String) : Int {
        val db = LevInvconn().dbConn()
        val sql = "SELECT CodUser FROM UserMovilApp WHERE UserLogin = CAST( '$user' as nvarchar)"
        val stmt = db?.prepareStatement(sql)
        val rs = stmt?.executeQuery()
        var codUser = 0
        while (rs?.next()==true) {
            codUser = rs.getInt("CodUser")
        }
        return codUser
    }

    fun getCodLevantamiento(polin :String,codBodega : Int,codSucursal: Int): Int {
        val db = LevInvconn().dbConn()
        val sql = "SELECT CodLevantamiento FROM TBLLEVANTINVENCABEZADO WHERE Polin = CAST( '$polin' as nvarchar) AND CodBodega = $codBodega AND CodSucursal = $codSucursal"
        val stmt = db?.prepareStatement(sql)
        val rs = stmt?.executeQuery()
        var codLevantamiento = 0
        while (rs?.next()==true) {
            codLevantamiento = rs.getInt("CodLevantamiento")
        }
        return codLevantamiento
    }
}