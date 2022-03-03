package com.example.wordassociater.fams

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.R
import com.example.wordassociater.databinding.HolderFamBinding
import com.example.wordassociater.fire_classes.Fam
import com.example.wordassociater.firestore.FireFams
import com.example.wordassociater.utils.CommonWord
import com.example.wordassociater.utils.Helper

class FamHolder(
        val b: HolderFamBinding,
        private val onHeaderClicked: () -> Unit,
        private val onFamAdded: (fam: Fam) -> Unit,
        private val onUpgradeFam: (fam: Fam) -> Unit,
        private val onMakeCommonWord: (fam: Fam, type: CommonWord.Type) -> Unit,
): RecyclerView.ViewHolder(b.root) {
    lateinit var type: FamRecycler.Type
    lateinit var fam : Fam
    fun onBind(type: FamRecycler.Type, fam: Fam) {
        this.type = type
        this.fam = fam
        when {
            fam.isHeader -> setHeader()
            type == FamRecycler.Type.Popup -> setPopUp()
            else -> setFam()
        }
    }

    private fun setPopUp() {
        b.makeCommonWord.visibility = View.GONE
        b.makeSomewhatCommonWord.visibility = View.GONE
        b.btnClass.visibility = View.GONE
        b.btnUpgrade.visibility = View.GONE
        b.famText.setTextField(fam.text)
        b.famText.enableInput(false)


        b.root.setOnClickListener {
            onFamAdded(fam)
        }
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

    private fun setInputField() {

        b.famText.disableNuwInput()
        b.famText.setTextField(fam.text)
        b.famText.setCenterGravity()

        if(type == FamRecycler.Type.List) {
            b.famText.enableTwiceClickSafety()
            b.famText.hideOnEnter()

            if(fam.firstOpen) {
                fam.firstOpen = false
                b.famText.showInputField()
            }

            b.famText.setOnEnterFunc {
                fam.text = Helper.stripWordLeaveWhiteSpace(it)
                onFamAdded(fam)
            }
        }
        if(type == FamRecycler.Type.Popup) {
            b.famText.enableInput(false)

        }

    }

    private fun setClickListener() {
        b.root.setOnClickListener {
            fam.text = Helper.stripWordLeaveWhiteSpace(b.famText.content)
            onFamAdded(fam)
        }
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