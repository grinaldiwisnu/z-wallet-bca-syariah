package id.grinaldi.zwallet.ui.main.home

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.grinaldi.zwallet.R
import id.grinaldi.zwallet.adapter.TransactionAdapter
import id.grinaldi.zwallet.databinding.FragmentHomeBinding
import id.grinaldi.zwallet.ui.SplashScreenActivity
import id.grinaldi.zwallet.ui.transfer.TransferActivity
import id.grinaldi.zwallet.utils.Helper.formatPrice
import id.grinaldi.zwallet.utils.KEY_LOGGED_IN
import id.grinaldi.zwallet.utils.PREFS_NAME
import id.grinaldi.zwallet.utils.State
import id.grinaldi.zwallet.widget.LoadingDialog
import javax.net.ssl.HttpsURLConnection

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var binding: FragmentHomeBinding
    private lateinit var prefs: SharedPreferences
    private lateinit var loadingDialog: LoadingDialog
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        loadingDialog = LoadingDialog(requireActivity())

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

        binding.buttonTransfer.setOnClickListener {
            startActivity(Intent(context, TransferActivity::class.java))
        }

        binding.buttonTopUp.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_topUpFragment)
        }
    }

    private fun prepareData() {
        this.transactionAdapter = TransactionAdapter(listOf())
        binding.recyclerTransaction.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = transactionAdapter
        }

        viewModel.getBalance().observe(viewLifecycleOwner) {
            when (it.state) {
                State.LOADING -> {
                    loadingDialog.start("Fetching your personal data")
                }
                State.SUCCESS -> {
                    loadingDialog.stop()
                    if (it.resource?.status == HttpsURLConnection.HTTP_OK) {
                        binding.apply {
                            textUserBalance.formatPrice(it.resource.data?.get(0)?.balance.toString())
                            textUserPhone.text = it.resource.data?.get(0)?.phone
                            textUserName.text = it.resource.data?.get(0)?.name
                        }
                    } else {
                        Toast.makeText(context, it.resource?.message, Toast.LENGTH_SHORT).show()
                    }
                }
                State.ERROR -> {
                    loadingDialog.stop()
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.getInvoice().observe(viewLifecycleOwner) {
            when (it.state) {
                State.LOADING -> {
                    binding.apply {
                        loadingIndicator.visibility = View.VISIBLE
                        recyclerTransaction.visibility = View.GONE
                    }
                }
                State.SUCCESS -> {
                    binding.apply {
                        loadingIndicator.visibility = View.GONE
                        recyclerTransaction.visibility = View.VISIBLE
                    }
                    if (it.resource?.status == HttpsURLConnection.HTTP_OK) {
                        this.transactionAdapter.apply {
                            addData(it.resource.data!!)
                            notifyDataSetChanged()
                        }
                    } else {
                        Toast.makeText(context, it.resource?.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                State.ERROR -> {
                    binding.apply {
                        loadingIndicator.visibility = View.GONE
                        recyclerTransaction.visibility = View.VISIBLE
                    }
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}