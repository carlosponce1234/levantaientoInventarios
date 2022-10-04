package com.alviacomercial.levantaientoinventarios

import java.sql.ResultSet

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

    fun getDescArticulo(codArticulo: Int) : String {
        val db = SfexConn().dbConn()
        val sql = "SELECT DesCatArt FROM Inventario.CatArticulos WHERE CodArticulo = $codArticulo"
        val stmt = db?.prepareStatement(sql)
        val rs = stmt?.executeQuery()
        var descArticulo = "No existe"
        while (rs?.next()==true) {
            descArticulo = rs.getString("DesCatArt")
        }
        return descArticulo
    }

    /* fun getCodUser(user: String) : Int {
        val db = LevInvconn().dbConn()
        val sql = "SELECT CodUser FROM UserMovilApp WHERE UserLogin = CAST( '$user' as nvarchar)"
        val stmt = db?.prepareStatement(sql)
        val rs = stmt?.executeQuery()
        var codUser = 0
        while (rs?.next()==true) {
            codUser = rs.getInt("CodUser")
        }
        return codUser
    }*/ // This function is not used

    fun getCodLevantamiento(polin :String,codBodega : Int,codSucursal: Int,codUser:Int,codRubro: Int): Int {
        val db = LevInvconn().dbConn()
        val sql = "SELECT CodLevantamiento FROM TBLLEVANTINVENCABEZADO WHERE Polin = CAST( '$polin' as nvarchar) AND CodBodega = $codBodega AND CodSucursal = $codSucursal"
        val stmt = db?.prepareStatement(sql)
        val rs = stmt?.executeQuery()
        var codLevantamiento = 0
        while (rs?.next()==true) {
            codLevantamiento = rs.getInt("CodLevantamiento")
        }
        val resultado=guardarLevantamiento(codUser,codSucursal,codRubro,codBodega,polin,codLevantamiento)
        if (resultado.next()) {
            codLevantamiento = resultado.getInt("CodLevantamiento")
        }
        return codLevantamiento
    }

    fun guardarLevantamiento(codUser:Int, dosSucursal:Int, codRubro:Int, codBodega:Int, polin:String, codLevantamiento: Int) : ResultSet {
        val db = LevInvconn().dbConn()
        val sql = "EXEC MovilAppCabeceraLevantamiento @CodUser = $codUser, @Codsucursal = $dosSucursal, @Codrubro = $codRubro, @Codbodega = $codBodega, @polin = '$polin', @CodLevantamiento = $codLevantamiento"
        val stmt = db?.prepareStatement(sql)
        val rs = stmt?.executeQuery()
        return rs!!
    }
    fun getCodMedida(alto:Float,ancho:Float,largo:Float): Int {
        val db = SfexConn().dbConn()
        val sql = "SELECT CodMedidas FROM Inventario.CatMedidas WHERE AltoArticulo = $alto AND AnchoArticulo = $ancho AND LargoArticulo = $largo"
        val stmt = db?.prepareStatement(sql)
        val rs = stmt?.executeQuery()
        var codMedida = 0
        while (rs?.next()==true) {
            codMedida = rs.getInt("CodMedidas")
        }
        return codMedida
    }
    fun piezaCompleta(codMedida:Int,codArticulo:String):Boolean{
        val db = SfexConn().dbConn()
        val sql = "SELECT CodMedidas  FROM Inventario.CatArticulosDet where CodMedidas = $codMedida and CodArticulo = '$codArticulo' and EsPiezaCompleta = 1"
        val stmt = db?.prepareStatement(sql)
        return stmt?.executeQuery()?.next()==true
    }

    fun etiquetaRepetida(etiqueta:Int,codLevantamiento:Int,codBodega: Int):Boolean{
        val db = LevInvconn().dbConn()
        val sql = "SELECT CodLevantamiento FROM TBLLEVANTINVDETALLE WHERE CodLevantamiento = $codLevantamiento AND CodPC = 42 AND CodBodega = $codBodega AND Verificacion = $etiqueta"
        val stmt = db?.prepareStatement(sql)
        val rs = stmt?.executeQuery()
        return rs?.next()==true
    }
    fun conteoCompleto(codLevantamiento:Int,codBodega: Int,codArticulo: String,etiqueta: Int):Boolean{
        val db = LevInvconn().dbConn()
        val sql = "SELECT CodLevantamiento FROM TBLLEVANTINVDETALLE WHERE CodLevantamiento = $codLevantamiento AND CodPC = 42 AND CodBodega = $codBodega AND Verificacion = $etiqueta AND CodArticulo=$codArticulo AND PConteo>0 AND SConteo>0 AND ConteoIngreso>0"
        val stmt = db?.prepareStatement(sql)
        val rs = stmt?.executeQuery()
        return rs?.next()==true
    }
    fun tieneSegundoConteo(codLevantamiento:Int,codBodega: Int,codArticulo: String,etiqueta: Int):Boolean{
        val db = LevInvconn().dbConn()
        val sql = "SELECT CodLevantamiento FROM TBLLEVANTINVDETALLE WHERE CodLevantamiento = $codLevantamiento AND CodPC = 42 AND CodBodega = $codBodega AND Verificacion = $etiqueta AND CodArticulo=$codArticulo AND SConteo>0 AND PConteo>0"
        val stmt = db?.prepareStatement(sql)
        val rs = stmt?.executeQuery()
        return rs?.next()==true
    }
    fun guardarPconteo(codLevantamiento: Int,codArticulo: String,brazo:String,codMedida: Int,ancho: Float,alto: Float,largo: Float,pconteo:Float,etiqueta: Int,codBodega: Int) {
        val db = LevInvconn().dbConn()
        val sql ="EXEC MovilAppDetalleLevantamiento @CodLevantamiento = $codLevantamiento, @CodArticulo = '$codArticulo', @Brazo = '$brazo', @CodMedida = $codMedida, @Ancho = $ancho, @Alto = $alto, @Largo = $largo, @PConteo = $pconteo, @Verificacion = $etiqueta, @CodBodega = $codBodega"
        val stmt = db?.prepareStatement(sql)
        if (stmt?.execute()==true) {
            println("PConteo guardado")
        }
    }
    fun updateConteo(codLevantamiento: Int,codArticulo:String,etiqueta: Int,codBodega: Int,conteo:Float,tipo:Int) {
        val db = LevInvconn().dbConn()
        val sql ="EXEC MovilAppUpdateConteo @CodLevantamiento = $codLevantamiento, @CodArticulo = '$codArticulo', @Verificacion = $etiqueta, @CodBodega = $codBodega, @Conteo = $conteo , @Tipo = $tipo"
        val stmt = db?.prepareStatement(sql)
        if (stmt?.execute()==true) {
            println("ConteoIngreso guardado")
        }
    }
}