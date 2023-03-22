package com.trinetra.cryptogo.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.trinetra.cryptogo.fragments.toplossgainFragment

class TopLossGainPagerAdapter(fragment : Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = toplossgainFragment()
        val bundle = Bundle()
        bundle.putInt("position", position)

        fragment.arguments = bundle

        return fragment
    }
}