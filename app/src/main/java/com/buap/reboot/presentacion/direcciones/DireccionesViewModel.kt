package com.buap.reboot.presentacion.direcciones

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.buap.reboot.dominio.casosUso.EliminarDireccionCasoUso
import com.buap.reboot.dominio.casosUso.MarcarDireccionPredeterminadaCasoUso
import com.buap.reboot.dominio.casosUso.ObtenerDireccionesCasoUso
import com.buap.reboot.dominio.casosUso.ObtenerSesionActualCasoUso
import com.buap.reboot.nucleo.Resultado

class DireccionesViewModel(
    private val obtenerDireccionesCasoUso: ObtenerDireccionesCasoUso,
    private val eliminarDireccionCasoUso: EliminarDireccionCasoUso,
    private val marcarDireccionPredeterminadaCasoUso: MarcarDireccionPredeterminadaCasoUso,
    private val obtenerSesionActualCasoUso: ObtenerSesionActualCasoUso
) : ViewModel() {

    private val _estado = MutableLiveData(EstadoDirecciones())
    val estado: LiveData<EstadoDirecciones> = _estado

    private fun idUsuarioActual(): Long? = obtenerSesionActualCasoUso.ejecutar()?.id

    fun cargar() {
        val idUsuario = idUsuarioActual()
        if (idUsuario == null) {
            _estado.value = EstadoDirecciones(cargando = false, error = "No hay sesion activa")
            return
        }
        when (val resultado = obtenerDireccionesCasoUso.ejecutar(idUsuario)) {
            is Resultado.Exito -> _estado.value = EstadoDirecciones(cargando = false, direcciones = resultado.datos)
            is Resultado.Error -> _estado.value = EstadoDirecciones(cargando = false, error = resultado.mensaje)
        }
    }

    fun eliminar(idDireccion: Long) {
        eliminarDireccionCasoUso.ejecutar(idDireccion)
        cargar()
    }

    fun marcarPredeterminada(idDireccion: Long) {
        val idUsuario = idUsuarioActual() ?: return
        marcarDireccionPredeterminadaCasoUso.ejecutar(idUsuario, idDireccion)
        cargar()
    }
}
