package id.grinaldi.zwallet.ui.transfer

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.grinaldi.zwallet.R
import id.grinaldi.zwallet.databinding.FragmentTransferConfirmationBinding
import id.grinaldi.zwallet.ui.ViewModelFactory
import id.grinaldi.zwallet.utils.BASE_URL
import id.grinaldi.zwallet.utils.Helper.formatPrice
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class TransferConfirmationFragment : Fragment() {
    private lateinit var binding: FragmentTransferConfirmationBinding
    private lateinit var viewModel: TransferViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTransferConfirmationBinding.inflate(layoutInflater)
        binding.include.toolbar.title = "Confirmation"

        val factory = ViewModelFactory.getInstance(requireActivity().application)
        viewModel = ViewModelProvider(requireActivity(), factory)[TransferViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.include.toolbar.title = "Confirmation"

        binding.include.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.getSelectedContact().observe(viewLifecycleOwner) {
            binding.transferUserInfo.apply {
                contactName.text = "${it?.name}"
                contactPhoneNum.text = "${it?.phone}"
                Glide.with(contactImage)
                    .load(BASE_URL + it?.image)
                    .apply(
                        RequestOptions.circleCropTransform()
                            .placeholder(R.drawable.ic_baseline_broken_image_24)
                    )
                    .into(contactImage)
            }
        }

        viewModel.getTransferParam().observe(viewLifecycleOwner) {
            binding.valueAmount.formatPrice(it.amount.toString())
            binding.valueBalance.formatPrice(it.amount.toString())
            if (it.notes.isNullOrEmpty()) {
                binding.valueNotes.text = "-"
            } else {
                binding.valueNotes.text = it.notes
            }
            // format date
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val current = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy - HH:mma")
                val answer =  current.format(formatter)
                binding.valueDate.text = answer
            } else {
                val date = Date()
                val formatter = SimpleDateFormat("MMM dd, yyyy - HH:mma")
                val answer = formatter.format(date)
                binding.valueDate.text = answer
            }
        }
    }
}