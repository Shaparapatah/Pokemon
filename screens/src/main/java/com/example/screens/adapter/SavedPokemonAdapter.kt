package com.example.screens.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.model.dto.CustomPokemonListItem
import com.example.screens.databinding.SavedItemPokemonBinding
import com.example.utils.ImageLoader
import java.util.*

class SavedPokemonAdapter : RecyclerView.Adapter<SavedPokemonAdapter.SavedPokemonViewHolder>() {

    private var onClickListener: OnClickListener? = null
    private var onDeleteListener: OnDeleteListener? = null
    private var pokemonList = mutableListOf<CustomPokemonListItem>()


    class SavedPokemonViewHolder(private val binding: SavedItemPokemonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: CustomPokemonListItem,
            onClickListener: OnClickListener?,
            onDeleteListener: OnDeleteListener?,
            pos: Int
        ) {
            binding.name.text = item.name.capitalize(Locale.ROOT) // captilise name
            binding.type.text = "Type: ${item.type?.capitalize(Locale.ROOT)}"
//            item.Image?.let { ImageLoader.loadImage(itemView.context, binding.image, it) }


            // setting click listener to be overridden in SavedFragment
            binding.cardView.setOnClickListener {
                onClickListener?.onClick(item)
            }


            // setting delete listener to be overridden in SavedFragment
            binding.deleteImg.setOnClickListener {
                onDeleteListener?.onDelete(item, pos)
            }
        }

        companion object {
            fun inflateLayout(parent: ViewGroup): SavedPokemonViewHolder {
                parent.apply {
                    val inflater = LayoutInflater.from(parent.context)
                    val binding = SavedItemPokemonBinding.inflate(inflater, parent, false)
                    return SavedPokemonViewHolder(binding)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedPokemonViewHolder {
        return SavedPokemonViewHolder.inflateLayout(parent)
    }

    override fun onBindViewHolder(holderSaved: SavedPokemonViewHolder, position: Int) {
        holderSaved.bind(pokemonList[position], onClickListener, onDeleteListener, position)
    }

    override fun getItemCount(): Int = pokemonList.size

    interface OnClickListener {
        fun onClick(item: CustomPokemonListItem)
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnDeleteListener {
        fun onDelete(item: CustomPokemonListItem, pos: Int)
    }

    fun setOnDeleteListener(onDeleteListener: OnDeleteListener) {
        this.onDeleteListener = onDeleteListener
    }


    fun setList(list: List<CustomPokemonListItem>) {
        pokemonList.clear()
        pokemonList = list as MutableList<CustomPokemonListItem>
    }

    fun removeItemAtPosition(pos: Int) {
        pokemonList.removeAt(pos)
    }


}