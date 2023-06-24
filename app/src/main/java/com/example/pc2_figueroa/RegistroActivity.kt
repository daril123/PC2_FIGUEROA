package com.example.pc2_figueroa

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*


class RegistroActivity : AppCompatActivity() {


    private lateinit var editTextDni: EditText
    private lateinit var editTextNombres: EditText
    private lateinit var editTextContraseña: EditText
    private lateinit var editTextConfirmarContraseña: EditText
    private lateinit var buttonRegistrar: Button

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)


        editTextDni = findViewById(R.id.editTextDni)
        editTextNombres = findViewById(R.id.editTextNombres)
        editTextContraseña = findViewById(R.id.editTextContraseña)
        editTextConfirmarContraseña = findViewById(R.id.editTextConfirmarContraseña)
        buttonRegistrar = findViewById(R.id.buttonRegistrar)

        buttonRegistrar.setOnClickListener {

            val dni = editTextDni.text.toString()
            val nombres = editTextNombres.text.toString()
            val contraseña = editTextContraseña.text.toString()
            val confirmarContraseña = editTextConfirmarContraseña.text.toString()

            if (contraseña == confirmarContraseña) {
                registrarUsuario(contraseña, dni, nombres, confirmarContraseña)
            } else {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registrarUsuario(clave: String, dni: String, nombres: String, contraseña: String) {
        val user = hashMapOf(
            "Clave" to clave,
            "Dni" to dni,
            "Nombres" to nombres
        )
        val uid = UUID.randomUUID().toString()
        db.collection("Users")
            .document(uid)
            .set(user)
            .addOnSuccessListener {
                Toast.makeText(this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show()

            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error al registrar usuario: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
