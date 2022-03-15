package id.grinaldi.zwallet.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.grinaldi.zwallet.databinding.ItemTopupBinding
import id.grinaldi.zwallet.model.TopUp

class TopUpAdapter(private var data: List<TopUp>): RecyclerView.Adapter<TopUpAdapter.TopUpAdapterHolder>() {

    class TopUpAdapterHolder(private val binding: ItemTopupBinding): RecyclerView.ViewHolder(binding
        .root) {
        fun bindData(data: TopUp) {
            binding.apply {
                topUpNumber.text = data.number.toString()
                textTopUp.text = data.description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopUpAdapterHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = ItemTopupBinding.inflate(inflater, parent, false)
        return TopUpAdapterHolder(binding)
    }

    override fun onBindViewHolder(holder: TopUpAdapterHolder, x: Int) {
        holder.bindData(this.data[x])
    }

    override fun getItemCount(): Int {
        return this.data.size
    }

    fun addData(data: List<TopUp>) {
        this.data = data
    }
}