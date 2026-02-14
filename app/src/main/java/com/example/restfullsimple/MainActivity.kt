package com.example.restfullsimple
import android.graphics.Color
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import android.widget.SeekBar
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.example.restfullsimple.databinding.ActivityMainBinding
class MainActivity : AppCompatActivity() {
    private val BOX_KEY = "PERCENT"
    private val SEEK_KEY = "SEEK"
    private val RESULT_KEY = "RESULT"
    private lateinit var binding: ActivityMainBinding
    private val viewModel: RestFullViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val percentbox = binding.percentbox
        val filler = binding.filler
        savedInstanceState?.let {
            percentbox.text = Editable.Factory.getInstance().newEditable(savedInstanceState.getInt(BOX_KEY).toString())
            filler.progress = savedInstanceState.getInt(SEEK_KEY)
            binding.output.text = savedInstanceState.getString(RESULT_KEY)
        }
        filler.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                val percent = progress * 20
                viewModel.percent(percent)
                percentbox.text = Editable.Factory.getInstance().newEditable(viewModel.getPer().toString())
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

        percentbox.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val input = s.toString().toInt()
                val num = when {
                    input <= 100 -> input
                    else -> 100
                }
                viewModel.percent(num)
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
            }

        })

        binding.output.text = viewModel.output()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(BOX_KEY,viewModel.getPer())
        outState.putInt(SEEK_KEY,viewModel.getPer()/20)
        outState.putString(RESULT_KEY,viewModel.output())

    }
}

