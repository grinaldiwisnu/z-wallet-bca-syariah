package id.grinaldi.zwallet.ui.auth.login

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.grinaldi.zwallet.data.ZWalletDataSource
import id.grinaldi.zwallet.data.api.ZWalletApi
import id.grinaldi.zwallet.model.APIResponse
import id.grinaldi.zwallet.model.User
import id.grinaldi.zwallet.network.NetworkConfig

class LoginViewModel(app: Application): ViewModel() {
    private var apiClient: ZWalletApi = NetworkConfig(app).buildApi()
    private var dataSource = ZWalletDataSource(apiClient)

    fun login(email: String, password: String): LiveData<APIResponse<User>> {
        return dataSource.login(email, password)
    }
}