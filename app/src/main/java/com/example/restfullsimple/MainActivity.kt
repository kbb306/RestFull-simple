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
        val battery = binding.attery
        battery.setImageResource(R.drawable.battfill6_foreground)
        battery.setColorFilter(Color.GREEN)

        percentbox.setText(viewModel.getPer().toString())
        filler.progress = viewModel.getPer()/20

        savedInstanceState?.let {
            percentbox.setText(savedInstanceState.getInt(BOX_KEY).toString())
            filler.progress = savedInstanceState.getInt(SEEK_KEY)
            binding.output.text = savedInstanceState.getString(RESULT_KEY)
        }

        filler.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                if(fromUser){
                val percent = progress * 20
                viewModel.percent(percent)
                }

                val newText = viewModel.getPer().toString()
                if (percentbox.text.toString() != newText) {
                    percentbox.setText(newText)
                }
                updateOut()
                val color = when {
                    progress == 0 -> Color.GRAY
                    progress <= 1 -> Color.RED
                    progress <= 2 -> Color.YELLOW
                    else -> Color.GREEN
                }

                val img = when {
                    progress == 0 -> R.drawable.battfil0_foreground
                    progress == 1 -> R.drawable.battfil1_foreground
                    progress == 2 -> R.drawable.battfill2_foreground
                    progress == 3 -> R.drawable.battfill3_foreground
                    progress == 4 -> R.drawable.battfill4_foreground
                    progress == 5 -> R.drawable.battfill6_foreground
                    else -> R.drawable.battfillelse_foreground
                }
                battery.setImageResource(img)
                battery.setColorFilter(color)

            }


            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        percentbox.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val instring = s.toString()
                if (instring.isEmpty()) {return}
                val input = instring.toIntOrNull() ?: 0
                val num = when {
                    input <= 100 -> input
                    else -> 100
                }
                if (num != viewModel.getPer()) {
                    viewModel.percent(num)
                    filler.progress = num / 20
                }
                updateOut()
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

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(BOX_KEY,viewModel.getPer())
        outState.putInt(SEEK_KEY,viewModel.getPer()/20)
        outState.putString(RESULT_KEY,viewModel.output())

    }

    fun updateOut() {
        binding.output.text = viewModel.output()
    }
}

