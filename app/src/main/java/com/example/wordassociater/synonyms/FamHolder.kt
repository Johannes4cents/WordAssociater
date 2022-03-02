package com.example.wordassociater.fams

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.Main
import com.example.wordassociater.R
import com.example.wordassociater.databinding.HolderFamBinding
import com.example.wordassociater.fire_classes.Fam
import com.example.wordassociater.firestore.FireCommonWords
import com.example.wordassociater.firestore.FireFams
import com.example.wordassociater.synonyms.popWordClasses
import com.example.wordassociater.utils.CommonWord
import com.example.wordassociater.utils.Helper
import com.example.wordassociater.utils.Language

class FamHolder(
        val b: HolderFamBinding,
        val onHeaderClicked: () -> Unit,
        private val onFamAdded: (fam: Fam) -> Unit
): RecyclerView.ViewHolder(b.root) {
    lateinit var type: FamRecycler.Type
    lateinit var fam : Fam
    fun onBind(type: FamRecycler.Type, fam: Fam) {
        this.type = type
        this.fam = fam
        if(fam.isHeader) setHeader()
        else setFam()
    }

    private fun setHeader() {
        b.holderLinear.visibility = View.GONE
        if(type != FamRecycler.Type.Popup) b.headerLinear.visibility = View.VISIBLE
        b.root.setOnClickListener {
            onHeaderClicked()
        }
    }

    private fun setFam() {
        setContent()
        setClickListener()
        setIcons()

    }

    private fun setContent() {
        setInputField()
    }

    private fun setIcons() {
        b.btnClass.setImageResource(fam.getClassImage())

        setWordClassIcon()
    }

    private fun setCommonWordIcon() {
        b.makeCommonWord.setImageResource(when(fam.commonWord) {
            CommonWord.Type.Very -> R.drawable.common_type_very_already_common
            CommonWord.Type.Somewhat -> R.drawable.common_type_somewhat
            CommonWord.Type.Uncommon -> R.drawable.common_type_very
        })

        if(fam.commonWord == CommonWord.Type.Somewhat) b.makeSomewhatCommonWord.visibility = View.GONE
    }

    private fun setWordClassIcon() {
        b.btnClass.setImageResource(fam.wordClass.image)
    }

    private fun setInputField() {
        b.famText.setTextField(fam.text)
        if(type == FamRecycler.Type.List) {
            b.famText.disableNuwInput()
            b.famText.enableTwiceClickSafety()
            b.famText.setTextField(fam.text)
            b.famText.hideOnEnter()
            b.famText.setCenterGravity()
            b.famText.setContentFunc {
                fam.text = Helper.stripWordLeaveWhiteSpace(it)
            }
            b.famText.showInputField()
        }
        if(type == FamRecycler.Type.Popup) {
            b.famText.enableInput(false)
        }

    }

    private fun setClickListener() {
        b.root.setOnClickListener {
            onFamAdded(fam)
        }
        b.btnClass.setOnClickListener {
            popWordClasses(b.btnClass, fam, ::onClassPicked)
        }

        b.makeCommonWord.setOnClickListener {
            makeCommonWord(CommonWord.Type.Very)
        }

        b.makeCommonWord.setOnClickListener {

        }
    }

    private fun onClassPicked(type: Fam.Class) {
        fam.wordClass = type
        FireFams.update(fam.id, "wordClass", fam.wordClass)
    }

    private fun makeCommonWord(type: CommonWord.Type) {
        val commonWord = CommonWord(Helper.stripWordLeaveWhiteSpace(fam.text), Language.German, type)
        if(Main.getCommonWord(Language.German, fam.text) == null ) FireCommonWords.add(commonWord)
        else Helper.toast("Already a common Word", b.root.context)
        fam.commonWord = type
        setCommonWordIcon()
    }



}