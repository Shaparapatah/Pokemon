package com.example.screens.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.model.dto.CustomPokemonListItem
import com.example.screens.databinding.ItemPokemonBinding
import com.example.utils.ImageLoader
import java.util.*

class MainScreenAdapter : RecyclerView.Adapter<MainScreenAdapter.MainScreenViewHolder>() {

//    private var onClickListener: OnClickListener? = null
    private var pokemonList = mutableListOf<CustomPokemonListItem>()


    class MainScreenViewHolder(private val binding: ItemPokemonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CustomPokemonListItem, onClickListener: OnClickListener?) {
            binding.name.text = item.name.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.ROOT)
                else it.toString()
            }

            binding.type.text = "Type: ${item.type}".replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.ROOT)
                else it.toString()
            }

            binding.cardView.setOnClickListener {
                onClickListener?.onClick(item)
            }

//            item.Image?.let { ImageLoader.loadImage(itemView.context, binding.image, it) }
        }
    }

    interface OnClickListener {
        fun onClick(item: CustomPokemonListItem)
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
//        this.onClickListener = onClickListener
    }

    fun setList(list: List<CustomPokemonListItem>) {
        pokemonList = list as MutableList<CustomPokemonListItem>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainScreenViewHolder {
        return inflateLayout(parent)
    }

    override fun onBindViewHolder(holder: MainScreenViewHolder, position: Int) {
//        holder.bind(pokemonList[position], onClickListener)
    }

    override fun getItemCount() = pokemonList.size

    companion object {
        fun inflateLayout(parent: ViewGroup): MainScreenViewHolder {
            parent.apply {
                val inflate = LayoutInflater.from(parent.context)
                val binding = ItemPokemonBinding.inflate(inflate, parent, false)
                return MainScreenViewHolder(binding)
            }
        }
    }
}
