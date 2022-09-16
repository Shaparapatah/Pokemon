package com.example.screens.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.model.dto.CustomPokemonListItem
import com.example.screens.databinding.ItemPokemonBinding
import java.util.*

class AdapterMain : RecyclerView.Adapter<AdapterMain.MainViewHolder>() {

    private var onClickListener: OnClickListener? = null
    private var pokemonList = mutableListOf<CustomPokemonListItem>()


    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(pokemonList[position], onClickListener)
    }

    override fun getItemCount(): Int = pokemonList.size

    inner class MainViewHolder(private val binding: ItemPokemonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CustomPokemonListItem, onClickListener: OnClickListener?) {
            binding.name.text =
                item.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() } //
            binding.type.text = "Type: ${
                item.type.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.ROOT
                    ) else it.toString()
                }
            }"

            binding.cardView.setOnClickListener {
                onClickListener?.onClick(item)
            }
        }
    }

    fun setList(list: List<CustomPokemonListItem>) {
        pokemonList = list as MutableList<CustomPokemonListItem>
    }


    interface OnClickListener {
        fun onClick(item: CustomPokemonListItem)

    }
}

