package com.buap.reboot.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.buap.reboot.data.bd.entidades.ArticuloEntity

private const val SQL_BUSCAR = """
    SELECT * FROM articulos
    WHERE disponible = 1
      AND (:texto IS NULL OR :texto = '' OR LOWER(titulo) LIKE '%' || LOWER(:texto) || '%')
      AND (:categoria IS NULL OR :categoria = '' OR LOWER(TRIM(categoria)) = LOWER(TRIM(:categoria)))
      AND (:ciudad IS NULL OR :ciudad = '' OR LOWER(TRIM(ciudad)) = LOWER(TRIM(:ciudad)))
      AND (:estadoArticulo IS NULL OR :estadoArticulo = '' OR LOWER(TRIM(estado_articulo)) = LOWER(TRIM(:estadoArticulo)))
      AND (:precioMinimo IS NULL OR precio >= :precioMinimo)
      AND (:precioMaximo IS NULL OR precio <= :precioMaximo)
    ORDER BY fecha_publicacion DESC
"""

@Dao
interface ArticuloDao {

    @Query(SQL_BUSCAR)
    suspend fun buscar(
        texto: String?,
        categoria: String?,
        ciudad: String?,
        estadoArticulo: String?,
        precioMinimo: Double?,
        precioMaximo: Double?
    ): List<ArticuloEntity>

    // Variante observable en vivo, lista para que una pantalla de resultados la observe directamente.
    @Query(SQL_BUSCAR)
    fun observarBusqueda(
        texto: String?,
        categoria: String?,
        ciudad: String?,
        estadoArticulo: String?,
        precioMinimo: Double?,
        precioMaximo: Double?
    ): LiveData<List<ArticuloEntity>>

    @Query("SELECT * FROM articulos WHERE id = :id LIMIT 1")
    suspend fun obtenerPorId(id: Long): ArticuloEntity?

    @Query("SELECT * FROM articulos WHERE id_vendedor = :idVendedor ORDER BY fecha_publicacion DESC")
    suspend fun obtenerPorVendedor(idVendedor: Long): List<ArticuloEntity>

    @Insert
    suspend fun insertar(articulo: ArticuloEntity): Long

    @Update
    suspend fun actualizar(articulo: ArticuloEntity)

    @Query("DELETE FROM articulos WHERE id = :idArticulo")
    suspend fun eliminar(idArticulo: Long)
}
