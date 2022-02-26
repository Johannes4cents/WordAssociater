package com.example.wordassociater.nuw

import androidx.recyclerview.widget.RecyclerView
import com.example.wordassociater.R
import com.example.wordassociater.databinding.HolderNuwBinding
import com.example.wordassociater.fire_classes.Nuw

class NuwHolder(
        val b: HolderNuwBinding,
        val onUpgradeClicked: (nuw: Nuw) -> Unit,
        val onRedXClicked: (nuw: Nuw) -> Unit,
        val rootClicked: (nuw: Nuw) -> Unit
): RecyclerView.ViewHolder(b.root) {
    lateinit var nuw: Nuw
    fun onBind(nuw: Nuw) {
        this.nuw = nuw
        setContent()
        setClickListener()
    }

    private fun setClickListener() {
        b.btnRedX.setOnClickListener {
            onRedXClicked(nuw)
        }

        b.btnUpgrade.setOnClickListener {
            onUpgradeClicked(nuw)
        }

        b.root.setOnClickListener {
            rootClicked(nuw)
        }
    }

    private fun setContent() {
        b.btnUpgrade.setImageResource(setUpgradeIcon())
        b.usedAmount.text = nuw.usedAmount.toString()
        b.textNuw.text = nuw.text

        b.root.setBackgroundColor(if(nuw.isUsed) b.root.resources.getColor(R.color.lightYellow) else b.root.resources.getColor(R.color.white))
        if(nuw.isUsed) b.btnUpgrade.setImageResource(R.drawable.icon_nuw_used)
    }

    private fun setUpgradeIcon(): Int {
        return when(nuw.isWord) {
            true -> R.drawable.btn_open_words_list
            false -> R.drawable.icon_upgrade
        }
    }
}