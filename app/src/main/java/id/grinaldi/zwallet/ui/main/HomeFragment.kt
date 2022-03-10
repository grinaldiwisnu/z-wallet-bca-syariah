package id.grinaldi.zwallet.ui.main

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.window.SplashScreen
import androidx.core.content.res.ResourcesCompat.getDrawable
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import id.grinaldi.zwallet.R
import id.grinaldi.zwallet.adapter.TransactionAdapter
import id.grinaldi.zwallet.data.Transaction
import id.grinaldi.zwallet.databinding.ActivityMainBinding
import id.grinaldi.zwallet.databinding.FragmentHomeBinding
import id.grinaldi.zwallet.model.APIResponse
import id.grinaldi.zwallet.model.User
import id.grinaldi.zwallet.model.UserDetail
import id.grinaldi.zwallet.network.NetworkConfig
import id.grinaldi.zwallet.ui.SplashScreenActivity
import id.grinaldi.zwallet.utils.KEY_LOGGED_IN
import id.grinaldi.zwallet.utils.PREFS_NAME
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.net.ssl.HttpsURLConnection

class HomeFragment : Fragment() {
    private val transactionData = mutableListOf<Transaction>()
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var binding: FragmentHomeBinding
    private lateinit var prefs: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefs = context?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)!!

        NetworkConfig(context).getService().getUserDetail().enqueue(object:
            Callback<APIResponse<UserDetail>>  {
            override fun onResponse(
                call: Call<APIResponse<UserDetail>>,
                response: Response<APIResponse<UserDetail>>
            ) {
                if (response.body()?.status != HttpsURLConnection.HTTP_OK) {
                    Toast.makeText(context, "Fetch detail data failed", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    val res = response.body()?.data
                    binding.textUserName.text = "${res?.firstname} ${res?.lastname}"
                    binding.textUserPhone.text = "${res?.phone}"
                }
            }

            override fun onFailure(call: Call<APIResponse<UserDetail>>, t: Throwable) {
                Toast.makeText(context, "Fetch detail data failed", Toast.LENGTH_SHORT)
                    .show()
            }

        })

        this.transactionAdapter = TransactionAdapter(transactionData)
        val layoutManager = LinearLayoutManager(context)
        binding.recyclerTransaction.layoutManager = layoutManager
        binding.recyclerTransaction.adapter = transactionAdapter
        prepareData()

        binding.profileImage.setOnClickListener {
//            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_profileFragment)
            AlertDialog.Builder(context)
                .setTitle("Logout Confirmation")
                .setMessage("Are you sure want to logout?")
                .setPositiveButton("Yes") { _, _ ->
                    with(prefs.edit()) {
                        putBoolean(KEY_LOGGED_IN, false)
                        apply()
                    }

                    val intent = Intent(activity, SplashScreenActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }
                .setNegativeButton("Cancel") {_, _ ->
                    return@setNegativeButton
                }
                .show()
        }
    }

    private fun prepareData() {
        this.transactionData.add(Transaction(
            transactionImage = activity?.getDrawable(R.drawable.avatar)!!,
            transactionName = "Grinaldi Wisnu",
            transactionNominal = 125000.00,
            transactionType = "Transfer"
        ))

        this.transactionAdapter.notifyDataSetChanged()

    }
}