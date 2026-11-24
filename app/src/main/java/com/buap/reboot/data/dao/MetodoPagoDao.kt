package com.buap.reboot.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.buap.reboot.data.bd.entidades.MetodoPagoEntity

@Dao
interface MetodoPagoDao {

    @Query("SELECT * FROM metodos_pago WHERE id_usuario = :idUsuario ORDER BY es_predeterminado DESC")
    suspend fun obtenerPorUsuario(idUsuario: Long): List<MetodoPagoEntity>

    @Query("SELECT * FROM metodos_pago WHERE id_usuario = :idUsuario AND es_predeterminado = 1 LIMIT 1")
    suspend fun obtenerPredeterminado(idUsuario: Long): MetodoPagoEntity?

    @Insert
    suspend fun insertar(metodoPago: MetodoPagoEntity): Long

    @Query("DELETE FROM metodos_pago WHERE id = :idMetodoPago")
    suspend fun eliminar(idMetodoPago: Long)

    @Query("UPDATE metodos_pago SET es_predeterminado = 0 WHERE id_usuario = :idUsuario")
    suspend fun limpiarPredeterminado(idUsuario: Long)

    @Query("UPDATE metodos_pago SET es_predeterminado = 1 WHERE id = :idMetodoPago")
    suspend fun marcarComoPredeterminado(idMetodoPago: Long)
}
