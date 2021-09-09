package com.example.paginglibrary.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.paginglibrary.R
import com.example.paginglibrary.api.api.PhotoModel
import com.example.paginglibrary.databinding.ItemRecyclerviewBinding

class PhotosPagingDataAdapter
    : PagingDataAdapter<PhotoModel, PhotosPagingDataAdapter.PhotosViewHolder>(PhotosDiffUtilItemCallback()) {

    class PhotosViewHolder(private val viewBinding: ItemRecyclerviewBinding): RecyclerView.ViewHolder(viewBinding.root){
        fun bind(data: PhotoModel){

            viewBinding.apply{

                Glide.with(itemView)
                    .load(data.urls.regular)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(ivUserPhoto)

                tvTitle.text = data.user.name
            }
        }
    }

    class PhotosDiffUtilItemCallback: DiffUtil.ItemCallback<PhotoModel>(){
        override fun areItemsTheSame(oldItem: PhotoModel, newItem: PhotoModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PhotoModel, newItem: PhotoModel): Boolean {
            return newItem == oldItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        return PhotosViewHolder(ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    // how do we get the Item at specific position?
    // >>> there is a fun called getItem(position) from the ListAdapter
    // and the PagingDataAdapter !!!!
    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        val currentDataItem: PhotoModel? = this.getItem(position)
        currentDataItem?.let { holder.bind(it) }
    }


}