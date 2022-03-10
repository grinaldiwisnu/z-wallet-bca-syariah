package id.grinaldi.zwallet.network

import android.content.Context
import id.grinaldi.zwallet.utils.KEY_USER_TOKEN
import id.grinaldi.zwallet.utils.PREFS_NAME
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(val context: Context?) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        val prefs = context?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val token = prefs?.getString(KEY_USER_TOKEN, "")

        if (!token.isNullOrEmpty()) {
            request.header("Authorization", "Bearer $token")
        }

        return chain.proceed(request.build())
    }
}