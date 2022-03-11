package id.grinaldi.zwallet.network

import android.content.Context
import android.content.SharedPreferences
import id.grinaldi.zwallet.data.api.ZWalletApi
import id.grinaldi.zwallet.model.request.RefreshTokenRequest
import id.grinaldi.zwallet.utils.KEY_USER_EMAIL
import id.grinaldi.zwallet.utils.KEY_USER_REFRESH_TOKEN
import id.grinaldi.zwallet.utils.KEY_USER_TOKEN
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.net.ssl.HttpsURLConnection

class RefreshTokenInterceptor(
    val context: Context?,
    private val client: ZWalletApi,
    private val prefs: SharedPreferences
): Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val updatedToken = getNewToken()
        return if (updatedToken == null)
            null
        else
            response.request.newBuilder()
                .header("Authorization", "Bearer $updatedToken")
                .build()
    }

    private fun getNewToken(): String? {
        val request = RefreshTokenRequest(
            prefs.getString(KEY_USER_EMAIL, "")!!,
            prefs.getString(KEY_USER_REFRESH_TOKEN, "")!!
        )

        val response = client.refreshToken(request).execute().body()
        return if (response?.status != HttpsURLConnection.HTTP_CREATED)
            null
        else {
            with (prefs.edit()) {
                putString(KEY_USER_TOKEN, response.data?.token)
                apply()
            }

            response.data?.token
        }
    }
}