package com.pf.aforo.ui.home

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.pf.aforo.data.model.UserFuncionario

//1
class UserHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
    //2
    private var view: View = v
    private var user: UserFuncionario? = null

    //3
    init {
        v.setOnClickListener(this)
    }

    //4
    override fun onClick(v: View) {
        Log.d("RecyclerView", "CLICK!")
    }

    companion object {
        //5
        private val PHOTO_KEY = "PHOTO"
    }

}