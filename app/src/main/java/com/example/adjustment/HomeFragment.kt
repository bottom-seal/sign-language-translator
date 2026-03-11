package com.example.adjustment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import java.util.*
import kotlin.concurrent.schedule
import kotlin.random.Random


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var db: DBHelp? = null
    //var parameter = 1 // 定義要 +1 的參數的初始值
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

            // 使用 Timer.schedule 函數來設定定時任務

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        db = DBHelp(requireContext(), "sample.db", 1)
        try {
            db!!.CheckDB()
            db!!.OpenDatabase()
        } catch (e: Exception) {
        }
        val rawtext = view.findViewById<TextView>(R.id.rawtext)
        val searchButton = view.findViewById<ImageView>(R.id.imageView)
        val imageButton = view.findViewById<ImageButton>(R.id.imageButton)
        val autoCompleteTextView = view.findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        val newList = ArrayList<String>()
        autoCompleteTextView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    if (it.length > 0) {
                        newList.clear()
                        newList.addAll(db?.getAAns(it.toString()) ?: emptyList())
                        autoCompleteTextView.setAdapter(
                            ArrayAdapter<String>(
                                requireContext(),
                                android.R.layout.simple_list_item_1,
                                newList
                            )
                        )
                        autoCompleteTextView.threshold = 1
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        autoCompleteTextView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val word: String = newList[position]
                getExample(word)
            }

        searchButton.setOnClickListener {
            val intent = Intent(requireContext(), Dictionary1::class.java)
            startActivity(intent)
        }
        val once = activity?.application as MyApp
        var parameter = once.myVariable
        //if(num==0)
        //{
        //num
        val random = Random(System.currentTimeMillis())
//            var randomNumber = random.nextInt(4)
//
//            while (randomNumber == 0)
//                randomNumber = random.nextInt(4)

        val rand = Random.nextInt(1,125)
        var word2: String? = null
        if (parameter != null) {
            Timer().schedule(0, 5 * 1000) { // 每 5 秒執行一次
                parameter += 1 // 將參數 +1
                if(parameter>125)
                    parameter %=125
                var word1 = db?.GetWord(parameter)
                word2 = word1.toString()
                rawtext.setText(word2)
                println("Parameter has been incremented to $parameter.") // 在控制台輸出新的參數值
            }
        }

        //var word1 = parameter?.let { db?.GetWord(it) }

        imageButton.setOnClickListener{
            val intent = Intent(activity,DictionarySearch::class.java)
            val ans = db?.GetExample(word2)
            val url = db?.GetUrl(word2)
            intent.putExtra("name",word2)
            intent.putExtra("example",ans)
            intent.putExtra("url",url)
            startActivity(intent)
        }

        // }


        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun getExample(word: String?) {
        val ans = db?.GetExample(word)
        val url = db?.GetUrl(word)

        val intent = Intent(requireContext(), DictionarySearch::class.java)
        intent.putExtra("name", word)
        intent.putExtra("example", ans)
        intent.putExtra("url", url)
        requireContext().startActivity(intent)
    }
}