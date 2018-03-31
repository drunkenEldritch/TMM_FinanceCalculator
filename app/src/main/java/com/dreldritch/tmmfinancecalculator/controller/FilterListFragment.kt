package com.dreldritch.tmmfinancecalculator.controller

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dreldritch.tmmfinancecalculator.R
import com.dreldritch.tmmfinancecalculator.adapter.ExpandableListDateAdapter
import com.dreldritch.tmmfinancecalculator.model.entities.FullTransactionData
import kotlinx.android.synthetic.main.fragment_filter_list.*

//TODO Remove fragment
class FilterListFragment: Fragment() {

    private var transactionsList: ArrayList<FullTransactionData>? = null

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(arguments != null){
            transactionsList = arguments.getParcelableArrayList<FullTransactionData>(ARG_FULL_DATA)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater!!.inflate(R.layout.fragment_filter_list, container, false)
    }

    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        private val ARG_FULL_DATA = "fullData"

        fun newInstance(fullTransactions: List<FullTransactionData>): FilterListFragment {
            val fragment = FilterListFragment()
            val args = Bundle()
            args.putParcelableArrayList(ARG_FULL_DATA, ArrayList(fullTransactions))
            fragment.arguments = args
            return fragment
        }
    }
}
