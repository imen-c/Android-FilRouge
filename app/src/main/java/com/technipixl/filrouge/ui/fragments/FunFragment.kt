package com.technipixl.filrouge.ui.fragments

import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.technipixl.filrouge.databinding.FragmentFunBinding
import com.technipixl.filrouge.network.models.JokeModel
import com.technipixl.filrouge.network.models.JokeResponse
import com.technipixl.filrouge.network.services.JokeServiceImpl
import com.technipixl.filrouge.ui.adapters.JokeAdapter
import com.technipixl.filrouge.ui.decorations.JokeCardMarginItemDecoration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FunFragment : Fragment(),
    NetworkFragment<JokeResponse>,
    ListFragment<JokeModel, JokeAdapter> {

    private var binding: FragmentFunBinding? = null
    private val service by lazy { JokeServiceImpl() }

    override var adapter: JokeAdapter = JokeAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFunBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    override fun loadData() {
        displayLoader()

        if(!service.isNetworkAvailable(requireContext())) {
            displayError()
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getJokes()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    displayResults(response.body())
                } else {
                    displayError()
                }
            }
        }
    }

    override fun displayLoader() {
        binding?.loaderIndicator?.visibility = View.VISIBLE
    }

    override fun hideLoader() {
        binding?.loaderIndicator?.visibility = View.GONE
    }

    override fun displayError() {
        Toast.makeText(requireContext(), "Error loading jokes", Toast.LENGTH_LONG).show()
        hideLoader()
    }

    override fun displayResults(response: JokeResponse?) {
        response?.let {
            setupRecyclerView(it.jokes).also {
                binding?.topImageView?.visibility = View.VISIBLE
            }
        }
        hideLoader()
    }

    override fun setupRecyclerView(models: List<JokeModel>) {
        adapter.models = models
        binding?.recyclerView?.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL,false)
        binding?.recyclerView?.setOnScrollChangeListener { _, _, _, _, _ ->
            (binding?.recyclerView?.layoutManager as? LinearLayoutManager)?.let { layoutManager ->
                binding?.topImageView?.visibility =
                    if (layoutManager.findFirstCompletelyVisibleItemPosition() == 0) View.VISIBLE
                    else View.INVISIBLE
            }
        }
        binding?.recyclerView?.adapter = adapter
        binding?.recyclerView?.addItemDecoration(
            JokeCardMarginItemDecoration(
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 18f, requireContext().resources.displayMetrics).toInt(),
                models.size)
        )
        adapter.notifyDataSetChanged()
    }

    override fun displayDetail(model: JokeModel) {
        TODO("Not yet implemented")
    }
}