package com.buap.reboot.data.repositorio

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

/**
 * Los casos de uso y ViewModels de presentacion son sincronos (no suspend), asi que las
 * operaciones de Room se despachan a IO y se esperan aqui para no cambiar esas firmas.
 */
fun <T> enIO(bloque: suspend () -> T): T = runBlocking(Dispatchers.IO) { bloque() }
