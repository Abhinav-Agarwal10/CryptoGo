package com.trinetra.cryptogo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.trinetra.cryptogo.adapters.MarketAdapter
import com.trinetra.cryptogo.apis.ApiInterface
import com.trinetra.cryptogo.apis.ApiUtilities
import com.trinetra.cryptogo.databinding.FragmentToplossgainBinding
import com.trinetra.cryptogo.models.CryptoCurrency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Collections

class toplossgainFragment : Fragment() {

    lateinit var binding : FragmentToplossgainBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentToplossgainBinding.inflate(layoutInflater)

        getMarketData()

        return binding.root
    }

    private fun getMarketData() {

        val position = requireArguments().getInt("position")
        lifecycleScope.launch (Dispatchers.IO){
            val res = ApiUtilities.getInstance().create(ApiInterface::class.java).getMarketData()

            if(res.body() != null)
            {
                withContext(Dispatchers.Main){
                    val dataitem = res.body()!!.data.cryptoCurrencyList

                    Collections.sort(dataitem){
                        o1, o2 -> (o2.quotes[0].percentChange24h.toInt())
                        .compareTo(o1.quotes[0].percentChange24h.toInt())
                    }

                    binding.spinKitView.visibility = GONE

                    val list = java.util.ArrayList<CryptoCurrency>()

                    if(position == 0)
                    {
                        list.clear()
                        for (i in 0..9)
                        {
                            list.add(dataitem[i])
                        }

                        binding.topGainLoseRecyclerView.adapter = MarketAdapter(
                            requireContext(),
                            list,
                            "home"
                        )
                    }
                    else
                    {
                        list.clear()
                        for (i in 0..9)
                        {
                            list.add(dataitem[dataitem.size-1-i])
                        }

                        binding.topGainLoseRecyclerView.adapter = MarketAdapter(
                            requireContext(),
                            list,
                            "home"
                        )
                    }
                }
            }
        }
    }

}