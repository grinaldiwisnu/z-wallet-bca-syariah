package id.grinaldi.zwallet.ui.transfer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint
import id.grinaldi.zwallet.R
import id.grinaldi.zwallet.databinding.FragmentTransferAmountBinding
import id.grinaldi.zwallet.model.request.TransferRequest
import id.grinaldi.zwallet.utils.BASE_URL

@AndroidEntryPoint
class TransferAmountFragment : Fragment() {
    private lateinit var binding: FragmentTransferAmountBinding
    private val viewModel: TransferViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTransferAmountBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.include.toolbar.title = "Transfer"

        binding.buttonContinueConfirmation.setOnClickListener {
            viewModel.setTransferParam(TransferRequest(
                "",
                binding.inputTransferAmount.text.toString().toInt(),
                binding.inputTransferDesc.text.toString()
            ))

            Navigation.findNavController(view).navigate(R.id.action_transferAmountFragment_to_transferConfirmationFragment)
        }
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
    }
}