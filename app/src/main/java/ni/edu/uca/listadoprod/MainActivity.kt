package ni.edu.uca.listadoprod

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dataadapter.ProductoAdapter
import dataclass.Producto
import ni.edu.uca.listadoprod.databinding.ActivityMainBinding
import ni.edu.uca.listadoprod.databinding.ItemlistaBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    var listaProd = ArrayList<Producto>()
    private var idAct = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        val prod = Producto(0,"prueba",123.3)
        listaProd.add(prod)
        iniciar()
    }

    private fun iniciar() {
        binding.btnAgregar.setOnClickListener {
            agregarProd()
        }
        binding.btnLimpiar.setOnClickListener {
            limpiar()
        }
    }

    private fun limpiar(){
        with(binding){
            etNombreProd.setText("")
            etPrecio.setText("")
            etID.requestFocus()
        }
    }

    private fun editarProd(){

    }

    private fun toggleEdit(id : String){

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
            rvcLista.adapter = ProductoAdapter(listaProd)

            limpiar()
            idAct++
            etID.setText(idAct.toString())
        }
    }
}