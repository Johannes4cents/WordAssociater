package com.example.wordassociater.fams

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.R
import com.example.wordassociater.databinding.HolderFamBinding
import com.example.wordassociater.fire_classes.Fam
import com.example.wordassociater.firestore.FireFams
import com.example.wordassociater.utils.CommonWord

class FamHolder(
        val b: HolderFamBinding,
        private val onFamClicked: (fam: Fam) -> Unit,
        private val onUpgradeFam: (fam: Fam) -> Unit,
        private val onMakeCommonWord: (fam: Fam, type: CommonWord.Type) -> Unit,
): RecyclerView.ViewHolder(b.root) {
    lateinit var type: FamRecycler.Type
    lateinit var fam : Fam

    fun onBind(type: FamRecycler.Type, fam: Fam) {
        this.type = type
        this.fam = fam
        b.famText.text = fam.text
        if (type == FamRecycler.Type.Popup) setPopUp()
        else setFam()
    }

    private fun setPopUp() {
        b.makeCommonWord.visibility = View.GONE
        b.makeSomewhatCommonWord.visibility = View.GONE
        b.btnClass.visibility = View.GONE
        b.btnUpgrade.visibility = View.GONE


        b.root.setOnClickListener {
            onFamClicked(fam)
        }
    }

    private fun setFam() {
        setClickListener()
        setIcons()

    }


    private fun setIcons() {
        b.btnClass.setImageResource(fam.getClassImage())
        setCommonWordIcon()
        fam.checkIfCommon()
        setWordClassIcon()
    }

    private fun setCommonWordIcon() {
        b.makeCommonWord.setImageResource(when(fam.commonWord) {
            CommonWord.Type.Very -> R.drawable.common_type_very_already_common
            CommonWord.Type.Somewhat -> R.drawable.common_type_somewhat
            CommonWord.Type.Uncommon -> R.drawable.common_type_very
        })

        if(fam.commonWord == CommonWord.Type.Somewhat) b.makeSomewhatCommonWord.visibility = View.INVISIBLE
    }

    private fun setWordClassIcon() {
        b.btnClass.setImageResource(fam.wordClass.image)
    }


    private fun setClickListener() {
        b.btnClass.setOnClickListener {
            popWordClasses(b.btnClass, fam, ::onClassPicked)
        }

        b.makeCommonWord.setOnClickListener {
            onMakeCommonWord(fam, CommonWord.Type.Very)
            setCommonWordIcon()
        }

        b.makeSomewhatCommonWord.setOnClickListener {
            onMakeCommonWord(fam, CommonWord.Type.Somewhat)
            setCommonWordIcon()
        }

        b.btnUpgrade.setOnClickListener {

        }
    }

    private fun onClassPicked(type: Fam.Class) {
        fam.wordClass = type
        FireFams.update(fam.id, "wordClass", fam.wordClass)
        setWordClassIcon()
    }





}