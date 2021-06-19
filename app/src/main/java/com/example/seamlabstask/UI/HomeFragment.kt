package com.example.seamlabstask.UI

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.seamlabstask.Models.ArticlesItem
import com.example.seamlabstask.Models.SourcesItem
import com.example.seamlabstask.R
import com.example.seamlabstask.Services.ConnectivityReceiver
import com.example.seamlabstask.UI.Adapters.NewsAdapter
import com.example.seamlabstask.ViewModel
import com.example.seamlabstask.ViewModel.Companion.articlesItem
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : Fragment(), ConnectivityReceiver.ConnectivityReceiverListner,
    NewsAdapter.OnClickListner {

    lateinit var viewRef: View
    lateinit var viewModel: ViewModel

    lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewRef = inflater.inflate(R.layout.fragment_home, container, false)

        initView(viewRef)

        subscribeToLiveData()

        return viewRef
    }

    private fun initView(viewRef: View) {

        viewRef.recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            newsAdapter = NewsAdapter()
            newsAdapter.setOnClickListner(this@HomeFragment)
            adapter = newsAdapter
            swipeToShare()
        }

        viewRef.refresh.setOnRefreshListener {
            viewModel.getNewsSources()
            viewRef.refresh.isRefreshing = false
        }


        viewRef.toolBar.setOnMenuItemClickListener(object : Toolbar.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {

                if (item!!.itemId == R.id.Search) {
                    val searchView = item.actionView as SearchView
                    searchView.queryHint = "Search Here"

                    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            return false
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            newsAdapter.filter!!.filter(newText)
                            return false
                        }
                    })
                }
                return true
            }
        })
    }

    private fun subscribeToLiveData() { // get Data from ViewModel

        viewModel.getFromCache()
        viewModel.getNewsSources()

        viewModel.sourceList.observe(viewLifecycleOwner, {
            initTabLayout(it)

        })

        viewModel.newsList.observe(viewLifecycleOwner, {
            if (it != null) {
                newsAdapter.updateData(it)
                viewRef.progress_bar.visibility = View.GONE
            }

        })

    }


    private fun initTabLayout(sourcesItems: List<SourcesItem>) {
        for (sources in sourcesItems) {
            val tab: TabLayout.Tab = tablayout.newTab()
            tab.text = sources.name
            tab.tag = sources
            tablayout.addTab(tab)
        }

        tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val sourcesItem = tab!!.tag as SourcesItem?
                Log.d("SOURCES", "" + sourcesItem!!.id)

                viewModel.getNewsSourcesbyID(sourcesItem!!.id)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                val sourcesItem = tab!!.tag as SourcesItem?
                viewModel.getNewsSourcesbyID(sourcesItem!!.id)
            }

        })
        if (tablayout.tabCount != 0) {
            viewRef.progress_bar.visibility = View.VISIBLE
            tablayout.getTabAt(0)!!.select()
        }
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showNetworkMessage(isConnected)
    }

    private fun showNetworkMessage(isConnected: Boolean) {
        val snackbar = Snackbar.make(requireView(), "You Are Offline", Snackbar.LENGTH_LONG)
        if (!isConnected) {
            snackbar.show()
        } else {
            snackbar.dismiss()
            viewModel.getNewsSources()

        }
    }

    override fun onResume() {
        super.onResume()
        ConnectivityReceiver.connectivityReceiverListner = this
    }

    private fun swipeToShare() {
        val itemSwipe = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                shareNews(viewHolder.adapterPosition)
            }

        }
        val swap = ItemTouchHelper(itemSwipe)
        swap.attachToRecyclerView(viewRef.recycler_view)

    }

    fun getUrl(position: Int): String {
        return newsAdapter.articles!![position].url
    }

    fun shareNews(position: Int) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(
            Intent.EXTRA_TEXT, (getUrl(position))
        )
        intent.type = "text/plain"
        requireContext().startActivity(Intent.createChooser(intent, "Send To"))
    }

    override fun onArticleClick(article: ArticlesItem, Position: Int) {
        articlesItem = article
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToArticleClick())
    }
}