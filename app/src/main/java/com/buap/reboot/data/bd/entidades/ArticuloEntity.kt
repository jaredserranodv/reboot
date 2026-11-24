package com.buap.reboot.data.bd.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "articulos",
    foreignKeys = [
        ForeignKey(
            entity = UsuarioEntity::class,
            parentColumns = ["id"],
            childColumns = ["id_vendedor"]
        )
    ],
    indices = [Index(value = ["id_vendedor"])]
)
data class ArticuloEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "id_vendedor") val idVendedor: Long,
    val titulo: String,
    val descripcion: String,
    val categoria: String,
    val precio: Double,
    @ColumnInfo(name = "estado_articulo") val estadoArticulo: String,
    val ciudad: String,
    @ColumnInfo(name = "fecha_publicacion") val fechaPublicacion: String,
    @ColumnInfo(name = "ruta_imagen") val rutaImagen: String?,
    @ColumnInfo(defaultValue = "1") val disponible: Boolean = true
)
