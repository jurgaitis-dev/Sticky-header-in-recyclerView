package com.z9.stickyheaderinrecyclerview.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.z9.stickyheaderinrecyclerview.databinding.ItemCountryBodyBinding
import com.z9.stickyheaderinrecyclerview.databinding.ItemLetterBinding
import com.z9.stickyheaderinrecyclerview.domain.model.Country

class CountriesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val countries = mutableListOf<Country>()

    override fun getItemViewType(position: Int): Int {
        if (countries[position] is Country.Letter) {
            return VIEW_TYPE_COUNTRY_LETTER
        }
        return VIEW_TYPE_COUNTRY_BODY
    }

    override fun getItemCount(): Int {
        return countries.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_COUNTRY_LETTER) {
            return CountryLetterViewHolder(
                ItemLetterBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                )
            )
        }

        val binding =
            ItemCountryBodyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryBodyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CountryLetterViewHolder -> holder.bind(position)
            is CountryBodyViewHolder -> holder.bind(position)
        }
    }

    inner class CountryLetterViewHolder(private val binding: ItemLetterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.title.text = countries[position].name
        }
    }

    inner class CountryBodyViewHolder(private val binding: ItemCountryBodyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.title.text = countries[position].name
        }
    }

    fun updateAdapter(newCountries: List<Country>) {
        countries.clear()
        countries.addAll(newCountries)
        notifyDataSetChanged()
    }

    companion object {
        const val VIEW_TYPE_COUNTRY_LETTER = 1
        const val VIEW_TYPE_COUNTRY_BODY = 2
    }
}