package com.technipixl.filrouge.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.technipixl.filrouge.R
import com.technipixl.filrouge.ui.fragments.HomeCardFragment

class HomeAdapter(activity: FragmentActivity, private val listener: HomeCardFragment.HomeCardClickListener) : FragmentStateAdapter(activity) {

    private val fragments = mutableListOf<HomeCardFragment>().apply {
        add(HomeCardFragment.newInstance(R.drawable.wolf, activity.getString(R.string.eat_card_title), 1, listener))
        add(HomeCardFragment.newInstance(R.drawable.`fun`, activity.getString(R.string.fun_card_title), 2, listener))
        add(HomeCardFragment.newInstance(R.drawable.run, activity.getString(R.string.move_card_title), 3, listener))
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}