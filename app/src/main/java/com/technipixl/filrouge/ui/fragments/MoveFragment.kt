package com.technipixl.filrouge.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.GoogleMap
import com.technipixl.filrouge.R
import com.technipixl.filrouge.databinding.FragmentFoodBinding
import com.technipixl.filrouge.databinding.FragmentMoveBinding
import com.technipixl.filrouge.network.models.BusinessModel
import com.technipixl.filrouge.network.models.BusinessResponse
import com.technipixl.filrouge.network.services.BusinessServiceImpl
import com.technipixl.filrouge.ui.adapters.BusinessAdapter

class MoveFragment : Fragment() ,  NetworkFragment<BusinessResponse>,
    ListFragment<BusinessModel, BusinessAdapter>{
    private lateinit var map : GoogleMap
    private var binding: FragmentMoveBinding? = null
    private val service by lazy { BusinessServiceImpl() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_move, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MoveFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override var adapter: BusinessAdapter
        get() = TODO("Not yet implemented")
        set(value) {}

    override fun setupRecyclerView(models: List<BusinessModel>) {
        TODO("Not yet implemented")
    }

    override fun displayDetail(model: BusinessModel) {
        TODO("Not yet implemented")
    }

    override fun loadData() {
        TODO("Not yet implemented")
    }

    override fun displayError() {
        TODO("Not yet implemented")
    }

    override fun displayResults(response: BusinessResponse?) {
        TODO("Not yet implemented")
    }

    override fun displayLoader() {
        TODO("Not yet implemented")
    }

    override fun hideLoader() {
        TODO("Not yet implemented")
    }
}