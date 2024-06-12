package com.example.trabalho32

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.trabalho32.databinding.ActivityMainFuncBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivityFunc : AppCompatActivity() {

    private lateinit var binding: ActivityMainFuncBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainFuncBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()


        binding.bottomNavigationView2.setOnItemSelectedListener {

            when(it.itemId){

                R.id.gerenObras -> replaceFragment(GerenObras())
                R.id.gerenVisitas -> replaceFragment(GerenVisitas())
                R.id.voltar -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }

                else -> {

                }
            }

            true



        }
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.frame_layout2, GerenObras::class.java, null)
            .commit()

    }


    private fun replaceFragment(fragment: Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout2,fragment)
        fragmentTransaction.commit()


    }
}
