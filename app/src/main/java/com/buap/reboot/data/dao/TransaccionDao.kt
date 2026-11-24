package com.buap.reboot.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.buap.reboot.data.bd.entidades.TransaccionEntity

@Dao
interface TransaccionDao {

    @Query("SELECT * FROM transacciones WHERE id_comprador = :idComprador ORDER BY fecha DESC")
    suspend fun obtenerPorComprador(idComprador: Long): List<TransaccionEntity>

    @Query("SELECT * FROM transacciones WHERE id_articulo = :idArticulo LIMIT 1")
    suspend fun obtenerPorArticulo(idArticulo: Long): TransaccionEntity?

    @Query("SELECT * FROM transacciones WHERE id = :idTransaccion LIMIT 1")
    suspend fun obtenerPorId(idTransaccion: Long): TransaccionEntity?

    @Insert
    suspend fun insertar(transaccion: TransaccionEntity): Long
}
