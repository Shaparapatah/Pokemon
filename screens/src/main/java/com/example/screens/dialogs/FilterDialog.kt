package com.example.screens.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.example.screens.R

class FilterDialog(var fragmentlistener: TypePicker) : DialogFragment() {

    var listener: TypePicker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRetainInstance(true)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        listener = fragmentlistener as TypePicker
        val fragement_container = container
        val rootview =
            layoutInflater.inflate(R.layout.dialog_typefilter, fragement_container, false)
        val fireImg = rootview.findViewById<ImageView>(R.id.dialog_fire_img)
        val waterImg = rootview.findViewById<ImageView>(R.id.dialog_water_img)
        val grassImg = rootview.findViewById<ImageView>(R.id.dialog_grass_img)
        val cancelBtn = rootview.findViewById<Button>(R.id.dialog_cancel_button)


        fireImg.setOnClickListener {
            listener?.typeToSearch("fire")
            this.dismiss()
        }

        waterImg.setOnClickListener {
            listener?.typeToSearch("water")
            this.dismiss()
        }

        grassImg.setOnClickListener {
            listener?.typeToSearch("grass")
            this.dismiss()
        }

        cancelBtn.setOnClickListener {
            this.dismiss()
        }

        return rootview
    }


    interface TypePicker {
        fun typeToSearch(type: String)
    }

    override fun onDestroyView() {
        if (dialog != null && retainInstance) dialog!!.setOnDismissListener(null)
        super.onDestroyView()
    }
}