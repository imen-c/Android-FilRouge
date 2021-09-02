package com.technipixl.filrouge.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.technipixl.filrouge.R
import com.technipixl.filrouge.databinding.BusinessItemBinding
import com.technipixl.filrouge.network.models.BusinessModel

class BusinessAdapter(val onItemClicked: (BusinessModel) -> Unit): RecyclerView.Adapter<BusinessAdapter.BusinessViewHolder>() {

    var models: List<BusinessModel>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusinessViewHolder {
        val binding = BusinessItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BusinessViewHolder(binding) { position ->
            models?.get(position)?.let { selectedItem ->
                onItemClicked(selectedItem)
            }
        }
    }

    override fun onBindViewHolder(holder: BusinessViewHolder, position: Int) {
        models?.get(position)?.let { model ->
            holder.bind(model)
        } ?: kotlin.run {
            holder.unbind()
        }
    }

    override fun onViewRecycled(holder: BusinessViewHolder) {
        super.onViewRecycled(holder)
        holder.unbind()
    }

    override fun getItemCount(): Int {
        return models?.size ?: 0
    }

    class BusinessViewHolder(private var viewBinding: BusinessItemBinding, val onItemClicked: (Int) -> Unit) :
        RecyclerView.ViewHolder(viewBinding.root),
        Bindable<BusinessModel> {

        init {
            itemView.setOnClickListener {
                onItemClicked(adapterPosition)
            }
        }

        override fun bind(model: BusinessModel) {
            viewBinding.titleTextView.text = model.categoryModels.firstOrNull()?.title
            viewBinding.nameTextView.text = model.name
            viewBinding.cityTextView.text = model.locationModel.city

            if(model.imageUrl.isNullOrEmpty().not()) {
                Picasso.get()
                    .load(model.imageUrl)
                    .error(R.drawable.image_placeholder)
                    .into(viewBinding.previewImageView)
            } else {
                viewBinding.previewImageView.setImageResource(R.drawable.image_placeholder)
            }
        }

        override fun unbind() {
            viewBinding.titleTextView.text = null
            viewBinding.nameTextView.text = null
            viewBinding.cityTextView.text = null
            viewBinding.previewImageView.setImageDrawable(null)
        }
    }
}