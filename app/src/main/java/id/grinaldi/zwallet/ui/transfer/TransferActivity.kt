package id.grinaldi.zwallet.ui.transfer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.grinaldi.zwallet.databinding.ActivityMainBinding
import id.grinaldi.zwallet.databinding.ActivityTransferBinding

class TransferActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTransferBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransferBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}