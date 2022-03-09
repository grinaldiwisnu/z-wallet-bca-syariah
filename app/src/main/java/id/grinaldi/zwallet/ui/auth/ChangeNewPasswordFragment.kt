package id.grinaldi.zwallet.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.grinaldi.zwallet.R
import id.grinaldi.zwallet.databinding.FragmentChangeNewPasswordBinding

class ChangeNewPasswordFragment : Fragment() {
    private lateinit var binding: FragmentChangeNewPasswordBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChangeNewPasswordBinding.inflate(layoutInflater)
        return binding.root
    }
}