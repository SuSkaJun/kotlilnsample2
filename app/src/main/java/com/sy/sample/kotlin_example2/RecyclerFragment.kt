package com.sy.sample.kotlin_example2

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by SuYa on 2017. 9. 6..
 */

class RecyclerFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_recyclerview, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() : RecyclerFragment {
            val fragment = RecyclerFragment()
            val arg = Bundle()
            fragment.arguments = arg
            return fragment
        }

    }

}