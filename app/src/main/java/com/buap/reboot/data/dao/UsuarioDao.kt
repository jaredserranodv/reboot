package com.buap.reboot.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.buap.reboot.data.bd.entidades.UsuarioEntity

@Dao
interface UsuarioDao {

    @Query("SELECT * FROM usuarios WHERE correo = :correo LIMIT 1")
    suspend fun buscarPorCorreo(correo: String): UsuarioEntity?

    @Query("SELECT * FROM usuarios WHERE id = :id LIMIT 1")
    suspend fun buscarPorId(id: Long): UsuarioEntity?

    @Insert
    suspend fun insertar(usuario: UsuarioEntity): Long

    @Query("UPDATE usuarios SET contrasena_hash = :nuevaContrasenaHash WHERE id = :idUsuario")
    suspend fun actualizarContrasena(idUsuario: Long, nuevaContrasenaHash: String)

    @Update
    suspend fun actualizar(usuario: UsuarioEntity)
}
