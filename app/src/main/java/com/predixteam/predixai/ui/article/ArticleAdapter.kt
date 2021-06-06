package com.predixteam.predixai.ui.article

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.predixteam.predixai.R
import com.predixteam.predixai.data.source.remote.response.ResponseItem
import com.predixteam.predixai.databinding.ListArticleBinding

class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    private var listArticle = ArrayList<ResponseItem>()

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ResponseItem)
    }

    fun setArticle(article: List<ResponseItem>?) {
        if (article == null) return
        this.listArticle.clear()
        this.listArticle.addAll(article)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ListArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val articles = listArticle[position]
        holder.bind(articles)
        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(listArticle[position])
        }
    }

    override fun getItemCount(): Int = listArticle.size


    class ArticleViewHolder(private val binding: ListArticleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: ResponseItem) {
            with(binding) {
                tvTitleArticle.text = article.title
                tvContentArticle.text = article.content
                Glide.with(itemView.context)
                    .load(article.images)
                    .apply(RequestOptions().placeholder(R.drawable.ic_loading).error(R.drawable.ic_error))
                    .into(imgArticle)
            }
        }
    }
}