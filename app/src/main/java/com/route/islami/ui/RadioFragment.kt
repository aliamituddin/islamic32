package com.route.islami.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.route.islami.ApiManager.ApiManager
import com.route.islami.ApiManager.model.RadioChannel
import com.route.islami.ApiManager.model.RadioResponse
import com.route.islami.R
import com.route.islami.adapters.RadioRecyclerAdapter
import com.route.islami.player.PlayerService
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
        adapter.onPlayClickListener = object : RadioRecyclerAdapter.OnItemClickListener {
            override fun onItemClick(pos: Int, item: RadioChannel) {
                startRadioService(item)
            }
        }
        adapter.onStopClickListener = object : RadioRecyclerAdapter.OnItemClickListener {
            override fun onItemClick(pos: Int, item: RadioChannel) {
                stopPlayerService()
            }
        }

        //call api
        getChannelsFromApi()

    }

    override fun onStart() {
        super.onStart()
        startService();
        bindService()
    }

    private fun startService() {
        val intent = Intent(activity, PlayerService::class.java)
        activity?.startService(intent)
    }

    override fun onStop() {
        super.onStop()
        activity?.unbindService(connection)
    }

    fun bindService() {
        val intent = Intent(activity, PlayerService::class.java)
        activity?.bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

    private fun startRadioService(item: RadioChannel) {
        if (bound)
            service.startMediaPlayer(item.radioUrl!!, item.name!!);
/*
        val intent = Intent(activity,PlayerService::class.java)
        intent.putExtra("url",item)
        activity?.startService(intent);
*/
    }

    fun stopPlayerService() {
        if (bound)
            service.pauseMediaPlayer()
    }

    lateinit var service: PlayerService;
    var bound = false;

    /** Defines callbacks for service binding, passed to bindService()  */
    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, mBinder: IBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            val binder = mBinder as PlayerService.MyBinder
            service = mBinder.getService()
            bound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            bound = false
        }
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