//package com.example.adjustment
//
//import android.content.BroadcastReceiver
//import android.content.Context
//import android.media.AudioManager
//import android.media.VolumeAutomation
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.*
//import androidx.core.content.ContextCompat.getSystemService
//
//// TODO: Rename parameter arguments, choose names that match
//// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"
//
///**
// * A simple [Fragment] subclass.
// * Use the [SettingFragment.newInstance] factory method to
// * create an instance of this fragment.
// */
//class SettingFragment : Fragment() {
//    // TODO: Rename and change types of parameters
//    private lateinit var rootView: View
//    private lateinit var audioManager: AudioManager
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        rootView = inflater.inflate(R.layout.fragment_setting, container, false)
//        return rootView
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        audioManager = context?.getSystemService(Context.AUDIO_SERVICE) as AudioManager
//        val btn_chart = rootView.findViewById<Button>(R.id.btn_chart)
//
//        val seekBar = rootView.findViewById<SeekBar>(R.id.seek_bar)
//        seekBar.max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
//        seekBar.progress = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
//        val textSizeSpinner = view.findViewById<Spinner>(R.id.spin_size)
//        val adapter = ArrayAdapter.createFromResource(
//            requireContext(),
//            R.array.size_array,
//            android.R.layout.simple_spinner_item
//        )
//        textSizeSpinner.adapter = adapter
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        textSizeSpinner.setSelection(1)
//        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
//            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
//                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0)
//            }
//
//            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
//            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
//        })
//    }
//    private var param1: String? = null
//    private var param2: String? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }
//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment SettingFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            SettingFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
//}
package com.example.adjustment


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import androidx.fragment.app.Fragment


class SettingFragment : Fragment() {
    private lateinit var audioManager: AudioManager
    private lateinit var seekBar: SeekBar
    private lateinit var volumeChangeReceiver: VolumeChangeReceiver

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_setting, container, false)

        audioManager = context?.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        seekBar = view.findViewById(R.id.seek_bar)

        val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        val buttonOpenLogin = view.findViewById<Button>(R.id.login)
        val buttonOpenForm = view.findViewById<Button>(R.id.btn_chart)
        val buttonOpenFragment = view.findViewById<Button>(R.id.thankslist)
        val buttonOpenPrivacy = view.findViewById<Button>(R.id.PP)

        buttonOpenLogin.setOnClickListener {

                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)

        }

        buttonOpenForm.setOnClickListener {
            val formUrl = "https://forms.gle/L9c4KEnrzmiGSVwv7"
            val openFormIntent = Intent(Intent.ACTION_VIEW)
            openFormIntent.data = Uri.parse(formUrl)
            startActivity(openFormIntent)
        }
        buttonOpenFragment.setOnClickListener {
            val intent = Intent(activity, contributorfragment::class.java)
            startActivity(intent)
        }
        buttonOpenPrivacy.setOnClickListener {
            val intent = Intent(activity, privacyfragment::class.java)
            startActivity(intent)
        }

        seekBar.max = maxVolume
        seekBar.progress = currentVolume

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        volumeChangeReceiver = VolumeChangeReceiver()
        val filter = IntentFilter("android.media.VOLUME_CHANGED_ACTION")
        requireContext().registerReceiver(volumeChangeReceiver, filter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireContext().unregisterReceiver(volumeChangeReceiver)
    }

    inner class VolumeChangeReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == "android.media.VOLUME_CHANGED_ACTION") {
                val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
                seekBar.progress = currentVolume
            }
        }
    }
}