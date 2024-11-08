package br.com.challenge01

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import br.com.challenge01.databinding.ActivityCadastroEnderecoBinding
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.Response
import retrofit2.Call
import retrofit2.converter.gson.GsonConverterFactory

class CadastroEnderecoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCadastroEnderecoBinding
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCadastroEnderecoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.15.3:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

        val cadastro: Cadastro? = intent.getParcelableExtra("cadastro")

        binding.btnBack.setOnClickListener {
            startActivity(Intent(this, CadastroActivity::class.java))
        }

        binding.btnEnviarCadastro.setOnClickListener {
            cadastrarClinica(cadastro)
        }
    }

    private fun cadastrarClinica(cadastro: Cadastro?) {
        val cep = binding.editTextNomeClinica.text.toString()
        val logradouro = binding.editTextCnpj.text.toString()
        val bairro = binding.editTextEmail.text.toString()
        val cidade = binding.editTextTelefone.text.toString()
        val uf = binding.editTextRazaoSocial.text.toString()
        val numero = binding.editTextSenha.text.toString()
        val complemento = binding.editTextComplemento.text.toString()

        if (cep.isNotEmpty() || logradouro.isNotEmpty() || bairro.isNotEmpty() || cidade.isNotEmpty() || uf.isNotEmpty() || numero.isNotEmpty()) {
            val endereco = Endereco(logradouro, bairro, cep, cidade, complemento, uf, numero)

            if (cadastro != null) {
                cadastro.endereco = endereco

                apiService.createClinica(cadastro).enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if (response.isSuccessful) {
                            startActivity(
                                Intent(
                                    this@CadastroEnderecoActivity,
                                    MainActivity::class.java
                                )
                            )
                        } else {
                            binding.errorTextView.visibility = View.VISIBLE
                            Log.e("Cadastro", "Erro na resposta: ${response.errorBody()?.string()}")
                        }
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        binding.errorTextView.visibility = View.VISIBLE
                        Log.e("Cadastro", "onFailure: ${t.message}", t)
                    }
                })
            } else {
                binding.errorTextView.visibility = View.VISIBLE
            }
        }
    }
}