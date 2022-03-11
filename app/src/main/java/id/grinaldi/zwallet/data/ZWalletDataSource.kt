package id.grinaldi.zwallet.data

import androidx.lifecycle.liveData
import id.grinaldi.zwallet.data.api.ZWalletApi
import id.grinaldi.zwallet.model.APIResponse
import id.grinaldi.zwallet.model.Invoice
import id.grinaldi.zwallet.model.User
import id.grinaldi.zwallet.model.UserDetail
import id.grinaldi.zwallet.model.request.LoginRequest
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class ZWalletDataSource(private val apiClient: ZWalletApi) {
    fun login(email: String, password: String) = liveData<APIResponse<User>>(Dispatchers.IO) {
        try {
            val loginRequest = LoginRequest(email = email, password = password)
            val response = apiClient.login(loginRequest)
            emit(response)
        } catch (e: Exception) {
            emit(APIResponse(400, e.localizedMessage, null))
        }
    }

    fun getInvoice() = liveData<APIResponse<List<Invoice>>>(Dispatchers.IO) {
        try {
            val response = apiClient.getInvoice()
            emit(response)
        } catch (e: Exception) {
            emit(APIResponse(400, e.localizedMessage, null))
        }
    }

    fun getBalance() = liveData<APIResponse<List<UserDetail>>>(Dispatchers.IO) {
        try {
            val response = apiClient.getBalance()
            emit(response)
        } catch (e: Exception) {
            emit(APIResponse(400, e.localizedMessage, null))
        }
    }
}