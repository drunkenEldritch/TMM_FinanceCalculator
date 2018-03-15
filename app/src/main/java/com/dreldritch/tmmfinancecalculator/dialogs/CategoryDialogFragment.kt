package com.dreldritch.tmmfinancecalculator.dialogs

import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.dreldritch.tmmfinancecalculator.R

class CategoryDialogFragment : DialogFragment() {

    //TODO Change string mock to db query

    private var mListenerCategoryDialog: OnCategoryInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_category_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    fun onItemPressed(category: String) {
        if (mListenerCategoryDialog != null) {
            mListenerCategoryDialog!!.onCategoryDialogInteraction(category)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnCategoryInteractionListener) {
            mListenerCategoryDialog = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnAccountDialogInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListenerCategoryDialog = null
    }

    interface OnCategoryInteractionListener {
        fun onCategoryDialogInteraction(category: String)
    }

    companion object {
        fun newInstance(): CategoryDialogFragment {
            return CategoryDialogFragment()
        }
    }
}// Required empty public constructor
