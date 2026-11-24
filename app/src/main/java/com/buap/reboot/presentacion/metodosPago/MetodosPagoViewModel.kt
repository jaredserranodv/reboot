package com.buap.reboot.presentacion.metodosPago

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.buap.reboot.dominio.casosUso.EliminarMetodoPagoCasoUso
import com.buap.reboot.dominio.casosUso.ObtenerMetodosPagoCasoUso
import com.buap.reboot.dominio.casosUso.ObtenerSesionActualCasoUso
import com.buap.reboot.nucleo.Resultado

class MetodosPagoViewModel(
    private val obtenerMetodosPagoCasoUso: ObtenerMetodosPagoCasoUso,
    private val eliminarMetodoPagoCasoUso: EliminarMetodoPagoCasoUso,
    private val obtenerSesionActualCasoUso: ObtenerSesionActualCasoUso
) : ViewModel() {

    private val _estado = MutableLiveData(EstadoMetodosPago())
    val estado: LiveData<EstadoMetodosPago> = _estado

    fun cargar() {
        val idUsuario = obtenerSesionActualCasoUso.ejecutar()?.id
        if (idUsuario == null) {
            _estado.value = EstadoMetodosPago(cargando = false, error = "No hay sesion activa")
            return
        }
        when (val resultado = obtenerMetodosPagoCasoUso.ejecutar(idUsuario)) {
            is Resultado.Exito -> _estado.value = EstadoMetodosPago(cargando = false, metodosPago = resultado.datos)
            is Resultado.Error -> _estado.value = EstadoMetodosPago(cargando = false, error = resultado.mensaje)
        }
    }

    fun eliminar(idMetodoPago: Long) {
        eliminarMetodoPagoCasoUso.ejecutar(idMetodoPago)
        cargar()
    }
}
