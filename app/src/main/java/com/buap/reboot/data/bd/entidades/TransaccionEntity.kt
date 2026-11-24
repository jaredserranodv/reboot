package com.buap.reboot.data.bd.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "transacciones",
    foreignKeys = [
        ForeignKey(
            entity = ArticuloEntity::class,
            parentColumns = ["id"],
            childColumns = ["id_articulo"]
        ),
        ForeignKey(
            entity = UsuarioEntity::class,
            parentColumns = ["id"],
            childColumns = ["id_comprador"]
        )
    ],
    indices = [Index(value = ["id_articulo"]), Index(value = ["id_comprador"])]
)
data class TransaccionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "id_articulo") val idArticulo: Long,
    @ColumnInfo(name = "id_comprador") val idComprador: Long,
    @ColumnInfo(name = "total_pagado") val totalPagado: Double,
    val estado: String,
    val fecha: String,
    @ColumnInfo(name = "direccion_envio") val direccionEnvio: String
)
