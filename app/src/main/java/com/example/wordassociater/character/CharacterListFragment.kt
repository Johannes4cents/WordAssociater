package com.example.wordassociater.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import com.example.wordassociater.Frags
import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.example.wordassociater.ViewPagerFragment
import com.example.wordassociater.databinding.FragmentCharacterListBinding
import com.example.wordassociater.fire_classes.Character
import com.example.wordassociater.utils.Page

class CharacterListFragment: Fragment() {
    lateinit var b: FragmentCharacterListBinding
    lateinit var characterAdapter: CharacterAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Main.inFragment = Frags.CHARACTERLIST
        b = FragmentCharacterListBinding.inflate(layoutInflater)
        handleRecycler()
        return b.root
    }

    private fun handleRecycler() {
        characterAdapter = CharacterAdapter(CharacterAdapter.Mode.LIST,null, ::handleCharacterSelected)
        b.characterListRecycler.adapter = characterAdapter
        getCharacter()
        setClickListener()
    }

    private fun getCharacter() {
        Main.characterList.observe(context as LifecycleOwner) {
            characterAdapter.submitList(it?.sortedBy { c -> c.name })
        }
    }

    private fun setClickListener() {
        b.backButton.setOnClickListener {
            ViewPagerFragment.goTopage(Page.Start)
        }
    }

    private fun handleCharacterSelected(character: Character) {
        CharacterFragment.character = character
        findNavController().navigate(R.id.action_ViewPagerFragment_to_characterFragment)
    }

}