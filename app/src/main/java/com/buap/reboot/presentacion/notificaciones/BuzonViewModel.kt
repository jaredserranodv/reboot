package com.buap.reboot.presentacion.notificaciones

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.buap.reboot.dominio.casosUso.MarcarNotificacionLeidaCasoUso
import com.buap.reboot.dominio.casosUso.ObtenerNotificacionesCasoUso
import com.buap.reboot.dominio.casosUso.ObtenerSesionActualCasoUso
import com.buap.reboot.nucleo.Resultado

class BuzonViewModel(
    private val obtenerNotificacionesCasoUso: ObtenerNotificacionesCasoUso,
    private val marcarNotificacionLeidaCasoUso: MarcarNotificacionLeidaCasoUso,
    private val obtenerSesionActualCasoUso: ObtenerSesionActualCasoUso
) : ViewModel() {

    private val _estado = MutableLiveData(EstadoBuzon())
    val estado: LiveData<EstadoBuzon> = _estado

    fun cargar() {
        val idUsuario = obtenerSesionActualCasoUso.ejecutar()?.id
        if (idUsuario == null) {
            _estado.value = EstadoBuzon(cargando = false, error = "No hay sesion activa")
            return
        }
        when (val resultado = obtenerNotificacionesCasoUso.ejecutar(idUsuario)) {
            is Resultado.Exito -> _estado.value = EstadoBuzon(cargando = false, notificaciones = resultado.datos)
            is Resultado.Error -> _estado.value = EstadoBuzon(cargando = false, error = resultado.mensaje)
        }
    }

    fun marcarComoLeida(idNotificacion: Long) {
        marcarNotificacionLeidaCasoUso.ejecutar(idNotificacion)
        cargar()
    }
}
