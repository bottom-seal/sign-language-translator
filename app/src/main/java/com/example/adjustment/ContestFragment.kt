package com.example.adjustment

import android.app.AlertDialog
import android.app.Dialog
import android.database.DatabaseUtils
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlin.random.Random
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions




private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

var score = 0
var reset = false
lateinit var txtScore: TextView
//this part works fine
class MyDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Create a new dialog and return it
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("haha")
        builder.setMessage("You just lost, loser\nbtw your point is " + score )
        builder.setPositiveButton("close") { dialog, which ->
            // Do something when the user clicks the OK button
            score = 0
            reset = true
            txtScore.text = score.toString()
        }
        return builder.create()
    }
}

class ContestFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private var db: DBHelp? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        db = DBHelp(requireContext(), "sample.db", 1)
        try {
            db!!.CheckDB()
            db!!.OpenDatabase()
        } catch (e: Exception) {
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contest, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*db = DBHelp(requireContext(), "sample.db", 1)*/
        /*try {
            db.copydb();
            db.OpenDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        /*try {
            db!!.CheckDB()
            db!!.OpenDatabase()
        } catch (e: Exception) {
        }
        */
        //val db = DBHelp(requireContext(), "sample.db", 1).readableDatabase

        //val rowCount = DatabaseUtils.queryNumEntries(db, "words").toInt()

        //val videoUrl = db!!.GetUrl()
        //val videoUrl = "https://sample-videos.com/video123/mp4/720/big_buck_bunny_720p_1mb.mp4"
        //videoView.setVideoURI(Uri.parse(videoUrl))
        val videoView = view.findViewById<YouTubePlayerView>(R.id.videoView)


        val replayBtn = view.findViewById<ImageButton>(R.id.replayBtn)
//        replayBtn.setOnClickListener() {
//            videoView.seekTo(0)
//            videoView.start()
//        }

        val button1 = view.findViewById<Button>(R.id.button1)
        val button2 = view.findViewById<Button>(R.id.button2)
        val button3 = view.findViewById<Button>(R.id.button3)
        txtScore = view.findViewById<TextView>(R.id.score)
        val heart1 = view.findViewById<ImageView>(R.id.heart1)
        val heart2 = view.findViewById<ImageView>(R.id.heart2)
        val heart3 = view.findViewById<ImageView>(R.id.heart3)
        var life = 3
        val arrBtn = arrayOf(button1, button2, button3)
        txtScore.text = score.toString()

        //initialize first answer
        val random = Random(System.currentTimeMillis())
        var randomNumber = random.nextInt(3)

        var ansNum = 0
        var testNum = 0
        var tempNum = -1

        ansNum = random.nextInt(125) + 1

        arrBtn[randomNumber].text = db!!.GetIdItem(ansNum)
        videoView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
//                val options: IFramePlayerOptions = IFramePlayerOptions.Builder().controls(0).build()
//                youtubePlayerView.initialize(getString("REMOVE_API_KEY") , options)

                youTubePlayer.loadVideo(db?.GetIdUrl(ansNum)as String, 0F)
                youTubePlayer.play()

                for (i in arrBtn.indices) {
                    if (i == randomNumber)
                    {
                        continue
                    }
                    else
                    {
                        testNum = random.nextInt(125) + 1
                        while(testNum == ansNum || testNum == tempNum)
                        {
                            testNum = random.nextInt(125) + 1
                        }
                        tempNum = testNum
                        arrBtn[i].text = db!!.GetIdItem(testNum)
                    }
                }

                for (button in arrBtn)
                {
                    button.setOnClickListener {
                        txtScore.text = score.toString()
                        when(it) {
                            button1 -> {
                                if (randomNumber == 0) {
                                    score++
                                } else {
                                    life--
                                }
                            }
                            button2 -> {
                                if (randomNumber == 1) {
                                    score++
                                } else {
                                    life--
                                }
                            }
                            button3 -> {
                                if (randomNumber == 2) {
                                    score++
                                } else {
                                    life--
                                }
                            }
                        }
                        txtScore.text = score.toString()
                        when (life) {
                            2 -> {
                                heart3.setVisibility(View.INVISIBLE)
                            }
                            1 -> {
                                heart2.setVisibility(View.INVISIBLE)
                            }
                            0 -> {
                                heart1.setVisibility(View.INVISIBLE)
                                val dialog = MyDialogFragment()
                                dialog.show(requireFragmentManager(), "MyDialogFragment")
                                life = 3
                                heart1.setVisibility(View.VISIBLE)
                                heart2.setVisibility(View.VISIBLE)
                                heart3.setVisibility(View.VISIBLE)
                            }
                        }
                        //get next answer
                        randomNumber = random.nextInt(3)
                        tempNum = -1
                        ansNum = random.nextInt(125) + 1

                        youTubePlayer.loadVideo(db?.GetIdUrl(ansNum)as String, 0F)
                        youTubePlayer.play()

                        arrBtn[randomNumber].text = db!!.GetIdItem(ansNum)

                        for (i in arrBtn.indices) {
                            if (i == randomNumber)
                            {
                                continue
                            }
                            else
                            {
                                testNum = random.nextInt(125) + 1
                                while(testNum == ansNum || testNum == tempNum)
                                {
                                    testNum = random.nextInt(125) + 1
                                }
                                tempNum = testNum
                                arrBtn[i].text = db!!.GetIdItem(testNum)
                            }
                        }
                    }
                }
            }
        })
    }
}