package com.buap.reboot.presentacion.misCompras

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.buap.reboot.dominio.casosUso.ObtenerMisComprasCasoUso
import com.buap.reboot.dominio.casosUso.ObtenerSesionActualCasoUso
import com.buap.reboot.nucleo.Resultado

class MisComprasViewModel(
    private val obtenerMisComprasCasoUso: ObtenerMisComprasCasoUso,
    private val obtenerSesionActualCasoUso: ObtenerSesionActualCasoUso
) : ViewModel() {

    private val _estado = MutableLiveData(EstadoMisCompras())
    val estado: LiveData<EstadoMisCompras> = _estado

    fun cargar() {
        val idUsuario = obtenerSesionActualCasoUso.ejecutar()?.id
        if (idUsuario == null) {
            _estado.value = EstadoMisCompras(cargando = false, error = "No hay sesion activa")
            return
        }
        when (val resultado = obtenerMisComprasCasoUso.ejecutar(idUsuario)) {
            is Resultado.Exito -> _estado.value = (_estado.value ?: EstadoMisCompras())
                .copy(cargando = false, compras = resultado.datos)
            is Resultado.Error -> _estado.value = EstadoMisCompras(cargando = false, error = resultado.mensaje)
        }
    }

    fun seleccionarPestana(soloEntregadas: Boolean) {
        _estado.value = (_estado.value ?: EstadoMisCompras()).copy(soloEntregadas = soloEntregadas)
    }
}
