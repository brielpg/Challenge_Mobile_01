package br.com.challenge01

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("clinica/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("clinica")
    fun createClinica(@Body clinica: Cadastro): Call<Void>
}

data class LoginRequest(
    val cnpj: String,
    val senha: String
)

data class LoginResponse(
    val id: Int,
    val nome: String,
    val telefone: String,
    val email: String,
    val cnpj: String
)
