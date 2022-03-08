package id.grinaldi.zwallet.ui.auth

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.core.widget.addTextChangedListener
import id.grinaldi.zwallet.R
import id.grinaldi.zwallet.ui.main.MainActivity

class LoginFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnLogin = view.findViewById<AppCompatButton>(R.id.btnLogin)
        val inputEmail = view.findViewById<EditText>(R.id.inputEmail)
        val inputPassword = view.findViewById<EditText>(R.id.inputPassword)

        inputPassword.addTextChangedListener {
            if (inputPassword.text.length > 8) {
                btnLogin.setBackgroundResource(R.drawable.background_button_auth_active)
                btnLogin.setTextColor(Color.parseColor("#FFFFFF"))
            }
        }

        btnLogin.setOnClickListener {
            Toast.makeText(context, "${inputEmail.text} / ${inputPassword.text}", Toast.LENGTH_SHORT).show()
            Handler().postDelayed({
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
            }, 2000)
        }
    }
}