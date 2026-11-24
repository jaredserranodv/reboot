package com.buap.reboot.dominio.modelo

enum class EstadoArticulo(val etiqueta: String) {
    NUEVO("nuevo"),
    SEMINUEVO("seminuevo"),
    USADO("usado");

    companion object {
        fun desdeEtiqueta(etiqueta: String): EstadoArticulo =
            entries.firstOrNull { it.etiqueta == etiqueta } ?: USADO
    }
}
