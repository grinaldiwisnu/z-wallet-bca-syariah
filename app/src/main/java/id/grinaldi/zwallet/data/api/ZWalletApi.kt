package id.grinaldi.zwallet.data.api

import id.grinaldi.zwallet.model.APIResponse
import id.grinaldi.zwallet.model.Invoice
import id.grinaldi.zwallet.model.User
import id.grinaldi.zwallet.model.UserDetail
import id.grinaldi.zwallet.model.request.LoginRequest
import id.grinaldi.zwallet.model.request.RefreshTokenRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ZWalletApi {
    @POST("auth/login")
    fun login(@Body request: LoginRequest): Call<APIResponse<User>>

    @GET("user/myProfile")
    fun getUserDetail(): Call<APIResponse<UserDetail>>

    @GET("home/getBalance")
    fun getBalance(): Call<APIResponse<List<UserDetail>>>

    @GET("home/getInvoice")
    fun getInvoice(): Call<APIResponse<List<Invoice>>>

    @POST("auth/refresh-token")
    fun refreshToken(@Body request: RefreshTokenRequest): Call<APIResponse<User>>
}