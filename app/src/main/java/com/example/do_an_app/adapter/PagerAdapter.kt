package com.example.do_an_app.adapter


import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.do_an_app.fragment.fragmentstatus.*

class PagerAdapter(fragmentMng: FragmentManager, lifeCycle: Lifecycle): FragmentStateAdapter(fragmentMng, lifeCycle) {
    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> FragmentStatusWaiting()
            1 -> FragmentStatusConfirm()
            2 -> FragmentStatusExpired()
            3 -> FragmentStatusPayed()
            else -> FragmentStatusCancel()
        }
    }


}