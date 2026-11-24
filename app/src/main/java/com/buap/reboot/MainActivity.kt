package com.buap.reboot

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.NavHostFragment
import com.buap.reboot.databinding.ActivityMainBinding
import com.buap.reboot.presentacion.comun.ActividadBase

class MainActivity : ActividadBase() {

    private lateinit var binding: ActivityMainBinding
    private var idTabActivo: Int = R.id.inicioFragment

    private val navHostsPorPestana by lazy {
        mapOf(
            R.id.inicioFragment to binding.navHostInicio.id,
            R.id.buscarFragment to binding.navHostBuscar.id,
            R.id.venderArticuloFragment to binding.navHostVender.id,
            R.id.buzonFragment to binding.navHostBuzon.id,
            R.id.perfilFragment to binding.navHostPerfil.id
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tabInicial = savedInstanceState?.getInt(ESTADO_TAB_ACTIVO) ?: R.id.inicioFragment
        idTabActivo = tabInicial
        sincronizarVisibilidadTabs(tabInicial)
        binding.navegacionInferior.selectedItemId = tabInicial

        binding.navegacionInferior.setOnItemSelectedListener { item ->
            cambiarPestana(item.itemId)
            true
        }

        binding.navegacionInferior.setOnItemReselectedListener { item ->
            val hostId = navHostsPorPestana[item.itemId] ?: return@setOnItemReselectedListener
            val navHost = supportFragmentManager.findFragmentById(hostId) as? NavHostFragment
            navHost?.navController?.let { controller ->
                controller.popBackStack(controller.graph.startDestinationId, false)
            }
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val hostId = navHostsPorPestana[idTabActivo]
                val navHost = if (hostId != null) supportFragmentManager.findFragmentById(hostId) as? NavHostFragment else null
                val controller = navHost?.navController

                if (controller != null && controller.navigateUp()) {
                    return
                }

                if (idTabActivo != R.id.inicioFragment) {
                    seleccionarPestana(R.id.inicioFragment)
                    return
                }

                isEnabled = false
                onBackPressedDispatcher.onBackPressed()
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(ESTADO_TAB_ACTIVO, idTabActivo)
    }

    /**
     * Cambia de pestana de forma programatica (publicar un articulo, boton atras), no desde
     * un tap del usuario en el propio BottomNavigationView.
     *
     * Importante: nunca asignar `selectedItemId` aqui. Esa asignacion dispara
     * `setOnItemSelectedListener` igual que un tap real (pasa por `performItemAction`), lo que
     * reentra a este mismo metodo y produce un StackOverflowError. `MenuItem.isChecked` en cambio
     * solo actualiza el marcado visual (via `MenuBuilder.onItemsChanged`) sin volver a invocar
     * el listener de seleccion.
     */
    fun seleccionarPestana(idPestana: Int) {
        if (!navHostsPorPestana.containsKey(idPestana)) return
        binding.navegacionInferior.menu.findItem(idPestana)?.isChecked = true
        cambiarPestana(idPestana)
    }

    private fun cambiarPestana(idPestana: Int) {
        if (!navHostsPorPestana.containsKey(idPestana)) return
        if (idTabActivo != idPestana) {
            idTabActivo = idPestana
            sincronizarVisibilidadTabs(idPestana)
        }
    }

    private fun sincronizarVisibilidadTabs(idSeleccionado: Int) {
        val transaccion = supportFragmentManager.beginTransaction()
        navHostsPorPestana.forEach { (idPestana, containerId) ->
            val navHost = supportFragmentManager.findFragmentById(containerId)
            if (navHost != null) {
                if (idPestana == idSeleccionado) {
                    transaccion.show(navHost)
                } else {
                    transaccion.hide(navHost)
                }
            }
        }
        transaccion.commit()
    }

    companion object {
        private const val ESTADO_TAB_ACTIVO = "estado_tab_activo"
    }
}
