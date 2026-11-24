package com.buap.reboot.dominio.casosUso

import com.buap.reboot.dominio.modelo.Resena
import com.buap.reboot.dominio.repositorio.RepositorioResenas
import com.buap.reboot.nucleo.Resultado

private const val CALIFICACION_MINIMA = 1
private const val CALIFICACION_MAXIMA = 5

class EnviarResenaCasoUso(
    private val repositorioResenas: RepositorioResenas
) {
    fun ejecutar(
        idTransaccion: Long,
        idVendedor: Long,
        idComprador: Long,
        calificacion: Int,
        comentario: String,
        recomendado: Boolean,
        fecha: String
    ): Resultado<Long> {
        if (calificacion < CALIFICACION_MINIMA || calificacion > CALIFICACION_MAXIMA) {
            return Resultado.Error("La calificacion debe ser de 1 a 5 estrellas")
        }
        if (repositorioResenas.obtenerPorTransaccion(idTransaccion) != null) {
            return Resultado.Error("Ya existe una resena para esta compra")
        }

        val resena = Resena(
            id = 0,
            idTransaccion = idTransaccion,
            idVendedor = idVendedor,
            idComprador = idComprador,
            calificacion = calificacion,
            comentario = comentario.trim(),
            recomendado = recomendado,
            fecha = fecha
        )
        return Resultado.Exito(repositorioResenas.registrar(resena))
    }
}
