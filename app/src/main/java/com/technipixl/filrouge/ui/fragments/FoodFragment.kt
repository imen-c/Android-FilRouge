package com.technipixl.filrouge.ui.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory.*
import com.squareup.picasso.Picasso
import com.technipixl.filrouge.R
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


    private
    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 123
    }

    private lateinit var map :GoogleMap
    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        map.mapType = GoogleMap.MAP_TYPE_NORMAL

        tracklocation()
        val base = LatLng(50.59,5.56)
        //val  maxZoom : Float = 15.0f
        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(position))
        //CameraUpdateFactory.newLatLngZoom(base, 16f)
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(base,10.0f))
        //googleMap.setMinZoomPreference(15.0f)
       // googleMap.setMaxZoomPreference(3.0f)


    }

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
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapsFragment) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
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

                    response.body()?.businesses?.forEach{
                       // listCoordonne.add((it))
                        val imageUrl  = it.imageUrl
                       // val bmp = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream())

                      // Picasso.get().load(imageUrl)

                        val position = LatLng(it.coordinatesModel.latitude,it.coordinatesModel.longitude)
                        map.addMarker(
                            MarkerOptions()
                            .position(position)
                            .title("Marker")
                               //.icon(BitmapDescriptorFactory.fromBitmap(bmp))
                        )


                    }


                } else {
                    displayError()
                }
            }
        }
    }

    private fun tracklocation(){
        if (ActivityCompat.checkSelfPermission (requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {


            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ), LOCATION_PERMISSION_REQUEST_CODE
            )

            return
        }
        map.isMyLocationEnabled = true
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

