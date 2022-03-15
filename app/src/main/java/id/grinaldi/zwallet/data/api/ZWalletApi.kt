package id.grinaldi.zwallet.data.api

import id.grinaldi.zwallet.model.*
import id.grinaldi.zwallet.model.request.LoginRequest
import id.grinaldi.zwallet.model.request.RefreshTokenRequest
import id.grinaldi.zwallet.model.request.SetPinRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ZWalletApi {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): APIResponse<User>

    @GET("user/myProfile")
    fun getUserDetail(): Call<APIResponse<UserDetail>>

    @GET("home/getBalance")
    suspend fun getBalance(): APIResponse<List<UserDetail>>

    @GET("home/getInvoice")
    suspend fun getInvoice(): APIResponse<List<Invoice>>

    @POST("auth/refresh-token")
    fun refreshToken(@Body request: RefreshTokenRequest): Call<APIResponse<User>>

    @GET("auth/auth/checkPIN/{PIN}")
    suspend fun validatePIN(@Path("PIN") pin: Int): APIResponse<String>

    @GET("auth/auth/checkPIN/{PIN}")
    suspend fun setPIN(@Body request: SetPinRequest): APIResponse<String>

    @GET("tranfer/contactUser")
    suspend fun getContacts(): APIResponse<List<Contact>>
}