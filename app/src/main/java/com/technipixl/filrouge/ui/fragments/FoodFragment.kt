package com.technipixl.filrouge.ui.fragments

import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.technipixl.filrouge.databinding.FragmentFoodBinding
import com.technipixl.filrouge.network.models.BusinessModel
import com.technipixl.filrouge.network.models.BusinessResponse
import com.technipixl.filrouge.network.services.BusinessServiceImpl
import com.technipixl.filrouge.ui.adapters.BusinessAdapter
import com.technipixl.filrouge.ui.decorations.BusinessCardMarginItemDecoration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FoodFragment : Fragment(),
    NetworkFragment<BusinessResponse>,
    ListFragment<BusinessModel, BusinessAdapter> {

    private var binding: FragmentFoodBinding? = null
    private val service by lazy { BusinessServiceImpl() }

    override var adapter: BusinessAdapter = BusinessAdapter(object: (BusinessModel) -> Unit {
        override fun invoke(model: BusinessModel) {
            displayDetail(model)
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFoodBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun displayLoader() {
        binding?.loaderIndicator?.visibility = View.VISIBLE
    }

    override fun hideLoader() {
        binding?.loaderIndicator?.visibility = View.GONE
    }

    override fun loadData() {
        displayLoader()

        if(!service.isNetworkAvailable(requireContext())) {
            displayError()
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.search(
                BusinessServiceImpl.SearchTerms.FOOD.value,
                BusinessServiceImpl.defaultCoordinates.latitude,
                BusinessServiceImpl.defaultCoordinates.longitude
            )
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    displayResults(response.body())
                } else {
                    displayError()
                }
            }
        }
    }

    override fun displayError() {
        Toast.makeText(requireContext(), "Error loading food businesses", Toast.LENGTH_LONG).show()
        hideLoader()
    }

    override fun displayResults(response: BusinessResponse?) {
        response?.let {
            setupRecyclerView(it.businesses)
        }
        hideLoader()
    }

    override fun displayDetail(model: BusinessModel) {
        val direction = FoodFragmentDirections.actionFoodFragmentToBusinessDetailFragment(model)
        findNavController().navigate(direction)
    }

    override fun setupRecyclerView(models: List<BusinessModel>) {
        adapter.models = models
        binding?.foodRecyclerView?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding?.foodRecyclerView?.adapter = adapter
        binding?.foodRecyclerView?.addItemDecoration(
            BusinessCardMarginItemDecoration(
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 18f, requireContext().resources.displayMetrics).toInt(),
                models.size)
        )
        adapter.notifyDataSetChanged()
    }
}