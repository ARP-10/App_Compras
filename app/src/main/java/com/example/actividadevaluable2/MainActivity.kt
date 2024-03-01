package com.example.actividadevaluable2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView

import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.actividadevaluable2.adapter.ProductoAdapter
import com.example.actividadevaluable2.databinding.ActivityMainBinding
import com.example.actividadevaluable2.model.Producto
import com.google.android.material.snackbar.Snackbar
import org.json.JSONArray

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var productoAdapter: ProductoAdapter
    private lateinit var listaProductos: ArrayList<Producto>
    private lateinit var carrito: ArrayList<Producto>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)


        setSupportActionBar(binding.toolbar)

        listaProductos = ArrayList()
        carrito = ArrayList()
        rellenarLista()

        setContentView(binding.root)

        productoAdapter = ProductoAdapter(this, binding.btnCarrito,listaProductos,carrito)
        binding.recyclerProductos.adapter = productoAdapter
        binding.recyclerProductos.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL,false)

        binding.spinnerCategorias.onItemSelectedListener = object : OnItemSelectedListener, AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ){
                val seleccionado = parent!!.adapter.getItem(position).toString()
                productoAdapter.filtrarLista(seleccionado)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                TODO("Not yet implemented")
            }


        }
        binding.btnCarrito.setOnClickListener {
            carrito = productoAdapter.devolverCarro()
            val intent = Intent(applicationContext, SecondActivity::class.java)
            intent.putExtra("carrito",carrito)
            startActivity(intent)

        }
    }

    // Sobreescribimos el método para que aporezca el menú en nuestra app
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Indicamos que menu queremos poner y donde
        menuInflater.inflate(R.menu.main_menu, menu)
        // Le decimos true para que se muestre
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Evaluamos si se pulsa un item u otro
        when(item.itemId) {
            R.id.menu_carrito -> {
                val intent = Intent(this, SecondActivity::class.java)
                startActivity(intent)
            }
        }
        return true
    }

    fun rellenarLista(){
        val peticion : JsonObjectRequest =
            JsonObjectRequest(Request.Method.GET,"https://dummyjson.com/products",null, {
                val products: JSONArray = it.getJSONArray("products")
                for(i in 0 until products.length()){
                    val product = products.getJSONObject(i)
                    productoAdapter.addProducto(
                        Producto(product.getString("title"),
                            product.getInt("price"),
                            product.getString("thumbnail"),
                            product.getString("category"))
                    )
                }


            },{
                Snackbar.make(binding.root, "Error en la conexión", Snackbar.LENGTH_SHORT).show()
            })
        Volley.newRequestQueue(this).add(peticion)
    }




}