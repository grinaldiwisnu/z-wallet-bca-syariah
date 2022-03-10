package id.grinaldi.zwallet.ui.main

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import id.grinaldi.zwallet.adapter.TransactionAdapter
import id.grinaldi.zwallet.data.api.ZWalletApi
import id.grinaldi.zwallet.databinding.FragmentHomeBinding
import id.grinaldi.zwallet.model.APIResponse
import id.grinaldi.zwallet.model.Invoice
import id.grinaldi.zwallet.model.UserDetail
import id.grinaldi.zwallet.network.NetworkConfig
import id.grinaldi.zwallet.ui.SplashScreenActivity
import id.grinaldi.zwallet.utils.Helper.formatPrice
import id.grinaldi.zwallet.utils.KEY_LOGGED_IN
import id.grinaldi.zwallet.utils.PREFS_NAME
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import javax.net.ssl.HttpsURLConnection

class HomeFragment : Fragment() {
    private val invoices = mutableListOf<Invoice>()
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var binding: FragmentHomeBinding
    private lateinit var prefs: SharedPreferences
    private lateinit var apiClient: ZWalletApi

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        apiClient = NetworkConfig(context).buildApi()
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefs = context?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)!!

        val userBalance = apiClient.getBalance().execute()
        if (userBalance.isSuccessful)
            if (userBalance.body()?.status != HttpsURLConnection.HTTP_OK) {
                Toast.makeText(context, "Fetch detail data failed", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val res = userBalance.body()?.data?.get(0)
                binding.textUserName.text = res?.name
                binding.textUserPhone.text = res?.phone
                binding.textUserBalance.formatPrice(res?.balance.toString())
            }

        this.transactionAdapter = TransactionAdapter(invoices)
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
        val dataInvoice = apiClient.getInvoice().execute()
        if (dataInvoice.isSuccessful)
            if (dataInvoice.body()?.status != HttpsURLConnection.HTTP_OK) {
                Toast.makeText(context, "Fetch invoices data failed", Toast.LENGTH_SHORT)
                    .show()
            } else {
                invoices.addAll(dataInvoice.body()!!.data)
                transactionAdapter.notifyDataSetChanged()
            }
    }
}