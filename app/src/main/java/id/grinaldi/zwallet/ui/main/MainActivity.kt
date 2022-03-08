package id.grinaldi.zwallet.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.grinaldi.zwallet.R
import id.grinaldi.zwallet.adapter.TransactionAdapter
import id.grinaldi.zwallet.data.Transaction

class MainActivity : AppCompatActivity() {
    private val transactionData = mutableListOf<Transaction>()
    private lateinit var transactionAdapter: TransactionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.transactionAdapter = TransactionAdapter(transactionData)
        val recycler: RecyclerView = findViewById(R.id.recyclerTransaction)
        val layoutManager = LinearLayoutManager(applicationContext)
        recycler.layoutManager = layoutManager
        recycler.adapter = transactionAdapter
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
