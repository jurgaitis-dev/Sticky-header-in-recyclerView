package com.z9.stickyheaderinrecyclerview.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.z9.stickyheaderinrecyclerview.databinding.FragmentCountriesBinding
import com.z9.stickyheaderinrecyclerview.presentation.CountriesAdapter.Companion.VIEW_TYPE_COUNTRY_LETTER
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CountriesFragment : Fragment() {

    private var countriesAdapter: CountriesAdapter? = null
    private lateinit var viewModel: CountriesViewModel

    private var _binding : FragmentCountriesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCountriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CountriesViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(CountriesViewModel::class.java)

        viewModel.apply {
            lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    state.collect {
                        updateAdapter(it)
                    }
                }
            }
        }
    }

    private fun updateAdapter(state: CountryState) {
        if (countriesAdapter == null) {
            countriesAdapter = CountriesAdapter()
            val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this.context)
            binding.countries.layoutManager = layoutManager
            binding.countries.adapter = countriesAdapter

            binding.countries.addItemDecoration(StickyHeaderItemDecoration(VIEW_TYPE_COUNTRY_LETTER))
        }
        countriesAdapter!!.updateAdapter(state.countries)
    }

    companion object {
        fun newInstance() = CountriesFragment()
    }
}