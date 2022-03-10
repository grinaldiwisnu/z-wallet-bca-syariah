package id.grinaldi.zwallet.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.grinaldi.zwallet.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
