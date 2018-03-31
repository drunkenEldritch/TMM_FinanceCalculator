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
import com.dreldritch.tmmfinancecalculator.model.entities.DateEntity
import com.dreldritch.tmmfinancecalculator.model.entities.FullTransactionData
import kotlinx.android.synthetic.main.fragment_filter_list.*

class FilterListFragment: Fragment() {

    // TODO: Rename and change types of parameters
    private var mDates: ArrayList<DateEntity>? = null
    private var mFullTransactions: ArrayList<FullTransactionData>? = null
    private var mFullDataMap: HashMap<Long, List<FullTransactionData>>? = null

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(arguments != null){
            mDates = arguments.getParcelableArrayList<DateEntity>(ARG_DATES)
            mFullTransactions = arguments.getParcelableArrayList<FullTransactionData>(ARG_FULL_DATA)
        }
        mFullDataMap = getDateMap()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater!!.inflate(R.layout.fragment_filter_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exp_list_view.setAdapter(ExpandableListDateAdapter(context, mDates!!, mFullDataMap!!))
    }

    // TODO: Rename method, update argument and hook method into UI event
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
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_DATES = "dates"
        private val ARG_FULL_DATA = "fullData"

        fun newInstance(dates: List<DateEntity>, fullTransactions: List<FullTransactionData>): FilterListFragment {
            val fragment = FilterListFragment()
            val args = Bundle()
            args.putParcelableArrayList(ARG_DATES, ArrayList(dates))
            args.putParcelableArrayList(ARG_FULL_DATA, ArrayList(fullTransactions))
            fragment.arguments = args
            return fragment
        }
    }

    private fun getDateMap(): HashMap<Long, List<FullTransactionData>>{
        var dateMap = HashMap<Long, List<FullTransactionData>>()
        if(mDates != null){
            for(date in mDates!!){
                dateMap.put(date.id!!, mFullTransactions!!.filter { it.date_id == date.id})
            }
        }
        return dateMap
    }
}// Required empty public constructor
