package com.example.wordassociater.popups

import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.wordassociater.databinding.PopNuwsBinding
import com.example.wordassociater.fire_classes.Nuw
import com.example.wordassociater.utils.Helper

fun popNuwsEdit(
        from: View,
        liveList: MutableLiveData<List<Nuw>?>,
        onUpgradeClicked: (nuw: Nuw) -> Unit,
        onDirtClicked: (nuw: Nuw) -> Unit,
        onPotatoClicked: (nuw: Nuw) -> Unit,
        onRootclicked: (nuw: Nuw) -> Unit
) {
    val b = PopNuwsBinding.inflate(LayoutInflater.from(from.context), null, false)
    val pop = Helper.getPopUp(b.root, from, 800, 800)

    b.nuwRecycler.initRecycler(liveList, onUpgradeClicked, onDirtClicked, onPotatoClicked, onRootclicked)
    b.clearAllBtn.setOnClickListener { pop.dismiss() }

}