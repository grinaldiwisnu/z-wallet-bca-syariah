package id.grinaldi.zwallet.ui.main.home

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
import id.grinaldi.zwallet.ui.viewModelsFactory
import id.grinaldi.zwallet.utils.Helper.formatPrice
import id.grinaldi.zwallet.utils.KEY_LOGGED_IN
import id.grinaldi.zwallet.utils.PREFS_NAME
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import javax.net.ssl.HttpsURLConnection

class HomeFragment : Fragment() {
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var binding: FragmentHomeBinding
    private lateinit var prefs: SharedPreferences
    private val viewModel: HomeViewModel by viewModelsFactory { HomeViewModel(requireActivity().application) }

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
        this.transactionAdapter = TransactionAdapter(listOf())
        binding.recyclerTransaction.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = transactionAdapter
        }

        viewModel.getBalance().observe(viewLifecycleOwner) {
            if (it.status == HttpsURLConnection.HTTP_OK) {
                binding.apply {
                    textUserBalance.formatPrice(it.data?.get(0)?.balance.toString())
                    textUserPhone.text = it.data?.get(0)?.phone
                    textUserName.text = it.data?.get(0)?.name
                }
            } else {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT)
                    .show()
            }
        }

        viewModel.getInvoice().observe(viewLifecycleOwner) {
            if (it.status == HttpsURLConnection.HTTP_OK) {
                this.transactionAdapter.apply {
                    addData(it.data!!)
                    notifyDataSetChanged()
                }
            } else {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}