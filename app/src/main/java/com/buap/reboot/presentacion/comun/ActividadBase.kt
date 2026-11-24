package com.buap.reboot.presentacion.comun

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.buap.reboot.R
import com.google.android.material.appbar.MaterialToolbar

/**
 * Actividad base para gestionar el comportamiento global de las actividades,
 * incluyendo el modo edge-to-edge y la aplicación segura de insets para barras de estado.
 */
abstract class ActividadBase : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    override fun setContentView(view: View?) {
        super.setContentView(view)
        aplicarInsetsSeguros()
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        aplicarInsetsSeguros()
    }

    protected open fun aplicarInsetsSeguros() {
        val toolbar = findViewById<MaterialToolbar?>(R.id.toolbar)
        if (toolbar != null) {
            WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = false
            val paddingSuperiorOriginal = toolbar.paddingTop
            ViewCompat.setOnApplyWindowInsetsListener(toolbar) { v, insets ->
                val statusBarTop = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
                v.updatePadding(top = paddingSuperiorOriginal + statusBarTop)
                insets
            }
            ViewCompat.requestApplyInsets(toolbar)
        }
    }
}
