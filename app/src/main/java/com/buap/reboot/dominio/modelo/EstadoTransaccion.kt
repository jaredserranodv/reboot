package com.buap.reboot.dominio.modelo

enum class EstadoTransaccion(val etiqueta: String) {
    PENDIENTE("pendiente"),
    CONFIRMADA("confirmada"),
    // TODO: no existe aun un flujo que transicione una compra a entregada (sin simulacion de envio)
    ENTREGADA("entregada");

    companion object {
        fun desdeEtiqueta(etiqueta: String): EstadoTransaccion =
            entries.firstOrNull { it.etiqueta == etiqueta } ?: PENDIENTE
    }
}
