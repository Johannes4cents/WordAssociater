package com.example.wordassociater

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.example.wordassociater.bars.AddWordBar
import com.example.wordassociater.character.CharacterAdapter
import com.example.wordassociater.databinding.FragmentStartBinding
import com.example.wordassociater.fire_classes.Sphere
import com.example.wordassociater.words.WordLinear

class StartFragment: Fragment() {
    lateinit var b : FragmentStartBinding
    lateinit var adapter: CharacterAdapter

    companion object {
        val selectedSphereList = MutableLiveData<List<Sphere>>()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentStartBinding.inflate(inflater)
        b.contentBar.navController = findNavController()
        b.addStuffBar.navController = findNavController()
        AddWordBar.navController = findNavController()
        setRecycler()
        setObserver()
        return b.root
    }

    private fun setRecycler() {
        adapter = CharacterAdapter(CharacterAdapter.Mode.PREVIEW)
        b.charPreviewRecycler.adapter = adapter
        b.connectedWordRecycler.setLiveWords(WordLinear.selectedWordsLive)
    }

    private fun setObserver() {
        CharacterAdapter.characterListTrigger.observe(context as LifecycleOwner) {
            if(CharacterAdapter.selectedCharacterList.isNotEmpty() || CharacterAdapter.selectedNameChars.isNotEmpty()) {
                b.charPreviewRecycler.visibility = View.VISIBLE
                adapter.submitList(CharacterAdapter.selectedCharacterList)
            }
            else {
                View.GONE
            }
        }
    }



}