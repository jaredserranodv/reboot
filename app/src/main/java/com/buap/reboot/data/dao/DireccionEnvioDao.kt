package com.buap.reboot.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.buap.reboot.data.bd.entidades.DireccionEnvioEntity

@Dao
interface DireccionEnvioDao {

    @Query("SELECT * FROM direcciones_envio WHERE id_usuario = :idUsuario ORDER BY es_predeterminada DESC")
    suspend fun obtenerPorUsuario(idUsuario: Long): List<DireccionEnvioEntity>

    @Query("SELECT * FROM direcciones_envio WHERE id_usuario = :idUsuario AND es_predeterminada = 1 LIMIT 1")
    suspend fun obtenerPredeterminada(idUsuario: Long): DireccionEnvioEntity?

    @Query("SELECT * FROM direcciones_envio WHERE id = :idDireccion LIMIT 1")
    suspend fun obtenerPorId(idDireccion: Long): DireccionEnvioEntity?

    @Insert
    suspend fun insertar(direccion: DireccionEnvioEntity): Long

    @Update
    suspend fun actualizar(direccion: DireccionEnvioEntity)

    @Query("DELETE FROM direcciones_envio WHERE id = :idDireccion")
    suspend fun eliminar(idDireccion: Long)

    @Query("UPDATE direcciones_envio SET es_predeterminada = 0 WHERE id_usuario = :idUsuario")
    suspend fun limpiarPredeterminada(idUsuario: Long)

    @Query("UPDATE direcciones_envio SET es_predeterminada = 1 WHERE id = :idDireccion")
    suspend fun marcarComoPredeterminada(idDireccion: Long)
}
