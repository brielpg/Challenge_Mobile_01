package br.com.challenge01

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.com.challenge01.databinding.ActivityPaginaInicialBinding

class PaginaInicialActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaginaInicialBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaginaInicialBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.imageButtonPerfil.setOnClickListener {
            startActivity(Intent(this, PerfilActivity::class.java))
        }
    }
}