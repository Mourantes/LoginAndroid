package com.example.login

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.login.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import android.app.Instrumentation.ActivityResult as ActivityResult1

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        val view = binding.root

        setContentView(view)

        auth = Firebase.auth
        //val text_botao_logar_google = binding.botaoGoogle.getChildAt(0) as TextView
        //text_botao_logar_google.text = "Fazer login com o Google"

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("695358313358-7ocsp28mp6hjglnbgh6gn1dabnptu7d6.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.botaoEntrar.setOnClickListener{

            if (TextUtils.isEmpty(binding.editTextUsuario.text)){
                binding.editTextUsuario.error ="Campo usuário não pode estar em branco"
            } else if (TextUtils.isEmpty(binding.editTextSenha.text)){
                binding.editTextSenha.error ="Campo senha não pode estar em branco"
            } else {
                loginUsuarioESenha(binding.editTextUsuario.text.toString(),
                    binding.editTextSenha.text.toString())
            }

        }

        binding.botaoGoogle.setOnClickListener {
            signIn()
        }

    }

    private fun signIn() {
        val intent = googleSignInClient.signInIntent
        abreActivity.launch(intent)
    }

    var abreActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        result: ActivityResult ->

        if (result.resultCode == RESULT_OK){
            val intent = result.data
            val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
            try {
                val conta = task.getResult(ApiException::class.java)
                loginComGoogle(conta.idToken!!)
            } catch (exception: ApiException) {

            }
        }
    }

    private fun loginComGoogle(token: String) {
        val credencial =GoogleAuthProvider.getCredential(token, null)
        auth.signInWithCredential(credencial).addOnCompleteListener(this){
            task: Task<AuthResult> ->
            if(task.isSuccessful) {
                Toast.makeText(baseContext, "Autenticação Efetuada com o Google.",
                    Toast.LENGTH_SHORT).show()
                abrePrincipal()
            } else {
                Toast.makeText(baseContext, "Erro de autenticação com o Google.",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loginUsuarioESenha(usuario: String, senha: String) {
        auth.signInWithEmailAndPassword(
            usuario, senha
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(baseContext, "Autenticação Efetuada.", Toast.LENGTH_SHORT).show()
                    val user = auth.currentUser
                    abrePrincipal()
                    //updateUI(user)
                } else {
                    Toast.makeText(baseContext, "Erro de autenticação.", Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                }
            }
    }

    fun abrePrincipal() {

        binding.editTextUsuario.text.clear()
        binding.editTextSenha.text.clear()

        val intent = Intent(this, PrincipalActivity::class.java)
        startActivity(intent)

        finish()
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser

        if (currentUser != null) {
            if (currentUser.email?.isNotEmpty() == true) {
                Toast.makeText(baseContext, "Usuário " + currentUser.email + " logado.",
                    Toast.LENGTH_SHORT).show()
                abrePrincipal()
            }
        }
        //updateUI(currentUser)
    }
}