package com.buap.reboot.presentacion.comun

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<com.google.android.material.appbar.MaterialToolbar?>(com.buap.reboot.R.id.toolbar)?.let { toolbar ->
            WindowCompat.getInsetsController(requireActivity().window, requireActivity().window.decorView).isAppearanceLightStatusBars = false
            val paddingSuperiorOriginal = toolbar.paddingTop
            ViewCompat.setOnApplyWindowInsetsListener(toolbar) { v, insets ->
                val statusBarTop = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
                v.updatePadding(top = paddingSuperiorOriginal + statusBarTop)
                insets
            }
            ViewCompat.requestApplyInsets(toolbar)

            if (toolbar.navigationIcon != null) {
                toolbar.setNavigationOnClickListener {
                    findNavController().navigateUp()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
