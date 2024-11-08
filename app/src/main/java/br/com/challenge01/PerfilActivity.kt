package br.com.challenge01

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import br.com.challenge01.databinding.ActivityPerfilBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PerfilActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPerfilBinding
    private lateinit var apiService : ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.15.3:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

        val clinicaLogada = getClinicaData()
        var numero = 0

        binding.textViewNomeClinicaPerfil.text = clinicaLogada?.nome.toString() + ","
        binding.textViewEmailUser.text = clinicaLogada?.email.toString()
        binding.textViewTelefoneUser.text = clinicaLogada?.telefone.toString()
        binding.textViewCnpjUser.text = clinicaLogada?.cnpj.toString()

        binding.imageButtonPerfil.setOnClickListener {
            if (numero == 3){
                numero = 0
                getClinicaByCnpj(clinicaLogada)
            }else{
                numero++
            }
        }

        binding.imageButtonLogout.setOnClickListener {
            clearClinicaData()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.imageButtonHome.setOnClickListener {
            startActivity(Intent(this, PaginaInicialActivity::class.java))
        }
    }

    private fun getClinicaByCnpj(clinica: Clinica?) {
        if (clinica != null) {
            apiService.getClinica(clinica.cnpj).enqueue(object : Callback<AdicionalPerfil> {
                override fun onResponse(call: Call<AdicionalPerfil>, response: Response<AdicionalPerfil>) {
                    if (response.isSuccessful) {
                        val clinicaResponse = response.body()
                        if (clinicaResponse != null) {
                            binding.textViewDataCriacao.text = clinicaResponse.dataCadastro
                            binding.textViewUf.text = clinicaResponse.endereco.uf
                            binding.textViewId.text = "ID: " + clinica.id.toString()
                        }
                    } else {
                        Log.e("Adicional", "OnResponse: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<AdicionalPerfil>, t: Throwable) {
                    Log.e("Adicional", "onFailure: ${t.message}", t)
                }
            })
        }
    }

}