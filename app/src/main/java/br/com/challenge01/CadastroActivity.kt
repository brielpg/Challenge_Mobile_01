package br.com.challenge01

import android.content.Intent
import android.os.Parcelable
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.com.challenge01.databinding.ActivityCadastroBinding
import android.os.Parcel

class CadastroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCadastroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.btnBack.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.btnEntrar.setOnClickListener {
            avancarEndereco()
        }
    }

    private fun avancarEndereco(){
        val cnpj = binding.editTextCnpj.text.toString()
        val email = binding.editTextEmail.text.toString()
        val nomeClinica = binding.editTextNomeClinica.text.toString()
        val telefone = binding.editTextTelefone.text.toString()
        val razaoSocial = binding.editTextRazaoSocial.text.toString()
        val senha = binding.editTextSenha.text.toString()

        if (cnpj.isNotEmpty() || email.isNotEmpty() || nomeClinica.isNotEmpty() || telefone.isNotEmpty() || razaoSocial.isNotEmpty() || senha.isNotEmpty()){
            val cadastro = Cadastro(nomeClinica, cnpj, telefone, email, razaoSocial, senha, null)

            val telaCadastroEndereco = Intent(this, CadastroEnderecoActivity::class.java).apply {
                putExtra("cadastro", cadastro)
            }

            startActivity(telaCadastroEndereco)
        }else{
            binding.errorTextView.visibility = View.VISIBLE
        }
    }
}