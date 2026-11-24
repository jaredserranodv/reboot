package com.buap.reboot.presentacion.editarPerfil

import com.buap.reboot.dominio.modelo.Usuario

data class EstadoEditarPerfil(
    val usuario: Usuario? = null,
    val cargando: Boolean = false,
    val error: String? = null,
    val guardadoExitosamente: Boolean = false
)
