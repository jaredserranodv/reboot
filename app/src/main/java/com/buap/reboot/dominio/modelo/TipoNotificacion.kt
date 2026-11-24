package com.buap.reboot.dominio.modelo

enum class TipoNotificacion(val etiqueta: String) {
    MENSAJE("mensaje"),
    OFERTA("oferta"),
    PAGO_CONFIRMADO("pago_confirmado"),
    RECORDATORIO("recordatorio");

    companion object {
        fun desdeEtiqueta(etiqueta: String): TipoNotificacion =
            entries.firstOrNull { it.etiqueta == etiqueta } ?: MENSAJE
    }
}
