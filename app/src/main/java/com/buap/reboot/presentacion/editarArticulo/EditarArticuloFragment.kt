package com.buap.reboot.presentacion.editarArticulo

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.buap.reboot.R
import com.buap.reboot.databinding.FragmentEditarArticuloBinding
import com.buap.reboot.dominio.modelo.Articulo
import com.buap.reboot.dominio.modelo.EstadoArticulo
import com.buap.reboot.presentacion.comun.FabricaViewModel
import com.buap.reboot.presentacion.comun.FragmentBase
import com.buap.reboot.presentacion.comun.cargarFotoArticulo
import com.buap.reboot.presentacion.comun.tomarPermisoPersistente

class EditarArticuloFragment :
    FragmentBase<FragmentEditarArticuloBinding>(FragmentEditarArticuloBinding::inflate) {

    private val viewModel: EditarArticuloViewModel by viewModels {
        FabricaViewModel {
            EditarArticuloViewModel(
                inyector.obtenerArticuloPorIdCasoUso,
                inyector.editarArticuloCasoUso,
                inyector.eliminarArticuloCasoUso
            )
        }
    }

    private val idArticulo: Long get() = arguments?.getLong("idArticulo") ?: 0L
    private var fotoSeleccionada: Uri? = null

    private val seleccionarImagen = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            requireContext().tomarPermisoPersistente(uri)
            fotoSeleccionada = uri
            binding.foto1.setImageURI(uri)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.foto1.setOnClickListener { seleccionarImagen.launch("image/*") }

        binding.campoCategoria.setAdapter(
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, resources.getStringArray(R.array.categorias_populares))
        )
        binding.campoEstado.setAdapter(
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, resources.getStringArray(R.array.estados_articulo))
        )

        binding.botonGuardar.setOnClickListener {
            viewModel.guardar(
                idArticulo = idArticulo,
                titulo = binding.campoTitulo.text?.toString().orEmpty(),
                descripcion = binding.campoDescripcion.text?.toString().orEmpty(),
                categoria = binding.campoCategoria.text.toString(),
                precioTexto = binding.campoPrecio.text?.toString().orEmpty(),
                estadoArticulo = EstadoArticulo.desdeEtiqueta(binding.campoEstado.text.toString()),
                ciudad = binding.campoCiudad.text?.toString().orEmpty(),
                rutaImagen = fotoSeleccionada?.toString()
            )
        }

        binding.botonEliminar.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setMessage(R.string.editar_articulo_confirmar_eliminar)
                .setPositiveButton(R.string.editar_articulo_boton_eliminar) { _, _ -> viewModel.eliminar(idArticulo) }
                .setNegativeButton(R.string.accion_cancelar, null)
                .show()
        }

        viewModel.estado.observe(viewLifecycleOwner) { estado ->
            binding.indicadorCarga.visibility = if (estado.cargando) View.VISIBLE else View.GONE
            binding.botonGuardar.isEnabled = !estado.cargando

            estado.articulo?.let { precargarCampos(it) }
            estado.error?.let { Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show() }

            if (estado.guardadoExitosamente) {
                Toast.makeText(requireContext(), "Articulo actualizado", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
            if (estado.eliminadoExitosamente) {
                Toast.makeText(requireContext(), R.string.editar_articulo_eliminada, Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }

        viewModel.cargar(idArticulo)
    }

    private fun precargarCampos(articulo: Articulo) {
        if (binding.campoTitulo.text.isNullOrEmpty()) binding.campoTitulo.setText(articulo.titulo)
        if (binding.campoDescripcion.text.isNullOrEmpty()) binding.campoDescripcion.setText(articulo.descripcion)
        if (binding.campoCategoria.text.isNullOrEmpty()) binding.campoCategoria.setText(articulo.categoria, false)
        if (binding.campoPrecio.text.isNullOrEmpty()) binding.campoPrecio.setText(articulo.precio.toString())
        if (binding.campoEstado.text.isNullOrEmpty()) binding.campoEstado.setText(articulo.estadoArticulo.etiqueta, false)
        if (binding.campoCiudad.text.isNullOrEmpty()) binding.campoCiudad.setText(articulo.ciudad)
        if (fotoSeleccionada == null) binding.foto1.cargarFotoArticulo(articulo.rutaImagen)
    }
}
