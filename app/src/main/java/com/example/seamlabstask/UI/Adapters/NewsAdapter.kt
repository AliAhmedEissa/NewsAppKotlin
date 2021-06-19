package com.example.seamlabstask.UI.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.seamlabstask.Models.ArticlesItem
import com.example.seamlabstask.R
import com.example.seamlabstask.Utils
import java.util.*
import kotlin.collections.ArrayList


class NewsAdapter() : RecyclerView.Adapter<NewsAdapter.ViewHolder>(),
    Filterable {
    var articles: ArrayList<ArticlesItem>? = null
    var articlesItemListFull: List<ArticlesItem>? = null

    private lateinit var onArticleClick: OnClickListner

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = articles!![position]
        holder.title.text = item.title
        holder.content.text = item.description

        holder.date.text = Utils.formatDate(item.publishedAt)
        if (item.source != null) holder.sourceName.text = item.source!!.name
        Glide.with(holder.itemView)
            .load(item.urlToImage)
            .into(holder.image)

        if (onArticleClick != null) {
            holder.articleCard.setOnClickListener {
                onArticleClick.onArticleClick(item, position)
            }
        }

    }

    override fun getItemCount(): Int {
        return if (articles == null) 0 else articles!!.size
    }

    interface OnClickListner {
        fun onArticleClick(article: ArticlesItem, Position: Int)
    }

    fun setOnClickListner(onArticleClick: NewsAdapter.OnClickListner) {
        this.onArticleClick = onArticleClick
    }


    fun updateData(items: List<ArticlesItem>) {
        this.articles = items as ArrayList<ArticlesItem>
        if (items != null) {
            articlesItemListFull = ArrayList(items)
        }
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter? {
        return exampleFilter
    }

    var exampleFilter: Filter = object : Filter() {
        override fun performFiltering(charSequence: CharSequence?): FilterResults? {
            val filteredList: MutableList<ArticlesItem> = ArrayList()
            if (charSequence == null || charSequence.length == 0) {
                filteredList.addAll(articlesItemListFull!!)
            } else {
                val filterPattern =
                    charSequence.toString().lowercase(Locale.getDefault()).trim { it <= ' ' }
                for (item in articlesItemListFull!!) {
                    if (item.title.lowercase(Locale.getDefault()).contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }
            val filterResults = FilterResults()
            filterResults.values = filteredList
            return filterResults
        }

        override fun publishResults(
            charSequence: CharSequence?,
            filterResults: FilterResults
        ) {
            articles!!.clear()
            articles!!.addAll(filterResults.values as List<ArticlesItem>)
            notifyDataSetChanged()
        }
    }

    class ViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {
        var sourceName: TextView
        var image: ImageView
        var content: TextView
        var title: TextView
        var date: TextView
        var articleCard: CardView

        init {
            sourceName = rootView.findViewById(R.id.source_name)
            title = rootView.findViewById(R.id.title)
            date = rootView.findViewById(R.id.date)
            image = rootView.findViewById(R.id.image)
            content = rootView.findViewById(R.id.content)
            articleCard = rootView.findViewById(R.id.articleCard)
        }


    }


}
