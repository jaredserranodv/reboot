package com.buap.reboot.data.bd.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "notificaciones",
    foreignKeys = [
        ForeignKey(
            entity = UsuarioEntity::class,
            parentColumns = ["id"],
            childColumns = ["id_usuario"]
        )
    ],
    indices = [Index(value = ["id_usuario"])]
)
data class NotificacionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "id_usuario") val idUsuario: Long,
    val tipo: String,
    val titulo: String,
    val mensaje: String,
    val fecha: String,
    val leida: Boolean
)
