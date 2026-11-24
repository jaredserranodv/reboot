package com.buap.reboot.presentacion.perfil

import com.buap.reboot.dominio.modelo.PerfilUsuario

data class EstadoPerfil(
    val cargando: Boolean = true,
    val perfil: PerfilUsuario? = null,
    val error: String? = null,
    val sesionCerrada: Boolean = false
)
