package com.buap.reboot.data.bd.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "direcciones_envio",
    foreignKeys = [
        ForeignKey(entity = UsuarioEntity::class, parentColumns = ["id"], childColumns = ["id_usuario"])
    ],
    indices = [Index(value = ["id_usuario"])]
)
data class DireccionEnvioEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "id_usuario") val idUsuario: Long,
    val etiqueta: String,
    @ColumnInfo(name = "calle_y_numero") val calleYNumero: String,
    val colonia: String,
    val ciudad: String,
    @ColumnInfo(name = "codigo_postal") val codigoPostal: String,
    @ColumnInfo(name = "telefono_contacto") val telefonoContacto: String,
    @ColumnInfo(name = "es_predeterminada", defaultValue = "0") val esPredeterminada: Boolean = false
)
