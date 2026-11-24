package com.buap.reboot.presentacion.comun

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.buap.reboot.AplicacionReboot
import com.buap.reboot.Inyector

/** Evita repetir el manejo de ciclo de vida del binding en cada Fragment de la app. */
abstract class FragmentBase<B : ViewBinding>(
    private val enlazar: (LayoutInflater, ViewGroup?, Boolean) -> B
) : Fragment() {

    private var _binding: B? = null
    protected val binding: B get() = _binding!!

    protected val inyector: Inyector get() = (requireActivity().application as AplicacionReboot).inyector

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = enlazar(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
