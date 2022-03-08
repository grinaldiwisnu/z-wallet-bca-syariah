package id.grinaldi.zwallet.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import id.grinaldi.zwallet.R
import id.grinaldi.zwallet.ui.auth.AuthActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        }, 3000)
    }
}