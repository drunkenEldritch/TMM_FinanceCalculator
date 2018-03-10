package com.dreldritch.tmmfinancecalculator

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.fragment_add_entry_dialog.*
import kotlinx.android.synthetic.main.fragment_add_entry_dialog.view.*


class AddEntryDialogFragment : DialogFragment() {

    private var type: String? = null

    private var mListenerAddDialog: OnAddDialogFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            type = arguments.getString(TYPE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view: View? = null

        when(type){
            DATEDIALOG -> view =  inflater!!.inflate(R.layout.fragment_add_entry_dialog!!, container, false)
        }

        return view
    }

    fun onOkButtonPressed() {
        if (mListenerAddDialog != null) {
            mListenerAddDialog!!.onFragmentInteraction()
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
        fun onFragmentInteraction()
    }

    companion object {
        private val TYPE = "mode"
        private val HEADER = "header"

        const val DATEDIALOG = "date_dialog"

        fun newInstance(header: String, type: String): AddEntryDialogFragment {
            val fragment = AddEntryDialogFragment()
            val args = Bundle()
            args.putString(TYPE, type)
            args.putString(HEADER, header)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
