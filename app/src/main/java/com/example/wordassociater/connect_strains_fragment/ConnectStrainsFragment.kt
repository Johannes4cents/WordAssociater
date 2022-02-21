package com.example.wordassociater.connect_strains_fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.wordassociater.R
import com.example.wordassociater.character.CharacterAdapter
import com.example.wordassociater.databinding.FragmentConnectStrainsBinding
import com.example.wordassociater.fire_classes.Strain
import com.example.wordassociater.firestore.FireStats
import com.example.wordassociater.firestore.FireStrains
import com.example.wordassociater.utils.Helper

class ConnectStrainsFragment:Fragment() {
    lateinit var b : FragmentConnectStrainsBinding
    var newStrain = Strain()
    var isStory = false
    val charAdapter = CharacterAdapter(CharacterAdapter.Mode.PREVIEW)

    companion object {
        val strainList = mutableListOf<Strain>()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        b = FragmentConnectStrainsBinding.inflate(inflater)
        Toast.makeText(requireContext(), "in ConnectStrain", Toast.LENGTH_SHORT).show()
        setOldStrainsContent()
        setWords()
        setIsStory()
        setClickListener()
        return b.root
    }

    private fun setClickListener() {
        b.saveBtn.setOnClickListener {
            saveStrain()
        }

        b.backBtn.setOnClickListener {
            strainList.clear()
            findNavController().navigate(R.id.action_connectStrainsFragment2_to_readFragment)
        }
    }

    private fun setOldStrainsContent() {
        var content = ""
        for(strain in strainList) {
            content += "${strain.content} \n ----------------------------- \n"
        }
        b.oldStrainsContent.text = content
    }

    private fun setWords() {
        val wordList = mutableListOf<String>()
        for(strain in strainList) {
            for(w in strain.wordList) {
                if(!wordList.contains(w)) wordList.add(w)
            }
        }
        Log.i("connectStrains", "$wordList")
        newStrain.wordList = wordList
        b.associatedWords.text = Helper.setWordsToMultipleLines(newStrain.getWords())

    }

    private fun saveStrain() {
        if(b.strainInput.text.isNotEmpty()) {
            newStrain.content = b.strainInput.text.toString()
            newStrain.connectionLayer = setConnectionLayer()
            newStrain.id = FireStats.getStoryPartId()
            newStrain.isStory = isStory
            newStrain.header = b.headerInput.text.toString()

            FireStrains.add(newStrain, b.root.context)
            Helper.getIMM(requireContext()).hideSoftInputFromWindow(b.strainInput.windowToken, 0)
            strainList.clear()
            findNavController().navigate(R.id.action_connectStrainsFragment2_to_readFragment)
        }
        else {
            Toast.makeText(context, "Strain can't be empty", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setConnectionLayer(): Int {
        var connectionLayer = 0
        for (strain in strainList) {
            connectionLayer += strain.connectionLayer
        }
        Helper.toast(connectionLayer.toString(), requireContext())
        return connectionLayer
    }

    private fun setIsStory() {
        for(strain in strainList) {
            if(strain.isStory) isStory = true
            break
        }
    }


}