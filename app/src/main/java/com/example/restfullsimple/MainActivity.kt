package com.example.restfullsimple
import android.graphics.Color
import android.graphics.drawable.LayerDrawable
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
        val percentbox = binding.percentbox
        val filler = binding.filler
        filler.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                val percent = when{
                    progress <= 0 -> 0
                    progress >= filler.max -> 100/1
                    else -> 100/(filler.max -progress)
                }
                viewModel.percent(percent)
                percentbox.text = Editable.Factory.getInstance().newEditable(viewModel.display())
                val color = when {
                    progress <= 1 -> Color.RED
                    progress <= 2 -> Color.YELLOW
                    else -> Color.GREEN
                }
                val fore = ((seekBar?.progressDrawable ?: return  ) as LayerDrawable).findDrawableByLayerId(android.R.id.progress)
                fore.setTint(color)

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        binding.output?.text = viewModel.output()
    }
}

