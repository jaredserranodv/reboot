package com.buap.reboot.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.buap.reboot.data.bd.entidades.ResenaEntity

@Dao
interface ResenaDao {

    @Query("SELECT * FROM resenas WHERE id_vendedor = :idVendedor")
    suspend fun obtenerPorVendedor(idVendedor: Long): List<ResenaEntity>

    @Query("SELECT * FROM resenas WHERE id_transaccion = :idTransaccion LIMIT 1")
    suspend fun obtenerPorTransaccion(idTransaccion: Long): ResenaEntity?

    @Insert
    suspend fun insertar(resena: ResenaEntity): Long
}
