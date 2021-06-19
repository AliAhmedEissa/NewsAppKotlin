package com.example.seamlabstask.UI

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.seamlabstask.R
import com.example.seamlabstask.Utils
import com.example.seamlabstask.ViewModel
import kotlinx.android.synthetic.main.fragment_article_click.view.*

class ArticleOnClick : Fragment() {

    lateinit var viewModel: ViewModel
    lateinit var url: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val viewRef = inflater.inflate(R.layout.fragment_article_click, container, false)

        subscribeDate(viewRef)

        return viewRef
    }

    private fun subscribeDate(viewRef: View) {

        ViewModel.articlesItem.let {
            if (it != null) {
                url = it.url
                viewRef.article_name.text = it.source!!.name
                viewRef.articletitle.text = it.title
                viewRef.articledate.text = Utils.formatDate(it.publishedAt)
                Glide.with(requireContext())
                    .load(it.urlToImage)
                    .into(viewRef.articleimage)
                viewRef.article_content.text = it.content
                Log.d("Content", it.content)
            }
        }


        viewRef.articleUrl.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse(url)
            startActivity(openURL)
        }

    }
}