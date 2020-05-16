package com.example.herbario_nacional.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.herbario_nacional.ui.Fragments.FungiFragment
import com.example.herbario_nacional.ui.Fragments.PlantsFragment

class PagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                PlantsFragment()
            }
            else -> {
                return FungiFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Plantas"
            else -> {
                return "Hongos"
            }
        }
    }
}