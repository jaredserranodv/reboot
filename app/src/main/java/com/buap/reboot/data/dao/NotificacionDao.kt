package com.buap.reboot.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.buap.reboot.data.bd.entidades.NotificacionEntity

@Dao
interface NotificacionDao {

    @Query("SELECT * FROM notificaciones WHERE id_usuario = :idUsuario ORDER BY fecha DESC")
    suspend fun obtenerPorUsuario(idUsuario: Long): List<NotificacionEntity>

    // Variante observable en vivo, lista para que el Buzon la observe directamente.
    @Query("SELECT * FROM notificaciones WHERE id_usuario = :idUsuario ORDER BY fecha DESC")
    fun observarPorUsuario(idUsuario: Long): LiveData<List<NotificacionEntity>>

    @Query("UPDATE notificaciones SET leida = 1 WHERE id = :idNotificacion")
    suspend fun marcarComoLeida(idNotificacion: Long)

    @Insert
    suspend fun insertar(notificacion: NotificacionEntity): Long
}
