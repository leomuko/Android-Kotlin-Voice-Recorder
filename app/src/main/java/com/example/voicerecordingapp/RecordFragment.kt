package com.example.voicerecordingapp

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_record.*
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class RecordFragment : Fragment() {


    val RECORDPERMISSION = Manifest.permission.RECORD_AUDIO
    val PERMISSIONCODE = 21
    var mediaRecorder: MediaRecorder? = null
    var recordFile: String? = null
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
                stopRecording()
                record_button.setImageDrawable(resources.getDrawable(R.drawable.record_btn_stopped, null))
                isRecording = false
            }else{
                if(checkRecordingPermission()) {

                    startRecording()
                    record_button.setImageDrawable(
                        resources.getDrawable(
                            R.drawable.record_btn_recording,
                            null
                        )
                    )
                    isRecording = true
                }
            }
        }
    }

    private fun startRecording() {
        record_timer.setBase(SystemClock.elapsedRealtime())
        record_timer.start()
        val recordPath : String = activity?.getExternalFilesDir("/")!!.absolutePath
        val formatter : SimpleDateFormat = SimpleDateFormat("yyyy_MM_dd_hh_mm_ss", Locale.ROOT)
        val now: Date = Date()
        recordFile = "Recording ${formatter.format(now)}.3gp"

        record_status_text.setText("Recording audionote")

        mediaRecorder = MediaRecorder()
        mediaRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        mediaRecorder!!.setOutputFile("$recordPath/$recordFile")
        mediaRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

        try {
            mediaRecorder!!.prepare()
        }catch (e: IOException){
            e.printStackTrace()
        }
        mediaRecorder!!.start()
        Log.d("RecordFragment", "recording has started")
    }

    private fun stopRecording() {
        record_timer.base
        record_timer.stop()
        record_status_text.setText("Recording saved as  ${recordFile}")
        mediaRecorder!!.stop()
        mediaRecorder!!.release()
        mediaRecorder = null
        Log.d("RecordFragment", "recording has stopped")
    }

    private fun checkRecordingPermission(): Boolean {
        if(context?.let { ActivityCompat.checkSelfPermission(it, Manifest.permission.RECORD_AUDIO) } == PackageManager.PERMISSION_GRANTED){
            return true
        }else{
            activity?.let { ActivityCompat.requestPermissions(it, arrayOf(RECORDPERMISSION), PERMISSIONCODE) }
            return false
        }
    }


}
