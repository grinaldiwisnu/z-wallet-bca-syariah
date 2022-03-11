package id.grinaldi.zwallet.network

import android.content.Context
import id.grinaldi.zwallet.data.api.ZWalletApi
import id.grinaldi.zwallet.utils.BASE_URL
import id.grinaldi.zwallet.utils.KEY_USER_TOKEN
import id.grinaldi.zwallet.utils.PREFS_NAME
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkConfig(val context: Context?) {
    private val preferences = context?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private fun getInterceptor(authenticator: Authenticator? = null): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val token = preferences?.getString(KEY_USER_TOKEN, "")
        val client = OkHttpClient.Builder()
        client.addInterceptor(logging)

        if (!token.isNullOrEmpty()) {
            client.addInterceptor(TokenInterceptor(token))
        }
        if (authenticator != null) {
            client.authenticator(authenticator)
        }

        return client.build()
    }

    private fun getService(): ZWalletApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getInterceptor())
            .build().create(ZWalletApi::class.java)
    }

    fun buildApi(): ZWalletApi {
        val authenticator = RefreshTokenInterceptor(context, getService(), preferences!!)

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getInterceptor(authenticator))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ZWalletApi::class.java)
    }
}