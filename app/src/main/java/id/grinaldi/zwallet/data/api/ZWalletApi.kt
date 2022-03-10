package id.grinaldi.zwallet.data.api

import id.grinaldi.zwallet.model.APIResponse
import id.grinaldi.zwallet.model.User
import id.grinaldi.zwallet.model.UserDetail
import id.grinaldi.zwallet.model.request.LoginRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ZWalletApi {
    @POST("auth/login")
    fun login(@Body request: LoginRequest): Call<APIResponse<User>>

    @GET("user/myProfile")
    fun getUserDetail(): Call<APIResponse<UserDetail>>

}