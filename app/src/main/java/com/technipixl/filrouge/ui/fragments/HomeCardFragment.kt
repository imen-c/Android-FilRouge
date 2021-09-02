package com.technipixl.filrouge.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.technipixl.filrouge.R
import com.technipixl.filrouge.databinding.FragmentHomeCardBinding

class HomeCardFragment : Fragment() {

    interface HomeCardClickListener {
        fun onCardClicked(menuIndex: Int)
    }

    private lateinit var binding: FragmentHomeCardBinding
    private lateinit var listener: HomeCardClickListener
    private var image: Int? = null
    private var title: String? = null
    private var menuIndex: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            image = it.getInt(ARG_IMAGE)
            title = it.getString(ARG_TITLE)
            menuIndex = it.getInt(ARG_MENU_INDEX)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentHomeCardBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        image?.let {
            view.findViewById<ImageView>(R.id.imageView).setImageResource(it)
        }
        title?.let {
            view.findViewById<TextView>(R.id.titleTextView).text = it
        }

        view.setOnClickListener {
            listener.onCardClicked(menuIndex ?: 0)
        }
    }

    companion object {
        private const val ARG_IMAGE = "image"
        private const val ARG_TITLE = "title"
        private const val ARG_MENU_INDEX = "index"

        @JvmStatic
        fun newInstance(image: Int, title: String, menuIndex: Int, listener: HomeCardClickListener) =
                HomeCardFragment().apply {
                    this.listener = listener
                    arguments = Bundle().apply {
                        putInt(ARG_IMAGE, image)
                        putString(ARG_TITLE, title)
                        putInt(ARG_MENU_INDEX, menuIndex)
                    }
                }
    }
}