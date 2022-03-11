package id.grinaldi.zwallet.ui.main.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.grinaldi.zwallet.data.ZWalletDataSource
import id.grinaldi.zwallet.data.api.ZWalletApi
import id.grinaldi.zwallet.model.APIResponse
import id.grinaldi.zwallet.model.Invoice
import id.grinaldi.zwallet.model.UserDetail
import id.grinaldi.zwallet.network.NetworkConfig

class HomeViewModel(app: Application): ViewModel() {
    private var apiClient: ZWalletApi = NetworkConfig(app).buildApi()
    private var dataSource = ZWalletDataSource(apiClient)

    fun getInvoice(): LiveData<APIResponse<List<Invoice>>> {
        return dataSource.getInvoice()
    }

    fun getBalance(): LiveData<APIResponse<List<UserDetail>>> {
        return dataSource.getBalance()
    }
}