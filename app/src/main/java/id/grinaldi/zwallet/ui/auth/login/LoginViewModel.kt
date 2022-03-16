package id.grinaldi.zwallet.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.grinaldi.zwallet.data.ZWalletDataSource
import id.grinaldi.zwallet.model.APIResponse
import id.grinaldi.zwallet.model.User
import id.grinaldi.zwallet.utils.Resource
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private var dataSource: ZWalletDataSource): ViewModel() {

    fun login(email: String, password: String): LiveData<Resource<APIResponse<User>?>> {
        return dataSource.login(email, password)
    }
}