package com.buap.reboot.data.bd.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "usuarios",
    indices = [Index(value = ["correo"], unique = true)]
)
data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "nombre_completo") val nombreCompleto: String,
    val correo: String,
    val telefono: String,
    @ColumnInfo(name = "contrasena_hash") val contrasenaHash: String,
    @ColumnInfo(name = "pregunta_seguridad") val preguntaSeguridad: String,
    @ColumnInfo(name = "respuesta_seguridad") val respuestaSeguridad: String,
    @ColumnInfo(name = "fecha_registro") val fechaRegistro: String,
    @ColumnInfo(name = "ruta_foto_perfil") val rutaFotoPerfil: String? = null
)
