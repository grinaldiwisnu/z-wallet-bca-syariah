package id.grinaldi.zwallet.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import id.grinaldi.zwallet.R
import id.grinaldi.zwallet.data.Transaction

class TransactionAdapter(private var data: List<Transaction>): RecyclerView.Adapter<TransactionAdapter.TransactionAdapterHolder>() {
    lateinit var contextAdapter: Context

    class TransactionAdapterHolder(view: View): RecyclerView.ViewHolder(view) {
        private val image: ShapeableImageView = view.findViewById(R.id.imageTransaction)
        private val name: TextView = view.findViewById(R.id.textTransactionName)
        private val type: TextView = view.findViewById(R.id.textTransactionType)
        private val amount: TextView = view.findViewById(R.id.textTransactionAmount)

        fun bindData(data: Transaction, context: Context, position: Int) {
            name.text = data.transactionName
            type.text = data.transactionType
            amount.text = data.transactionNominal.toString()
            image.setImageDrawable(data.transactionImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionAdapterHolder {
        val inflater = LayoutInflater.from(parent.context)
        this.contextAdapter = parent.context

        val inflatedView: View = inflater.inflate(R.layout.item_transaction_home, parent, false)
        return TransactionAdapterHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: TransactionAdapterHolder, x: Int) {
        holder.bindData(this.data[x], contextAdapter, x)
    }

    override fun getItemCount(): Int {
        return this.data.size
    }
}