package com.buap.reboot.data.bd.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

// Nunca se guarda el numero completo de tarjeta ni el CVV, solo los ultimos 4 digitos.
@Entity(
    tableName = "metodos_pago",
    foreignKeys = [
        ForeignKey(entity = UsuarioEntity::class, parentColumns = ["id"], childColumns = ["id_usuario"])
    ],
    indices = [Index(value = ["id_usuario"])]
)
data class MetodoPagoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "id_usuario") val idUsuario: Long,
    @ColumnInfo(name = "tipo_tarjeta") val tipoTarjeta: String,
    @ColumnInfo(name = "ultimos_cuatro_digitos") val ultimosCuatroDigitos: String,
    @ColumnInfo(name = "mes_vencimiento") val mesVencimiento: Int,
    @ColumnInfo(name = "anio_vencimiento") val anioVencimiento: Int,
    @ColumnInfo(name = "es_predeterminado", defaultValue = "0") val esPredeterminado: Boolean = false
)
