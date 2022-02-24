package com.example.wordassociater.strains

import android.os.Bundle
import android.util.Log
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
import com.example.wordassociater.ViewPagerFragment
import com.example.wordassociater.databinding.FragmentStrainsListBinding
import com.example.wordassociater.fire_classes.Strain
import com.example.wordassociater.utils.Page

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
        strainAdapter = StrainAdapter(::handleStrainSelected)
        b.strainsRecycler.adapter = strainAdapter
        setClickListener()
        setObserver()
        handleSearch()
        handleLayerButton()
        Main.inFragment = Frags.READ
        strainAdapter.submitList(Main.strainsList.value)
        return b.root
    }


    private fun handleStrainSelected(strain: Strain) {
        StrainEditFragment.oldStrain = strain.copyMe()
        StrainEditFragment.strain = strain
        Main.inFragment = Frags.WRITE
        StrainEditFragment.comingFrom  = Frags.READ
        navController.navigate(R.id.action_readFragment_to_writeFragment)
    }

    private fun handleLayerButton() {
        b.strainLayerButton.setClickListener()
        b.strainLayerButton.currentLayer.observe(context as LifecycleOwner) {
            var filteredList = Main.strainsList.value?.filter { strain -> strain.connectionLayer >= it }
            strainAdapter.submitList(filteredList)
        }
    }

    private fun handleSearch() {
        b.searchBar.getStrains {
            Log.i("strainSearch", "strainSearch called")
            if(it.isEmpty()) strainAdapter.submitList(Main.strainsList.value)
            else strainAdapter.submitList(it)
        }
    }
    private fun setClickListener() {
        b.backBtn.setOnClickListener {
            ViewPagerFragment.comingFrom = Page.Start
            findNavController().navigate(R.id.action_readFragment_to_startFragment)
        }
    }

    private fun setObserver() {

        Main.strainsList.observe(context as LifecycleOwner) {
            if(it != null) {
                strainAdapter.submitList(it)
            }
        }
    }

}