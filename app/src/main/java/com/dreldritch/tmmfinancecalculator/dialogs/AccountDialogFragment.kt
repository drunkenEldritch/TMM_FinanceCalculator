package com.dreldritch.tmmfinancecalculator.dialogs

import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.dreldritch.tmmfinancecalculator.R
import com.dreldritch.tmmfinancecalculator.extensions.getAccountStrings
import com.dreldritch.tmmfinancecalculator.model.entities.AccountEntity
import kotlinx.android.synthetic.main.fragment_account_dialog.*

class AccountDialogFragment : DialogFragment() {

    //TODO Change string mock to db query

    private var mListenerAccountDialog: OnAccountDialogInteractionListener? = null
    lateinit var accounts : ArrayList<AccountEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(arguments != null)
            accounts = arguments.getParcelableArrayList<AccountEntity>(ACCOUNTS)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_account_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        acc_dialog_list_view.apply {
            val adapter = ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, accounts.getAccountStrings())
            this.adapter = adapter
            setOnItemClickListener { parent, view, position, id ->
                //val result = acc_dialog_list_view.getItemAtPosition(position) as String
                onItemPressed(accounts[position])
                dismiss()
            }
        }
    }

    fun onItemPressed(accountEntity: AccountEntity) {
        if (mListenerAccountDialog != null) {
            mListenerAccountDialog!!.onAccDialogInteraction(accountEntity)
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
        fun onAccDialogInteraction(accountEntity: AccountEntity)
    }

    companion object {
        const val ACCOUNTS = "accounts"

        fun newInstance(accounts: List<AccountEntity>): AccountDialogFragment {
            val dialog = AccountDialogFragment()
            val bundle = Bundle()
            bundle.putParcelableArrayList(ACCOUNTS, ArrayList(accounts))
            dialog.arguments = bundle
            return dialog
        }
    }

}// Required empty public constructor
