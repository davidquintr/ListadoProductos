package ni.edu.uca.listadoprod

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dataadapter.ProductoAdapter
import dataclass.Producto
import ni.edu.uca.listadoprod.databinding.ActivityMainBinding
import ni.edu.uca.listadoprod.databinding.ItemlistaBinding

class MainActivity : AppCompatActivity(){

    private lateinit var binding : ActivityMainBinding
    var listaProd = ArrayList<Producto>()
    private var idAct = 0;
    private var editMode = false
    private var editedElement : Producto = Producto(-1,"",0.00)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.etID.setText(idAct.toString())
        iniciar()
    }

    private fun iniciar() {
        binding.btnAgregar.setOnClickListener {
            if(!editMode)
                agregarProd()
            else
                terminarEdicion()

        }
        binding.btnLimpiar.setOnClickListener {
            limpiar()
        }

    }

    private fun terminarEdicion(){
        try {
        with(binding){
                var itemSel = listaProd.indexOf(editedElement)
                editedElement = Producto(editedElement.id,etNombreProd.text.toString(),etPrecio.text.toString().toDouble())
                listaProd[itemSel] = editedElement
                rvcLista.adapter = ProductoAdapter(listaProd,
                {producto ->  onItemClick(producto)},
                {producto -> onDeleteItem(producto)})
                etID.setText(idAct.toString())
                btnAgregar.setText("Agregar")
            }
            editMode = false
            editedElement = Producto(-1,"",0.00)
        }catch (ex : Exception){
            Toast.makeText(this@MainActivity,"Error ${ex.toString()}",Toast.LENGTH_LONG).show()
        }

        limpiar()
    }

    private fun limpiar(){
        with(binding){
            etNombreProd.setText("")
            etPrecio.setText("")
            etID.requestFocus()
        }
    }

    private fun agregarProd(){
        with(binding){
            try {
                val id: Int = idAct;
                val nombre : String = etNombreProd.text.toString()
                val precio : Double = etPrecio.text.toString().toDouble()
                val prod = Producto(id,nombre,precio)
                listaProd.add(prod)
            }catch (ex : Exception){
                Toast.makeText(this@MainActivity,"Error ${ex.toString()}",Toast.LENGTH_LONG).show()
            }
            rvcLista.layoutManager = LinearLayoutManager(this@MainActivity)
            rvcLista.adapter = ProductoAdapter(listaProd,
                {producto ->  onItemClick(producto)},
                {producto -> onDeleteItem(producto)})

            limpiar()
            idAct++
            etID.setText(idAct.toString())
        }
    }

    private fun onDeleteItem(producto: Producto) {
        var alerta = AlertDialog.Builder(this)
        alerta.setMessage("¿Quieres eliminar este item?")
            .setCancelable(false)
            .setPositiveButton("Aceptar", DialogInterface.OnClickListener {
                    dialog, id -> eliminarItem(producto)
            })
            // negative button text and action
            .setNegativeButton("Cancelar", DialogInterface.OnClickListener {
                    dialog, id ->
            })
        val dialogo = alerta.create()
        dialogo.setTitle("Eliminar item")
        dialogo.show()
    }

    private fun eliminarItem(producto: Producto){
        try{
            listaProd.remove(producto)
            binding.rvcLista.adapter = ProductoAdapter(listaProd,
                {producto ->  onItemClick(producto)},
                {producto -> onDeleteItem(producto)})

        }catch (ex : Exception){
            Toast.makeText(this@MainActivity,"Error ${ex.toString()}",Toast.LENGTH_LONG).show()
        }
    }

    private fun onItemClick(producto: Producto) {
        try {
            with(binding){
                etID.setText(producto.id.toString())
                etNombreProd.setText(producto.nombre.toString())
                etPrecio.setText(producto.precio.toString())
                btnAgregar.setText("Finalizar")
                editMode = true
                editedElement = producto
            }
        }catch (ex : Exception){
            Toast.makeText(this@MainActivity,"Error ${ex.toString()}",Toast.LENGTH_LONG).show()
        }
    }
}