package br.com.challenge01

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import br.com.challenge01.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var apiService : ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
//            .baseUrl("http://localhost:8080/")
            .baseUrl("http://25.64.203.9:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

        binding.btnCadastrarse.setOnClickListener{
            startActivity(Intent(this, CadastroActivity::class.java))
        }

        binding.btnEntrar.setOnClickListener {
            verificarLogin()
        }
    }

    private fun verificarLogin(){
        val usuario = binding.editTextCnpj.text.toString()
        val senha = binding.editTextTextPassword.text.toString()
        val loginRequest = LoginRequest(usuario, senha)

        apiService.login(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>){
                if (response.isSuccessful){
                    response.body()?.let{ i ->
                        val clinicaLogada = Clinica(
                            i.id,
                            i.nome,
                            i.telefone,
                            i.email,
                            i.cnpj)

                        saveClinicaData(clinicaLogada)
                        startActivity(Intent(this@MainActivity, PaginaInicialActivity::class.java))
                    }
                } else {
                    Log.e("Login", "Erro na resposta: ${response.errorBody()?.string()}")
                    binding.errorTextView.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable){
                binding.errorTextView.visibility = View.VISIBLE
                Log.e("Login", "onFailure: ${t.message}", t)
            }
        })
    }
}