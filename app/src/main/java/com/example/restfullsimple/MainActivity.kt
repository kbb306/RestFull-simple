package com.example.restfullsimple
import android.os.Build
import android.os.Bundle
import android.text.Editable
import androidx.appcompat.app.AppCompatActivity
import android.widget.SeekBar
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.example.restfullsimple.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: RestFullViewModel by viewModels()
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.filler.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                viewModel.percent(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        binding.percentbox.text = Editable.Factory.getInstance().newEditable(viewModel.display())
        binding.output?.text = viewModel.output()
    }
}

