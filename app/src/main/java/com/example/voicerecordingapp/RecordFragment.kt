package com.example.voicerecordingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_record.*


class RecordFragment : Fragment() {

    var navController : NavController? = null
    var isRecording : Boolean = false

        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_record, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        list_button.setOnClickListener {
            navController!!.navigate(R.id.action_recordFragment_to_audioListFragment)
        }
        record_button.setOnClickListener{
            if (isRecording){
                record_button.setImageDrawable(resources.getDrawable(R.drawable.record_btn_stopped, null))
                isRecording = false
            }else{
                record_button.setImageDrawable(resources.getDrawable(R.drawable.record_btn_recording, null))
                isRecording = true
            }
        }
    }


}
