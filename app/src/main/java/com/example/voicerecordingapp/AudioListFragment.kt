package com.example.voicerecordingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.fragment_audio_list.*
import kotlinx.android.synthetic.main.media_player.*
import java.io.File


class AudioListFragment : Fragment() {


    var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>? = null
    lateinit var allfiles: Array<File>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_audio_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val path = activity?.getExternalFilesDir("/")!!.absolutePath
        var directory : File = File(path)
        allfiles = directory.listFiles()
        val adapter = AudioListAdapter(allfiles)
        audioListRecyclerView.setHasFixedSize(true)
        audioListRecyclerView.layoutManager = LinearLayoutManager(context)
        audioListRecyclerView.adapter = adapter

        bottomSheetBehavior = BottomSheetBehavior.from(player_sheet)
        (bottomSheetBehavior as BottomSheetBehavior<ConstraintLayout>).addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback(){
            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if(newState == BottomSheetBehavior.STATE_HIDDEN){
                    (bottomSheetBehavior as BottomSheetBehavior<ConstraintLayout>).state = BottomSheetBehavior.STATE_COLLAPSED
                }
            }

        })
    }


}
