package com.technipixl.filrouge.ui.adapters

import android.speech.tts.TextToSpeech
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.technipixl.filrouge.R
import com.technipixl.filrouge.databinding.JokeItemBinding
import com.technipixl.filrouge.network.models.JokeModel
import com.technipixl.filrouge.utils.IntentUtils
import java.util.*

class JokeAdapter: RecyclerView.Adapter<JokeAdapter.JokeViewHolder>() {

    var models: List<JokeModel>? = null

    private val textColorResources = mutableListOf(
        R.color.green_gradient_start,
        R.color.green_gradient_end,
        R.color.dark_blue,
        R.color.medium_blue,
        R.color.black
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        val binding = JokeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val colorResource = textColorResources.random()
        val color = binding.root.resources.getColor(colorResource, null)
        return JokeViewHolder(binding, color)
    }

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        models?.get(position)?.let { model ->
            holder.bind(model)
        } ?: kotlin.run {
            holder.unbind()
        }
    }

    override fun onViewRecycled(holder: JokeViewHolder) {
        super.onViewRecycled(holder)
        holder.unbind()
    }

    override fun getItemCount(): Int {
        return models?.size ?: 0
    }

    class JokeViewHolder(private var viewBinding: JokeItemBinding, @ColorInt val textColor: Int) :
        RecyclerView.ViewHolder(viewBinding.root),
        Bindable<JokeModel>,
        TextToSpeech.OnInitListener {

        private var ttsInitializationStatus = TextToSpeech.STOPPED

        override fun bind(model: JokeModel) {
            val tts = TextToSpeech(viewBinding.root.context, this)
            viewBinding.jokeTextView.setTextColor(textColor)
            viewBinding.jokeTextView.text = Html.fromHtml(model.joke, HtmlCompat.FROM_HTML_MODE_LEGACY)
            viewBinding.shareImageButton.setOnClickListener {
                IntentUtils.share(model.joke, viewBinding.root.context)
            }

            viewBinding.playImageButton.setOnClickListener {
                if(ttsInitializationStatus==TextToSpeech.SUCCESS) {
                    tts.language = Locale.US
                    tts.speak(model.joke, TextToSpeech.QUEUE_FLUSH, null, null)
                }
            }
        }

        override fun unbind() {
            viewBinding.jokeTextView.text = null
            viewBinding.shareImageButton.setOnClickListener(null)
        }

        override fun onInit(status: Int) {
            ttsInitializationStatus = status
        }
    }
}