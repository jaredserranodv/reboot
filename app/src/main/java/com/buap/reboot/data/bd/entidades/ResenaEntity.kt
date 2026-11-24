package com.buap.reboot.data.bd.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "resenas",
    foreignKeys = [
        ForeignKey(entity = TransaccionEntity::class, parentColumns = ["id"], childColumns = ["id_transaccion"]),
        ForeignKey(entity = UsuarioEntity::class, parentColumns = ["id"], childColumns = ["id_vendedor"]),
        ForeignKey(entity = UsuarioEntity::class, parentColumns = ["id"], childColumns = ["id_comprador"])
    ],
    indices = [
        Index(value = ["id_transaccion"], unique = true),
        Index(value = ["id_vendedor"]),
        Index(value = ["id_comprador"])
    ]
)
data class ResenaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "id_transaccion") val idTransaccion: Long,
    @ColumnInfo(name = "id_vendedor") val idVendedor: Long,
    @ColumnInfo(name = "id_comprador") val idComprador: Long,
    val calificacion: Int,
    val comentario: String,
    val recomendado: Boolean,
    val fecha: String
)
