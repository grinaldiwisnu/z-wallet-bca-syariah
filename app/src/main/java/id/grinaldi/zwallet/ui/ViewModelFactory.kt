package id.grinaldi.zwallet.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.grinaldi.zwallet.ui.auth.login.LoginViewModel
import id.grinaldi.zwallet.ui.main.home.HomeViewModel
import id.grinaldi.zwallet.ui.transfer.TransferViewModel

class ViewModelFactory(private val app: Application) : ViewModelProvider
.NewInstanceFactory() {

    companion object {
        @Volatile
            private var instance: ViewModelFactory? = null

        fun getInstance(app: Application): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(app).apply {
                    instance = this
                }
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(TransferViewModel::class.java) -> {
                TransferViewModel(app) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(app) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(app) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }

    }
}