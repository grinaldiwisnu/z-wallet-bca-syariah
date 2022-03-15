package id.grinaldi.zwallet.ui.transfer

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.grinaldi.zwallet.data.ZWalletDataSource
import id.grinaldi.zwallet.data.api.ZWalletApi
import id.grinaldi.zwallet.model.APIResponse
import id.grinaldi.zwallet.model.Contact
import id.grinaldi.zwallet.model.request.TransferRequest
import id.grinaldi.zwallet.network.NetworkConfig
import id.grinaldi.zwallet.utils.Resource

class TransferViewModel(app: Application): ViewModel() {
    private var apiClient: ZWalletApi = NetworkConfig(app).buildApi()
    private var dataSource = ZWalletDataSource(apiClient)
    private var selectedContact = MutableLiveData<Contact>()
    private var transfer = MutableLiveData<TransferRequest>()

    fun getContact(): LiveData<Resource<APIResponse<List<Contact>>?>> {
        return dataSource.getContact()
    }

    fun setSelectedContact(contact: Contact) {
        selectedContact.value = contact
    }

    fun getSelectedContact(): MutableLiveData<Contact> {
        return selectedContact
    }

    fun setTransferParam(data: TransferRequest) {
        transfer.value = data
    }

    fun getTransferParam(): MutableLiveData<TransferRequest> {
        return transfer
    }
}