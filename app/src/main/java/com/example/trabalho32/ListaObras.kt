package com.example.trabalho32

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trabalho32.adapter.ObrasAdapter
import com.example.trabalho32.databinding.FragmentListaObrasBinding
import com.example.trabalho32.model.Obras
import com.google.firebase.firestore.FirebaseFirestore

class ListaObras : Fragment() {

    private lateinit var binding: FragmentListaObrasBinding
    private lateinit var obrasAdapter: ObrasAdapter
    private var listaObras = ArrayList<Obras>()
    private lateinit var filteredVisitList: ArrayList<Obras>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListaObrasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cadastro = arguments?.getString("cadastro")
        binding.txtNomeUsuario.text = "Bem-vindo(a)"

        binding.recyclerViewObras.layoutManager = GridLayoutManager(requireContext(), 2)
        obrasAdapter = ObrasAdapter(listaObras) { obra ->
            onObraClick(obra)
        }
        binding.recyclerViewObras.setHasFixedSize(true)
        binding.recyclerViewObras.adapter = obrasAdapter

        binding.btvoltar.setOnClickListener {
            val fragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout, Inicioo())
            fragmentTransaction.commit()
        }

        setupSearchView()
        getVisitasData()
    }

    private fun setupSearchView() {
        binding.pesquisa.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filteredVisitList = filterObras(newText)
                obrasAdapter.updateList(filteredVisitList)
                return true
            }
        })
    }

    private fun getVisitasData() {
        val db = FirebaseFirestore.getInstance()
        val visitasRef = db.collection("Obras2")

        visitasRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                listaObras.clear()
                for (document in task.result!!) {
                    val visitas = document.toObject(Obras::class.java)
                    listaObras.add(visitas)
                }
                filteredVisitList = ArrayList(listaObras)
                obrasAdapter.updateList(filteredVisitList)
            } else {
                Log.w("Visitas", "Error getting data", task.exception)
            }
        }
    }

    private fun onObraClick(obra: Obras) {
        val descricaoPintFragment = DescricaoPint.newInstance(obra)
        parentFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, descricaoPintFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun filterObras(query: String?): ArrayList<Obras> {
        val filteredList = ArrayList<Obras>()
        for (obra in listaObras) {
            if (obra.nome!!.contains(query!!, ignoreCase = true)) {
                filteredList.add(obra)
            }
        }
        return filteredList
    }
}
