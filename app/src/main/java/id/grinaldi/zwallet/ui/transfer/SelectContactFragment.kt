package id.grinaldi.zwallet.ui.transfer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.grinaldi.zwallet.R
import id.grinaldi.zwallet.adapter.ContactAdapter
import id.grinaldi.zwallet.databinding.FragmentSelectContactBinding
import id.grinaldi.zwallet.utils.State
import id.grinaldi.zwallet.widget.LoadingDialog
import javax.net.ssl.HttpsURLConnection

@AndroidEntryPoint
class SelectContactFragment : Fragment() {
    private lateinit var binding: FragmentSelectContactBinding
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var contactAdapter: ContactAdapter
    private val viewModel: TransferViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectContactBinding.inflate(layoutInflater)
        loadingDialog = LoadingDialog(requireActivity())
        binding.include.toolbar.title = "Find Receiver"

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.include.toolbar.setNavigationOnClickListener {
            activity?.finish()
        }

        this.contactAdapter = ContactAdapter(listOf()) { contact, _ ->
            viewModel.setSelectedContact(contact)
            Navigation.findNavController(view).navigate(R.id
                .action_selectContactFragment_to_transferAmountFragment)
        }
        binding.recyclerContact.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = contactAdapter
        }
        viewModel.getContact().observe(viewLifecycleOwner) {
            when (it.state) {
                State.LOADING -> {
                    binding.apply {
                        loadingIndicator.visibility = View.VISIBLE
                        recyclerContact.visibility = View.GONE
                    }
                }
                State.SUCCESS -> {
                    binding.apply {
                        loadingIndicator.visibility = View.GONE
                        recyclerContact.visibility = View.VISIBLE
                    }
                    if (it.resource?.status == HttpsURLConnection.HTTP_OK) {
                        this.contactAdapter.apply {
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
                        recyclerContact.visibility = View.VISIBLE
                    }
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}