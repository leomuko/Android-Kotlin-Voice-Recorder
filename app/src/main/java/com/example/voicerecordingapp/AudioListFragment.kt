package com.example.voicerecordingapp

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.fragment_audio_list.*
import kotlinx.android.synthetic.main.media_player.*
import java.io.File
import java.io.IOException


class AudioListFragment : Fragment(), onItemListClick {


    var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>? = null
    lateinit var allfiles: Array<File>
    var mediaPlayer: MediaPlayer? = null
    var isPlaying: Boolean = false
    var fileToPlay: File? = null
    var seekBarHandler: Handler? = null
    var updateSeekBar: Runnable? = null

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
        var directory: File = File(path)
        allfiles = directory.listFiles()
        val adapter = AudioListAdapter(allfiles, this)
        audioListRecyclerView.setHasFixedSize(true)
        audioListRecyclerView.layoutManager = LinearLayoutManager(context)
        audioListRecyclerView.adapter = adapter


        bottomSheetBehavior = BottomSheetBehavior.from(player_sheet)
        (bottomSheetBehavior as BottomSheetBehavior<ConstraintLayout>).addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    (bottomSheetBehavior as BottomSheetBehavior<ConstraintLayout>).state =
                        BottomSheetBehavior.STATE_COLLAPSED
                }
            }

        })
        media_play_button.setOnClickListener {
            if (isPlaying) {
                pauseAudio()
            } else {
                if (fileToPlay != null) {
                    resumeAudio()
                }

            }
        }
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                if (fileToPlay != null) {
                    pauseAudio()
                }

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (fileToPlay != null) {
                    val progress = seekBar!!.progress
                    mediaPlayer!!.seekTo(progress)
                    resumeAudio()
                }

            }

        })
    }

    override fun onClickListener(file: File, position: Int) {
        fileToPlay = file
        if (isPlaying) {
            stopAudio()

            playAudio(fileToPlay)
        } else {
            playAudio(fileToPlay)
        }

    }

    private fun stopAudio() {
        media_play_button.setImageDrawable(
            activity?.resources?.getDrawable(
                R.drawable.player_play_btn,
                null
            )
        )
        player_header_status.setText("Stopped")
        isPlaying = false
        mediaPlayer!!.stop()
        seekBarHandler!!.removeCallbacks(updateSeekBar!!)
    }

    private fun playAudio(file: File?) {
        mediaPlayer = MediaPlayer()

        bottomSheetBehavior?.state = BottomSheetBehavior.STATE_EXPANDED

        try {
            mediaPlayer!!.setDataSource(fileToPlay!!.absolutePath)
            mediaPlayer!!.prepare()
            mediaPlayer!!.start()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        media_play_button.setImageDrawable(
            activity?.resources?.getDrawable(
                R.drawable.player_pause_btn,
                null
            )
        )
        media_file_name.setText(fileToPlay?.name)
        player_header_status.setText("Playing")
        isPlaying = true

        mediaPlayer!!.setOnCompletionListener {
            stopAudio()
            player_header_status.setText("Finished")
        }
        seekBar.max = mediaPlayer!!.duration
        seekBarHandler = Handler()
        updateRunnable()

        seekBarHandler!!.postDelayed(updateSeekBar!!, 0)

    }

    private fun updateRunnable() {
        updateSeekBar = object : Runnable {
            override fun run() {
                seekBar.progress = mediaPlayer!!.currentPosition
                seekBarHandler!!.postDelayed(this, 500)
            }
        }
    }

    private fun pauseAudio() {
        media_play_button.setImageDrawable(
            activity?.resources?.getDrawable(
                R.drawable.player_play_btn,
                null
            )
        )
        mediaPlayer!!.pause()
        isPlaying = false
        seekBarHandler!!.removeCallbacks(updateSeekBar!!)
    }

    private fun resumeAudio() {
        media_play_button.setImageDrawable(
            activity?.resources?.getDrawable(
                R.drawable.player_pause_btn,
                null
            )
        )
        mediaPlayer!!.start()
        isPlaying = true
        updateRunnable()
        seekBarHandler!!.postDelayed(updateSeekBar!!, 0)
    }

    override fun onStop() {
        if (isPlaying) {
            stopAudio()
        }
        super.onStop()
    }


}
