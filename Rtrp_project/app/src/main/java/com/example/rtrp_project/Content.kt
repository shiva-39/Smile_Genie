package com.example.rtrp_project

import android.R
import android.app.ProgressDialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.example.rtrp_project.databinding.ActivityContentBinding
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.io.IOException
import kotlin.math.max


class Content : AppCompatActivity() {
    private lateinit var binding : ActivityContentBinding
    lateinit var storageReference: StorageReference
    lateinit var progessDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var selectedCard = intent.getStringExtra("selected_card")
        val maxSmilePercentage = intent.getFloatExtra("max_smile_percentage", 0f)

        progessDialog = ProgressDialog(this)


        storageReference = FirebaseStorage.getInstance().getReference(selectedCard+"/"+".png")

        if (maxSmilePercentage >= 80) {
            //happy
            val storageReference = FirebaseStorage.getInstance().getReference("meme/memevid.mp4")
            var localFile: File? = null
            try {
                localFile = File.createTempFile("meme", "mp4")
            } catch (e: IOException) {
                e.printStackTrace()
            }

            if (localFile != null) {
                storageReference.getFile(localFile)
                    .addOnSuccessListener { taskSnapshot: FileDownloadTask.TaskSnapshot? ->
                        // Video downloaded successfully, now play it in VideoView
                        //val videoView = findViewById<VideoView>(R.id.videoView)
                        binding.videoView.setVideoPath(localFile.absolutePath)
                        binding.videoView.start()
                        binding.videoView.setOnCompletionListener { mp ->
                            // Video playback completed, stop the video playback and hide the VideoView
                            binding.videoView.stopPlayback()
                            binding.videoView.visibility = View.GONE
                        }
                    }
                    .addOnFailureListener { exception: Exception? -> }
            }
        }

        else if(maxSmilePercentage<80 && maxSmilePercentage>50){
            //neutral
            var text: String? = null
            var path:String?=null
            if(selectedCard=="Telugu"){
                selectedCard= "Telugu"
                val range = (1..2)
                text = range.random().toString()
                path= "Telugu"
            }
            else if(selectedCard=="Hindi") {
                selectedCard = "Hindi"
                val range = (1..2)
                text = range.random().toString()
                path = "Hindi"
            }
            else{
                selectedCard = "English"
                val range = (1..2)
                text = range.random().toString()
                path = "English"
            }
            //binding.textView.text=selectedCard+" "+path
            //Toast.makeText(this,selectedCard,Toast.LENGTH_SHORT).show()
            storageReference  = FirebaseStorage.getInstance().getReference(selectedCard+"/"+path+text+".png")
            try {
                val localFile = File.createTempFile("tempfile", ".png")
                storageReference.getFile(localFile)
                    .addOnSuccessListener {
                        if(progessDialog.isShowing()){
                            progessDialog.dismiss()
                        }
                        val bitmap : Bitmap
                        bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                        binding.imageView.setImageBitmap(bitmap)
                    }
                    .addOnFailureListener{

                        if(progessDialog.isShowing()){
                            progessDialog.dismiss()
                        }
                        Toast.makeText(this,"Failed to retrieve image from firebase", Toast.LENGTH_SHORT).show()
                    }

            } catch (e: IOException) {
                e.printStackTrace()
            }

        }

        else{
            //sad
            var text: String? = null
            var path:String?=null
            if(selectedCard=="Telugu"){
                selectedCard = "Telugu"
                val range = (1..2)
                text = range.random().toString()
                path= "Telugu"
            }
            else if(selectedCard=="Hindi") {
                selectedCard = "Hindi"
                val range = (1..2)
                text = range.random().toString()
                path = "Hindi"
            }
            else{
                selectedCard = "English"
                val range = (1..2)
                text = range.random().toString()
                path = "English"
            }
            storageReference  = FirebaseStorage.getInstance().getReference(selectedCard+"/"+path+text+".png")
            try {
                val localFile = File.createTempFile("tempfile", ".png")
                storageReference.getFile(localFile)
                    .addOnSuccessListener {
                        if(progessDialog.isShowing()){
                            progessDialog.dismiss()
                        }
                        val bitmap : Bitmap
                        bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                        binding.imageView.setImageBitmap(bitmap)
                    }
                    .addOnFailureListener{

                        if(progessDialog.isShowing()){
                            progessDialog.dismiss()
                        }
                        Toast.makeText(this,"Failed to retireve image from firebase", Toast.LENGTH_SHORT).show()
                    }

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }




//        binding.enterButton.setOnClickListener {
//            progessDialog.create()
//            progessDialog.setMessage("Fetching Image...")
//            progessDialog.setCancelable(false)
//            progessDialog.show()

//        binding.textViewSelectedCard.text = "Selected Card: $selectedCard"
//        binding.textViewMaxSmilePercentage.text = "Max Smile Percentage: $maxSmilePercentage"
    }
}