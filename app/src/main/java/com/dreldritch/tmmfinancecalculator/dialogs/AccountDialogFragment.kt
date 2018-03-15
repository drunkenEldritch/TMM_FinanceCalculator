package com.dreldritch.tmmfinancecalculator.dialogs

import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.dreldritch.tmmfinancecalculator.R
import kotlinx.android.synthetic.main.fragment_account_dialog.*

class AccountDialogFragment : DialogFragment() {

    //TODO Change string mock to db query

    private var mListenerAccountDialog: OnAccountDialogInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_account_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, arrayOf("Acc1", "Acc2"))
        acc_dialog_list_view.adapter = adapter
        acc_dialog_list_view.setOnItemClickListener { parent, view, position, id ->
            val result = acc_dialog_list_view.getItemAtPosition(position) as String
            onItemPressed(result)
            dismiss()
        }
    }

    fun onItemPressed(account: String) {
        if (mListenerAccountDialog != null) {
            mListenerAccountDialog!!.onAccDialogInteraction(account)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnAccountDialogInteractionListener) {
            mListenerAccountDialog = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnAccountDialogInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListenerAccountDialog = null
    }

    interface OnAccountDialogInteractionListener {
        fun onAccDialogInteraction(account: String)
    }

    companion object {
        fun newInstance(): AccountDialogFragment {
            return AccountDialogFragment()
        }
    }
}// Required empty public constructor
