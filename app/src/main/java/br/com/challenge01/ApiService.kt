package br.com.challenge01

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("clinica/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("clinica")
    fun createClinica(@Body clinica: Cadastro): Call<Void>

    @GET("clinica/{cnpj}")
    fun getClinica(@Path("cnpj") cnpj: String): Call<AdicionalPerfil>
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
