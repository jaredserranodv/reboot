package com.buap.reboot.presentacion.misPublicaciones

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.buap.reboot.dominio.casosUso.CambiarDisponibilidadArticuloCasoUso
import com.buap.reboot.dominio.casosUso.ObtenerMisPublicacionesCasoUso
import com.buap.reboot.dominio.casosUso.ObtenerSesionActualCasoUso
import com.buap.reboot.nucleo.Resultado

class MisPublicacionesViewModel(
    private val obtenerMisPublicacionesCasoUso: ObtenerMisPublicacionesCasoUso,
    private val cambiarDisponibilidadArticuloCasoUso: CambiarDisponibilidadArticuloCasoUso,
    private val obtenerSesionActualCasoUso: ObtenerSesionActualCasoUso
) : ViewModel() {

    private val _estado = MutableLiveData(EstadoMisPublicaciones())
    val estado: LiveData<EstadoMisPublicaciones> = _estado

    fun cargar() {
        val idUsuario = obtenerSesionActualCasoUso.ejecutar()?.id
        if (idUsuario == null) {
            _estado.value = EstadoMisPublicaciones(cargando = false, error = "No hay sesion activa")
            return
        }
        when (val resultado = obtenerMisPublicacionesCasoUso.ejecutar(idUsuario)) {
            is Resultado.Exito -> _estado.value = EstadoMisPublicaciones(cargando = false, publicaciones = resultado.datos)
            is Resultado.Error -> _estado.value = EstadoMisPublicaciones(cargando = false, error = resultado.mensaje)
        }
    }

    fun reactivar(idArticulo: Long) {
        cambiarDisponibilidadArticuloCasoUso.ejecutar(idArticulo, disponible = true)
        cargar()
    }
}
