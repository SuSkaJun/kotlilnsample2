package com.sy.sample.kotlin_example2

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var pageadapter : MainPageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        initPager()
        initBottomView()

        Glide.with(this)
                .load("https://cdn.pixabay.com/photo/2017/08/21/13/54/dogs-2665454__340.jpg")
                .into(header_img)

        fab.setOnClickListener { view ->

            val current_pos = view_pager.currentItem

            when (current_pos) {
                2 -> {
                    val fragment : RecyclerFragment = supportFragmentManager.findFragmentByTag(getFragmentTag(R.id.view_pager, current_pos)) as RecyclerFragment
                    if (null != fragment) fragment.refresh()
                }
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                val i = Intent(this, SettingActivity::class.java)
                startActivity(i)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun initPager() {

        val tab_names = ArrayList<String>()
        tab_names.add("home")
        tab_names.add("list")
        tab_names.add("recycler")
        tab_names.add("webview")

        pageadapter = MainPageAdapter(supportFragmentManager, tab_names)

        view_pager.adapter = pageadapter
        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                bottom_navigation_view.menu.getItem(position).isChecked = true
            }
        })
    }

    fun initBottomView() {
        bottom_navigation_view.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.tab_home -> {
                    view_pager.setCurrentItem(0)
                    true
                }
                R.id.tab_list -> {
                    view_pager.setCurrentItem(1)
                    true
                }
                R.id.tab_recycler -> {
                    view_pager.setCurrentItem(2)
                    true
                }
                R.id.tab_web -> {
                    view_pager.setCurrentItem(3)
                    true
                }
            }
            false
        })
    }

    fun getFragmentTag(viewPagerID : Int, pos : Int) : String {
        return "android:switcher:$viewPagerID:$pos"
    }

    inner class MainPageAdapter(fm : FragmentManager, private var items : ArrayList<String>) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            Log.d("pageadapter getItem", "idx : $position")
            when (position) {
                0 -> return HomeFragment.newInstance()
                1 -> return ListviewFragment.newInstance()
                2 -> return RecyclerFragment.newInstance()
                3 -> return WebViewFragment.newInstance()
            }
            return HomeFragment.newInstance()
        }

        override fun getCount(): Int {
            return items.size
        }

        override fun getPageTitle(position: Int): CharSequence {
            return items.get(position)
        }
    }
}
