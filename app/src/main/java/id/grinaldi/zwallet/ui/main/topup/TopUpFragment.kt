package id.grinaldi.zwallet.ui.main.topup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import id.grinaldi.zwallet.R
import id.grinaldi.zwallet.adapter.TopUpAdapter
import id.grinaldi.zwallet.databinding.FragmentTopUpBinding
import id.grinaldi.zwallet.utils.TopUpStep

class TopUpFragment : Fragment() {
    private lateinit var binding: FragmentTopUpBinding
    private lateinit var topUpAdapter: TopUpAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTopUpBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        topUpAdapter = TopUpAdapter(TopUpStep.getStep())
        binding.topUpRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = topUpAdapter
        }
        binding.include.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}