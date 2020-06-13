package com.route.islami.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.route.islami.*
import com.route.islami.adapters.SurasRecyclerAdapter
import kotlinx.android.synthetic.main.activity_quran.*


class QuranActivity : AppCompatActivity() {

    val adapter = SurasRecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quran)
        adapter.surasList = DataHolder.ArSuras
        adapter.onItemClickListener = object :
            SurasRecyclerAdapter.OnItemClickListener {
            override fun onItemClick(pos: Int, item: String) {
                val intent = Intent(
                    this@QuranActivity,
                    SuraDetailsActivity::class.java
                )
                intent.putExtra(Constants.EXTRA_SURA_NAME, item)
                intent.putExtra(Constants.EXTRA_SURA_FILE_NAME, ("" + (pos + 1) + ".txt"))
                startActivity(intent)
            }
        }
        recycler_view.adapter = adapter


    }

}
