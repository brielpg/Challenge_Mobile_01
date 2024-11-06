package br.com.challenge01

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import br.com.challenge01.databinding.ActivityPerfilBinding

class PerfilActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPerfilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        val clinicaLogada = getClinicaData()

        binding.textViewNomeClinicaPerfil.text = clinicaLogada?.nome.toString()
        binding.textViewEmailUser.text = clinicaLogada?.email.toString()
        binding.textViewTelefoneUser.text = clinicaLogada?.telefone.toString()
        binding.textViewCnpjUser.text = clinicaLogada?.cnpj.toString()

        binding.imageButtonLogout.setOnClickListener {
            clearClinicaData()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.imageButtonHome.setOnClickListener {
            startActivity(Intent(this, PaginaInicialActivity::class.java))
        }
    }
}