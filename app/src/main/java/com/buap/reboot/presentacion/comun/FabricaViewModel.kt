package com.buap.reboot.presentacion.comun

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/** Evita una clase Factory distinta por pantalla: recibe el constructor del ViewModel como lambda. */
class FabricaViewModel<T : ViewModel>(private val crear: () -> T) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <U : ViewModel> create(modelClass: Class<U>): U = crear() as U
}
