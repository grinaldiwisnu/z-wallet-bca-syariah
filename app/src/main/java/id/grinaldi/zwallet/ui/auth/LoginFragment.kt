package id.grinaldi.zwallet.ui.auth

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.core.widget.addTextChangedListener
import id.grinaldi.zwallet.R
import id.grinaldi.zwallet.databinding.FragmentLoginBinding
import id.grinaldi.zwallet.ui.main.MainActivity

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        binding.inputPassword.addTextChangedListener {
            if (binding.inputPassword.text.length > 8) {
                binding.btnLogin.setBackgroundResource(R.drawable.background_button_auth_active)
                binding.btnLogin.setTextColor(Color.parseColor("#FFFFFF"))
            }
        }

        binding.btnLogin.setOnClickListener {
            Toast.makeText(context, "${binding.inputEmail.text} / ${binding.inputPassword.text}", Toast.LENGTH_SHORT).show()
            Handler().postDelayed({
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
            }, 2000)
        }
    }
}