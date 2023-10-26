package com.example.login

import android.content.ContentValues.TAG
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

                auth.signInWithEmailAndPassword(
                    binding.editTextUsuario.text.toString(),
                    binding.editTextSenha.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCustomToken:success")
                            val user = auth.currentUser
                            Toast.makeText(baseContext, "Autenticação efetuada.",
                                Toast.LENGTH_SHORT).show()
                            //updateUI(user)
                        } else {
                            // Mensagem de erro Sign In.
                            Log.w(TAG, "signInWithCustomToken:failure", task.exception)
                            Toast.makeText(baseContext, "Erro de autenticação.",
                                Toast.LENGTH_SHORT).show()
                            //updateUI(null)
                        }
                    }

        }

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        //val currentUser = auth.currentUser
        //updateUI(currentUser)
    }
}