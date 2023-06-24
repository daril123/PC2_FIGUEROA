package com.example.pc2_figueroa

import androidx.appcompat.app.AppCompatActivity

import android.content.Intent
import android.os.Bundle

import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {

    private lateinit var editTextDni: EditText
    private lateinit var editTextClave: EditText
    private lateinit var buttonIniciarSesion: Button
    private lateinit var   buttonRegistrar: Button

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editTextDni = findViewById(R.id.editTextDni)
        editTextClave = findViewById(R.id.editTextClave)
        buttonIniciarSesion = findViewById(R.id.buttonIniciarSesion)
        buttonRegistrar = findViewById(R.id.buttonCrearCuenta)

        buttonIniciarSesion.setOnClickListener {
            val dni = editTextDni.text.toString()
            val clave = editTextClave.text.toString()

            if (dni.isNotEmpty() && clave.isNotEmpty()) {
                iniciarSesion(dni, clave)
            } else {
                Toast.makeText(this, "Ingresee el DNI y la clave, por favor ", Toast.LENGTH_SHORT).show()
            }
        }
        buttonRegistrar.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }
    }






    private fun iniciarSesion(dni: String, clave: String) {
        db.collection("Users")
            .whereEqualTo("Dni", dni)
            .whereEqualTo("Clave", clave)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    // aqui si el usuario existe entonces muestro el toastyt
                    Toast.makeText(this, "Inicio de sesión exitosoo", Toast.LENGTH_SHORT).show()




                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish() //
                } else {
                   //si no encuentro el usuario
                    Toast.makeText(this, "DNI o clave invalidos, registrese para ingresar", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error al iniciar sesión: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
