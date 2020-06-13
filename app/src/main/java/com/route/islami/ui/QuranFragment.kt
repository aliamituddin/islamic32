package com.route.islami.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.route.islami.Constants
import com.route.islami.DataHolder
import com.route.islami.R
import com.route.islami.adapters.SurasRecyclerAdapter
import kotlinx.android.synthetic.main.activity_quran.*

/**
 * A simple [Fragment] subclass.
 * Use the [QuranFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class QuranFragment : Fragment() {

    val adapter = SurasRecyclerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quran, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.surasList = DataHolder.ArSuras
        adapter.onItemClickListener = object :
            SurasRecyclerAdapter.OnItemClickListener {
            override fun onItemClick(pos: Int, item: String) {
                val intent = Intent(
                    activity,
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