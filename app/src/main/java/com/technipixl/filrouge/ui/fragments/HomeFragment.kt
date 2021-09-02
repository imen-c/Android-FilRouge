package com.technipixl.filrouge.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.tabs.TabLayoutMediator
import com.technipixl.filrouge.R
import com.technipixl.filrouge.databinding.FragmentHomeBinding
import com.technipixl.filrouge.ui.adapters.HomeAdapter
import com.technipixl.filrouge.ui.activities.HomeActivity

class HomeFragment : Fragment(), HomeCardFragment.HomeCardClickListener {

    lateinit var binding: FragmentHomeBinding

    private val onPageChangeCallback: OnPageChangeCallback = object: OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            (activity as? HomeActivity)?.let {
                it.selectedHomeCardIndex = position
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.viewPager.unregisterOnPageChangeCallback(onPageChangeCallback)
    }

    override fun onCardClicked(menuIndex: Int) {
        when (menuIndex) {
            1 -> findNavController().navigate(R.id.foodFragment)
            2 -> findNavController().navigate(R.id.funFragment)
            3 -> findNavController().navigate(R.id.moveFragment)
        }
    }

    private fun setupViewPager() {
        val pagerAdapter = HomeAdapter(requireActivity(), this)
        binding.viewPager.clipToPadding = false
        binding.viewPager.setPadding(120, 0, 120, 0)
        binding.viewPager.adapter = pagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
           //TabLayout selected @ position
        }.attach()

        binding.viewPager.registerOnPageChangeCallback(onPageChangeCallback)
    }
}