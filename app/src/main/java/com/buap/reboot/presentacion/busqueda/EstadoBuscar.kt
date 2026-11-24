package com.buap.reboot.presentacion.busqueda

// TODO: las busquedas recientes no estan definidas en el esquema de BD; se mantienen solo en memoria
data class EstadoBuscar(
    val busquedasRecientes: List<String> = emptyList()
)
