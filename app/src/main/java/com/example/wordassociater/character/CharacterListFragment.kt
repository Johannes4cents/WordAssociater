package com.example.wordassociater.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.example.wordassociater.Frags
import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.example.wordassociater.databinding.FragmentCharacterListBinding
import com.example.wordassociater.fire_classes.Character
import com.example.wordassociater.firestore.FireLists

class CharacterListFragment: Fragment() {
    lateinit var b: FragmentCharacterListBinding
    lateinit var characterAdapter: CharacterAdapter

    companion object {
        val selectedCharacter = MutableLiveData<Character?>()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Main.inFragment = Frags.CHARACTERLIST
        b = FragmentCharacterListBinding.inflate(layoutInflater)
        handleRecycler()
        return b.root
    }

    private fun handleRecycler() {
        characterAdapter = CharacterAdapter(CharacterAdapter.Mode.LIST)
        b.characterListRecycler.adapter = characterAdapter
        getCharacter()
        setObserver()
        setClickListener()
    }

    private fun getCharacter() {
        FireLists.characterList.addSnapshotListener { docs, error ->
            val characterList = mutableListOf<Character>()
            for(doc in docs!!) {
                val character = doc.toObject(Character::class.java)
                character.id = doc.id
                characterList.add(character)
            }
            characterAdapter.submitList(characterList)
        }
    }

    private fun setClickListener() {
        b.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_characterListFragment_to_startFragment)
        }
    }

    private fun setObserver() {
        selectedCharacter.observe(viewLifecycleOwner) {
            if(it != null) {
                findNavController().navigate(R.id.action_characterListFragment_to_characterFragment)
            }
        }
    }
}