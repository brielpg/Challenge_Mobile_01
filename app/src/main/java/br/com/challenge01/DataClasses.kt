package br.com.challenge01

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import java.sql.Date

fun Context.saveClinicaData(clinica: Clinica) {
    val sharedPref = getSharedPreferences("clinica_prefs", Context.MODE_PRIVATE) ?: return
    with(sharedPref.edit()) {
        putInt("id", clinica.id)
        putString("nome", clinica.nome)
        putString("email", clinica.email)
        putString("telefone", clinica.telefone)
        putString("cnpj", clinica.cnpj)
        apply()
    }
}

fun Context.getClinicaData(): Clinica? {
    val sharedPref = getSharedPreferences("clinica_prefs", Context.MODE_PRIVATE)
    val id = sharedPref.getInt("id", -1)
    val nome = sharedPref.getString("nome", null) ?: return null
    val email = sharedPref.getString("email", null) ?: return null
    val telefone = sharedPref.getString("telefone", null) ?: return null
    val cnpj = sharedPref.getString("cnpj", null) ?: return null
    return Clinica(id, nome, telefone, email, cnpj)
}

fun Context.clearClinicaData() {
    val sharedPref = getSharedPreferences("clinica_prefs", Context.MODE_PRIVATE)
    with(sharedPref.edit()) {
        clear()
        apply()
    }
}

data class AdicionalPerfil(
    val dataCadastro: Date,
    val uf: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        Date(parcel.readLong()),
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(dataCadastro.time)
        parcel.writeString(uf)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Clinica> {
        override fun createFromParcel(parcel: Parcel): Clinica {
            return Clinica(parcel)
        }

        override fun newArray(size: Int): Array<Clinica?> {
            return arrayOfNulls(size)
        }
    }
}


data class Clinica(
    val id: Int,
    val nome: String,
    val telefone: String,
    val email: String,
    val cnpj: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(nome)
        parcel.writeString(telefone)
        parcel.writeString(email)
        parcel.writeString(cnpj)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Clinica> {
        override fun createFromParcel(parcel: Parcel): Clinica {
            return Clinica(parcel)
        }

        override fun newArray(size: Int): Array<Clinica?> {
            return arrayOfNulls(size)
        }
    }
}

data class Endereco(
    val logradouro: String,
    val bairro: String,
    val cep: String,
    val cidade: String,
    val complemento: String,
    val uf: String,
    val numero: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(logradouro)
        parcel.writeString(bairro)
        parcel.writeString(cep)
        parcel.writeString(cidade)
        parcel.writeString(complemento)
        parcel.writeString(uf)
        parcel.writeString(numero)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Endereco> {
        override fun createFromParcel(parcel: Parcel): Endereco {
            return Endereco(parcel)
        }

        override fun newArray(size: Int): Array<Endereco?> {
            return arrayOfNulls(size)
        }
    }
}

data class Cadastro(
    val nome: String,
    val cnpj: String,
    val telefone: String,
    val email: String,
    val razaoSocial: String,
    val senha: String,
    var endereco: Endereco? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readParcelable(Endereco::class.java.classLoader) ?: Endereco("", "", "", "", "", "", "")
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nome)
        parcel.writeString(cnpj)
        parcel.writeString(telefone)
        parcel.writeString(email)
        parcel.writeString(razaoSocial)
        parcel.writeString(senha)
        parcel.writeParcelable(endereco, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Cadastro> {
        override fun createFromParcel(parcel: Parcel): Cadastro {
            return Cadastro(parcel)
        }

        override fun newArray(size: Int): Array<Cadastro?> {
            return arrayOfNulls(size)
        }
    }
}