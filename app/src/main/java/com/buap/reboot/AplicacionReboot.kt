package com.buap.reboot

import android.app.Application

class AplicacionReboot : Application() {
    val inyector: Inyector by lazy { Inyector(this) }
}
