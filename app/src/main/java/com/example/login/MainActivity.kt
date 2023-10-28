package com.example.login

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.login.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var googleSignInCliente: GoogleSignInClient
    private lateinit var auth: FirebaseAuth;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        val view = binding.root

        setContentView(view)

        auth = Firebase.auth
        //val text_botao_logar_google = binding.botaoGoogle.getChildAt(0) as TextView
        //text_botao_logar_google.text = "Fazer login com o Google"

        binding.botaoEntrar.setOnClickListener{

            if (binding.editTextUsuario.text.isNullOrEmpty()){
                // Mensagem de erro Sign In.
                Toast.makeText(
                    baseContext, "Digite o usuário.",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (binding.editTextSenha.text.isNullOrEmpty()){
                // Mensagem de erro Sign In.
                Toast.makeText(
                    baseContext, "Digite a senha.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                loginUsuarioESenha(binding.editTextUsuario.text.toString(),
                    binding.editTextSenha.text.toString())
            }

        }

    }

    private fun loginUsuarioESenha(usuario: String, senha: String) {
        auth.signInWithEmailAndPassword(
            usuario, senha
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    abrePrincipal()
                    //updateUI(user)
                } else {

                    //updateUI(null)
                }
            }
    }

    fun abrePrincipal() {
        Toast.makeText(baseContext, "Autenticação efetuada.",
            Toast.LENGTH_SHORT).show()
        binding.editTextUsuario.text.clear()
        binding.editTextSenha.text.clear()
        val intent = Intent(this, PrincipalActivity::class.java)
        startActivity(intent)

        finish()
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        //val currentUser = auth.currentUser
        //updateUI(currentUser)
    }
}