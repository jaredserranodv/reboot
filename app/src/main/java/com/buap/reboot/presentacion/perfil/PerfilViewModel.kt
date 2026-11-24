package com.buap.reboot.presentacion.perfil

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.buap.reboot.dominio.casosUso.CerrarSesionCasoUso
import com.buap.reboot.dominio.casosUso.ObtenerPerfilCasoUso
import com.buap.reboot.dominio.casosUso.ObtenerSesionActualCasoUso
import com.buap.reboot.nucleo.Resultado

class PerfilViewModel(
    private val obtenerPerfilCasoUso: ObtenerPerfilCasoUso,
    private val obtenerSesionActualCasoUso: ObtenerSesionActualCasoUso,
    private val cerrarSesionCasoUso: CerrarSesionCasoUso
) : ViewModel() {

    private val _estado = MutableLiveData(EstadoPerfil())
    val estado: LiveData<EstadoPerfil> = _estado

    fun cargar() {
        val idUsuario = obtenerSesionActualCasoUso.ejecutar()?.id
        if (idUsuario == null) {
            _estado.value = EstadoPerfil(cargando = false, error = "No hay sesion activa")
            return
        }
        when (val resultado = obtenerPerfilCasoUso.ejecutar(idUsuario)) {
            is Resultado.Exito -> _estado.value = EstadoPerfil(cargando = false, perfil = resultado.datos)
            is Resultado.Error -> _estado.value = EstadoPerfil(cargando = false, error = resultado.mensaje)
        }
    }

    fun cerrarSesion() {
        cerrarSesionCasoUso.ejecutar()
        _estado.value = (_estado.value ?: EstadoPerfil()).copy(sesionCerrada = true)
    }
}
