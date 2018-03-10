package com.dreldritch.tmmfinancecalculator

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class AddEntryDialogFragment : DialogFragment() {

    private var layout: Int? = null

    private var mListenerAddDialog: OnAddDialogFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            layout = arguments.getInt(LAYOUT_PARAM)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_add_entry_dialog!!, container, false)
    }

    fun onOkButtonPressed(uri: Uri) {
        if (mListenerAddDialog != null) {
            mListenerAddDialog!!.onFragmentInteraction(uri)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnAddDialogFragmentInteractionListener) {
            mListenerAddDialog = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnAddDialogFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListenerAddDialog = null
    }

    interface OnAddDialogFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        private val LAYOUT_PARAM = "layout"

        fun newInstance(resource: Int): AddEntryDialogFragment {
            val fragment = AddEntryDialogFragment()
            val args = Bundle()
            args.putInt(LAYOUT_PARAM, resource)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
