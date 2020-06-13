package com.route.islami.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.route.islami.ApiManager.ApiManager
import com.route.islami.ApiManager.model.RadioResponse
import com.route.islami.R
import com.route.islami.adapters.RadioRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_radio.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 * Use the [RadioFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RadioFragment : Fragment() {


    val adapter = RadioRecyclerAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_radio, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view.adapter = adapter

        //call api
        getChannelsFromApi()
        //Todo: set click listener for play and stop
        // when click on play -> play url of channel using android mediaplayer

    }

    private fun getChannelsFromApi() {

        //show loading dialog
        ApiManager.getWebServices()
            .getRadioChannels()
            .enqueue(object : Callback<RadioResponse> {
                override fun onFailure(call: Call<RadioResponse>, t: Throwable) {
                    //hideloadingDialog
                    Toast.makeText(activity, t.localizedMessage ?: "error", Toast.LENGTH_LONG)
                        .show()
                }

                override fun onResponse(
                    call: Call<RadioResponse>,
                    response: Response<RadioResponse>
                ) {
                    //hideLoadingDialog
                    progress_bar.visibility = View.GONE
                    val channels = response.body()?.radios;
                    adapter.changeData(channels ?: listOf())
                }
            })
    }
}