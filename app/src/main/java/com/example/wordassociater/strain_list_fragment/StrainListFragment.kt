package com.example.wordassociater.strain_list_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.wordassociater.Frags
import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.example.wordassociater.databinding.FragmentStrainsListBinding
import com.example.wordassociater.utils.Helper
import com.example.wordassociater.fire_classes.Strain

class StrainListFragment: Fragment() {

    companion object {
        var openStrain = MutableLiveData<Strain?>(null)
        lateinit var strainAdapter: StrainAdapter
        lateinit var navController : NavController
    }

    lateinit var b: FragmentStrainsListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = FragmentStrainsListBinding.inflate(inflater)
        navController = findNavController()
        openStrain.postValue(null)
        strainAdapter = StrainAdapter()
        b.strainsRecycler.adapter = strainAdapter
        setClickListener()
        setObserver()
        handleOrSearch()
        handleLayerButton()
        Main.inFragment = Frags.READ
        strainAdapter.submitList(Main.strainsList.value)
        return b.root
    }

    private fun handleLayerButton() {
        b.strainLayerButton.setClickListener()
        b.strainLayerButton.currentLayer.observe(context as LifecycleOwner) {
            var filteredList = Main.strainsList.value?.filter { strain -> strain.connectionLayer >= it }
            strainAdapter.submitList(filteredList)
        }
    }

    private fun handleOrSearch() {
        b.searchBar.searchWords.observe(viewLifecycleOwner) {
            if(it.isNotEmpty()) strainAdapter.submitList(Helper.getOrFilteredStoryPartList(it, Main.strainsList.value!!) as List<Strain>)
            else strainAdapter.submitList(Main.strainsList.value)
        }
    }
    private fun setClickListener() {
        b.backBtn.setOnClickListener {
            findNavController().navigate(R.id.action_readFragment_to_startFragment)
        }
    }

    private fun setObserver() {
        openStrain.observe(context as LifecycleOwner) {
            if(it != null && Main.inFragment == Frags.READ) {
                Main.inFragment = Frags.WRITE
                findNavController().navigate(R.id.action_readFragment_to_writeFragment)
            }
        }

        Main.strainsList.observe(context as LifecycleOwner) {
            if(it != null) {
                strainAdapter.submitList(it)
            }
        }
    }

}