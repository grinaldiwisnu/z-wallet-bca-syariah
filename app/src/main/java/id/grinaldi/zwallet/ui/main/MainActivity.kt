package id.grinaldi.zwallet.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.grinaldi.zwallet.R
import id.grinaldi.zwallet.adapter.TransactionAdapter
import id.grinaldi.zwallet.data.Transaction
import id.grinaldi.zwallet.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val transactionData = mutableListOf<Transaction>()
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.transactionAdapter = TransactionAdapter(transactionData)
        val layoutManager = LinearLayoutManager(applicationContext)
        binding.recyclerTransaction.layoutManager = layoutManager
        binding.recyclerTransaction.adapter = transactionAdapter
        prepareData()
    }

    private fun prepareData() {
        this.transactionData.add(Transaction(
            transactionImage = getDrawable(R.drawable.avatar)!!,
            transactionName = "Grinaldi Wisnu",
            transactionNominal = 125000.00,
            transactionType = "Transfer"
        ))

        this.transactionAdapter.notifyDataSetChanged()
    }
}
