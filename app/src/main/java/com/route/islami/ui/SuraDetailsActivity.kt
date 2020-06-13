package com.route.islami.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.route.islami.Constants
import com.route.islami.R
import com.route.islami.adapters.VersesRecyclerAdapter
import kotlinx.android.synthetic.main.activity_sura_details.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class SuraDetailsActivity : AppCompatActivity() {

    val adapter = VersesRecyclerAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sura_details)

        val suraName = intent.getStringExtra(Constants.EXTRA_SURA_NAME)
        val fileName = intent.getStringExtra(Constants.EXTRA_SURA_FILE_NAME)
        name.setText(suraName)
        val suraContent = readSuraFile(fileName)
        adapter.changeData(suraContent)
        recycler_view.adapter = adapter

    }

    fun readSuraFile(fileName: String): List<String> {
        val suraContent = mutableListOf<String>()
        var reader: BufferedReader? = null;
        try {
            reader = BufferedReader(
                InputStreamReader(getAssets().open(fileName))
            );

            // do reading, usually loop until end of file reading
            var mLine: String? = null
            mLine = reader.readLine()
            while (mLine != null) {
                //proccess line
                suraContent.add(mLine)
                mLine = reader.readLine()
            }
        } catch (e: IOException) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (e: IOException) {
                    //log the exception
                }
            }
        }

        return suraContent
    }
}
