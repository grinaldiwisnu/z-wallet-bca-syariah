package id.grinaldi.zwallet.ui.auth.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import id.grinaldi.zwallet.R
import id.grinaldi.zwallet.databinding.FragmentLoginBinding
import id.grinaldi.zwallet.ui.main.MainActivity
import id.grinaldi.zwallet.ui.viewModelsFactory
import id.grinaldi.zwallet.utils.*
import id.grinaldi.zwallet.widget.LoadingDialog
import javax.net.ssl.HttpsURLConnection

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModelsFactory { LoginViewModel(requireActivity().application) }
    private lateinit var preferences: SharedPreferences
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        preferences = context?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)!!
        loadingDialog = LoadingDialog(requireActivity())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        binding.inputPassword.addTextChangedListener {
            if (binding.inputPassword.text.length > 8) {
                binding.btnLogin.setBackgroundResource(R.drawable.background_button_auth_active)
                binding.btnLogin.setTextColor(Color.parseColor("#FFFFFF"))
            } else if (binding.inputPassword.text.length <= 8) {
                binding.btnLogin.setBackgroundResource(R.drawable.background_button_auth)
                binding.btnLogin.setTextColor(Color.parseColor("#9DA6B5"))
            }
        }

        binding.btnLogin.setOnClickListener {
            if (binding.inputEmail.text.isNullOrEmpty() || binding.inputPassword.text.isNullOrEmpty()) {
                Toast.makeText(activity, "Email/password is empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val response = viewModel.login(
                binding.inputEmail.text.toString(),
                binding.inputPassword.text.toString()
            )

            response.observe(viewLifecycleOwner) {
                when (it.state) {
                    State.LOADING -> {

                    }
                    State.SUCCESS -> {
                        if (it.resource?.status == HttpsURLConnection.HTTP_OK) {
                            with (preferences.edit()) {
                                putBoolean(KEY_LOGGED_IN, true)
                                putString(KEY_USER_EMAIL, it.resource.data?.email)
                                putString(KEY_USER_TOKEN, it.resource.data?.token)
                                putString(KEY_USER_REFRESH_TOKEN, it.resource.data?.refreshToken)
                                apply()
                            }

                            Handler().postDelayed({
                                val intent = Intent(activity, MainActivity::class.java)
                                startActivity(intent)
                                activity?.finish()
                            }, 1000)
                        } else {
                            Toast.makeText(context, it.resource?.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                    State.ERROR -> {
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }

        binding.textSignUp.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.loginActionRegister)
        }

        binding.textForgotPassword.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }
    }
}