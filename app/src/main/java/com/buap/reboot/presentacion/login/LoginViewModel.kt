package com.buap.reboot.presentacion.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.buap.reboot.dominio.casosUso.IniciarSesionCasoUso
import com.buap.reboot.nucleo.Resultado

class LoginViewModel(
    private val iniciarSesionCasoUso: IniciarSesionCasoUso
) : ViewModel() {

    private val _estado = MutableLiveData(EstadoLogin())
    val estado: LiveData<EstadoLogin> = _estado

    fun iniciarSesion(correo: String, contrasena: String) {
        _estado.value = EstadoLogin(cargando = true)
        when (val resultado = iniciarSesionCasoUso.ejecutar(correo, contrasena)) {
            is Resultado.Exito -> _estado.value = EstadoLogin(sesionIniciada = true)
            is Resultado.Error -> _estado.value = EstadoLogin(error = resultado.mensaje)
        }
    }
}
