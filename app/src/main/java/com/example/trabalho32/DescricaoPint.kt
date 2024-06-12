package com.example.trabalho32

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.speech.tts.TextToSpeech
import com.bumptech.glide.Glide
import com.example.trabalho32.databinding.FragmentDescricaoPintBinding
import com.example.trabalho32.model.Obras
import java.util.Locale

class DescricaoPint : Fragment(), TextToSpeech.OnInitListener {

    private lateinit var binding: FragmentDescricaoPintBinding
    private lateinit var textToSpeech: TextToSpeech
    private var obra: Obras? = null

    companion object {
        private const val ARG_OBRA = "obra"

        fun newInstance(obra: Obras): DescricaoPint {
            val fragment = DescricaoPint()
            val args = Bundle()
            args.putSerializable(ARG_OBRA, obra)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            obra = it.getSerializable(ARG_OBRA) as? Obras
        }
        textToSpeech = TextToSpeech(context, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDescricaoPintBinding.inflate(inflater, container, false)
        val view = binding.root

        view.findViewById<Button>(R.id.btvoltar2).setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        val audiodescricaoImageView = view.findViewById<ImageView>(R.id.audiodescricao)
        val descriptionTextView = view.findViewById<TextView>(R.id.descriptionTextView)
        val autorTextView = view.findViewById<TextView>(R.id.autorTextView)

        obra?.let {
            binding.nameTextView.text = it.nome
            descriptionTextView.text = it.descricao
            autorTextView.text = it.autor
            Glide.with(this)
                .load(it.imagem)
                .into(binding.artworkImageView)
        }

        audiodescricaoImageView.setOnClickListener {
            val text = descriptionTextView.text.toString()
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        }

        return view
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val portugues = Locale("pt", "BR")
            textToSpeech.language = portugues
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        textToSpeech.shutdown()
    }
}
