package com.dreldritch.tmmfinancecalculator.dialogs

import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dreldritch.tmmfinancecalculator.R
import kotlinx.android.synthetic.main.fragment_add_entry_dialog.*
import java.text.SimpleDateFormat
import java.util.*


class DateDialogFragment : DialogFragment() {

    val DATESORT = "yyyy-MM-dd"
    val DATESLASH = "dd/MM/yyyy"
    val DATEPOINT = "dd.MM.yyyy"

    val preferedFormat = DATESORT

    private var mListenerAddDialog: OnAddDialogFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_add_entry_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog_btn_ok.setOnClickListener {
            onOkButtonPressed()
            dismiss()
        }

        dialog_btn_dismiss.setOnClickListener {
            dismiss()
        }
    }

    fun onOkButtonPressed() {
        if (mListenerAddDialog != null) {
            mListenerAddDialog!!.onDateDialogInteraction(getDate(preferedFormat))
        }
    }

    private fun getDate(format: String): String{
        val calendar = GregorianCalendar(datePicker.year,
                datePicker.month,
                datePicker.dayOfMonth)
        return SimpleDateFormat(format).format(calendar.timeInMillis)
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
        fun onDateDialogInteraction(date: String)
    }

    companion object {
        fun newInstance(): DateDialogFragment {
            return DateDialogFragment()
        }
    }
}// Required empty public constructor
