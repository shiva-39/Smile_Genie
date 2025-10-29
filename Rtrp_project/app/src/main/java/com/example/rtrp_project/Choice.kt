package com.example.rtrp_project

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rtrp_project.databinding.ActivityChoiceBinding
import kotlin.math.max

class Choice : AppCompatActivity() {
    private lateinit var binding:ActivityChoiceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChoiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var maxSmilePercentage:Float? = null
        val smilePercentages = intent.getFloatArrayExtra("smile_percentages")
        if (smilePercentages != null && smilePercentages.isNotEmpty()) {
            maxSmilePercentage = smilePercentages.maxOrNull() ?: 0f
            val formattedMaxSmilePercentage = String.format("%.1f", maxSmilePercentage)
            Toast.makeText(this, maxSmilePercentage.toString(),Toast.LENGTH_SHORT).show()
            //binding.textView.text = "Max Smile Percentage: $formattedMaxSmilePercentage"
        }
        else {
            //binding.textView.text = "No smile percentages found"
            Toast.makeText(this, "No smile percentages found",Toast.LENGTH_SHORT).show()
        }
        binding.teluguTxt.setOnClickListener {
            if (maxSmilePercentage != null) {
                launchContentActivity("Telugu", maxSmilePercentage)
            } else {
                // Handle the case where maxSmilePercentage is null, maybe show a message or do something else
                Toast.makeText(this,"Smile percentage is null",Toast.LENGTH_SHORT).show()
            }
        }

        binding.hindiTxt.setOnClickListener {
            if (maxSmilePercentage != null) {
                launchContentActivity("Hindi", maxSmilePercentage)
            } else {
                // Handle the case where maxSmilePercentage is null, maybe show a message or do something else
                Toast.makeText(this,"Smile percentage is null",Toast.LENGTH_SHORT).show()
            }
        }

        binding.englishTxt.setOnClickListener {
            if (maxSmilePercentage != null) {
                launchContentActivity("English", maxSmilePercentage)
            } else {
                // Handle the case where maxSmilePercentage is null, maybe show a message or do something else
                Toast.makeText(this,"Smile percentage is null",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun launchContentActivity(selectedCard: String, maxSmilePercentage: Float) {
        val intent = Intent(this, Content::class.java)
        intent.putExtra("selected_card", selectedCard)
        intent.putExtra("max_smile_percentage", maxSmilePercentage)
        //Toast.makeText(this,selectedCard,Toast.LENGTH_SHORT).show()
        startActivity(intent)
    }
}