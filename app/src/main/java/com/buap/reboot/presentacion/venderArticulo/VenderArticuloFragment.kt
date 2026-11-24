package com.buap.reboot.presentacion.venderArticulo

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.buap.reboot.R
import com.buap.reboot.databinding.FragmentVenderArticuloBinding
import com.buap.reboot.dominio.modelo.EstadoArticulo
import com.buap.reboot.presentacion.comun.FabricaViewModel
import com.buap.reboot.presentacion.comun.FragmentBase
import com.buap.reboot.presentacion.comun.tomarPermisoPersistente

// TODO: la tabla articulos solo soporta una ruta_imagen; se usa la primera foto seleccionada
class VenderArticuloFragment :
    FragmentBase<FragmentVenderArticuloBinding>(FragmentVenderArticuloBinding::inflate) {

    private val viewModel: VenderArticuloViewModel by viewModels {
        FabricaViewModel { VenderArticuloViewModel(inyector.publicarArticuloCasoUso, inyector.obtenerSesionActualCasoUso) }
    }

    private val fotosSeleccionadas = arrayOfNulls<Uri>(3)
    private var slotActivo = 0

    private val seleccionarImagen = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            requireContext().tomarPermisoPersistente(uri)
            fotosSeleccionadas[slotActivo] = uri
            vistaDeFoto(slotActivo).setImageURI(uri)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listOf(binding.foto1, binding.foto2, binding.foto3).forEachIndexed { indice, vista ->
            vista.setOnClickListener {
                slotActivo = indice
                seleccionarImagen.launch("image/*")
            }
        }

        binding.campoCategoria.setAdapter(
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, resources.getStringArray(R.array.categorias_populares))
        )
        binding.campoEstado.setAdapter(
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, resources.getStringArray(R.array.estados_articulo))
        )

        binding.botonPublicar.setOnClickListener {
            viewModel.publicar(
                titulo = binding.campoTitulo.text?.toString().orEmpty(),
                descripcion = binding.campoDescripcion.text?.toString().orEmpty(),
                categoria = binding.campoCategoria.text.toString(),
                precioTexto = binding.campoPrecio.text?.toString().orEmpty(),
                estadoArticulo = EstadoArticulo.desdeEtiqueta(binding.campoEstado.text.toString()),
                ciudad = binding.campoCiudad.text?.toString().orEmpty(),
                rutaImagen = fotosSeleccionadas.firstOrNull { it != null }?.toString()
            )
        }

        viewModel.estado.observe(viewLifecycleOwner) { estado ->
            binding.indicadorCarga.visibility = if (estado.cargando) View.VISIBLE else View.GONE
            binding.botonPublicar.isEnabled = !estado.cargando

            estado.error?.let { Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show() }

            if (estado.publicadoExitosamente) {
                Toast.makeText(requireContext(), "Articulo publicado", Toast.LENGTH_SHORT).show()
                val actividadPrincipal = activity as? com.buap.reboot.MainActivity
                if (actividadPrincipal != null) {
                    actividadPrincipal.seleccionarPestana(R.id.buscarFragment)
                } else {
                    findNavController().navigate(R.id.buscarFragment)
                }
            }
        }
    }

    private fun vistaDeFoto(indice: Int): ImageView = when (indice) {
        0 -> binding.foto1
        1 -> binding.foto2
        else -> binding.foto3
    }
}
