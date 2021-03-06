package com.sy.sample.kotlin_example2

import android.graphics.Rect
import android.os.Bundle
import android.os.CountDownTimer
import android.support.transition.Explode
import android.support.transition.Transition
import android.support.transition.TransitionManager
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sy.sample.kotlin_example2.adapter.RecyclerAdapter
import com.sy.sample.kotlin_example2.model.DataModel
import com.sy.sample.kotlin_example2.presenter.RecyclerContract
import com.sy.sample.kotlin_example2.presenter.RecyclerPresenter
import kotlinx.android.synthetic.main.fragment_recyclerview.*

/**
 * Created by SuYa on 2017. 9. 6..
 */

class RecyclerFragment : Fragment(), RecyclerContract.View {

    private lateinit var adapter : RecyclerAdapter
    private lateinit var mpresenter : RecyclerContract.Presenter
    private var isUsedAnimated : Boolean = true

    private lateinit var countdown : CountDownTimer

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_recyclerview, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        countdown = object : CountDownTimer(1000, 1000) {
            override fun onFinish() {
                isUsedAnimated = true
            }

            override fun onTick(p0: Long) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }

        RecyclerPresenter(this)

        view.let {
            val layoutmanager = GridLayoutManager(activity, 2)
            recycler_view.layoutManager = layoutmanager
            adapter = RecyclerAdapter(context, ArrayList<DataModel.ListItem>())

            adapter.setClickListener(object : RecyclerAdapter.ItemClickListener{
                override fun onItemClickListener(item: DataModel.ListItem) {
                    isUsedAnimated = false
                    val viewRect = Rect()
                    view!!.getGlobalVisibleRect(viewRect)

                    val explode : Transition = Explode()
                    explode.epicenterCallback = object :Transition.EpicenterCallback() {
                        override fun onGetEpicenter(transition: Transition): Rect {
                            return viewRect
                        }
                    }

                    explode.duration = 1000
                    TransitionManager.beginDelayedTransition(recycler_view, explode)
                    countdown.start()
                    adapter.clear()
                }
            })

            recycler_view.adapter = adapter
        }
        mpresenter.start()
    }


    override fun updateView(data: ArrayList<DataModel.ListItem>) {
        if (true and isUsedAnimated)
            adapter.addAllItems(data)
    }

    override fun showToast(value: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setPresenter(presenter: RecyclerContract.Presenter) {
        this.mpresenter = presenter
    }

    override fun refresh() {
        this.mpresenter.loadItems()
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