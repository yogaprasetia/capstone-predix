package com.predixteam.predixai.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.predixteam.predixai.R
import com.predixteam.predixai.data.ModelEntity
import com.predixteam.predixai.databinding.ListModelBinding

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private var listModels = ArrayList<ModelEntity>()

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ModelEntity)
    }

    fun setModels(movies: List<ModelEntity>?) {
        if (movies == null) return
        this.listModels.clear()
        this.listModels.addAll(movies)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ListModelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val models = listModels[position]
        holder.bind(models)
        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(listModels[position])
        }
    }

    override fun getItemCount(): Int = listModels.size


    class HomeViewHolder(private val binding: ListModelBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: ModelEntity) {
            with(binding) {
                tvModelName.text = model.name
                tvDisease.text = model.disease
                Glide.with(itemView.context)
                    .load(model.imageModel)
                    .apply(RequestOptions().placeholder(R.drawable.ic_loading).error(R.drawable.ic_error))
                    .into(imgModel)
            }
        }
    }
}