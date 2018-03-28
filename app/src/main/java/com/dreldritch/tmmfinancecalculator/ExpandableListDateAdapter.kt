package com.dreldritch.tmmfinancecalculator

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import com.dreldritch.tmmfinancecalculator.model.entities.DateEntity
import com.dreldritch.tmmfinancecalculator.model.entities.EntryDataObject
import com.dreldritch.tmmfinancecalculator.model.entities.FullTransactionData
import kotlinx.android.synthetic.main.exp_date_header.view.*
import kotlinx.android.synthetic.main.exp_date_list_item.view.*

class ExpandableListDateAdapter(val context: Context, private val groupList:List<DateEntity>,
                                private val groupChildMap: Map<Long, List<FullTransactionData>>)
    : BaseExpandableListAdapter() {

    private val DATE_ADAPTER_TAG = "DateAdapter"

    override fun getGroupCount(): Int {
        val numGroup = groupList.size
        Log.d(DATE_ADAPTER_TAG, "numGroup: $numGroup")
        return numGroup
    }

    override fun getGroup(p0: Int): Any = groupList[p0]

    override fun getGroupView(p0: Int, p1: Boolean, p2: View?, p3: ViewGroup?): View {
        val dateEntity = groupList[p0]
        val layoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val groupLayout = layoutInflater.inflate(R.layout.exp_date_header, null)
        groupLayout.exp_header_txt.text = dateEntity.date

        return groupLayout
    }

    //override fun getChildrenCount(p0: Int) = groupChildMap[groupList[p0].id]!!.size
    override fun getChildrenCount(p0: Int): Int{
        val numChild = groupChildMap[groupList[p0].id]!!.size
        Log.d(DATE_ADAPTER_TAG, "ChildrenCount: $numChild")
        return numChild
    }

    override fun getChild(p0: Int, p1: Int): Any = groupChildMap[groupList[p0].id]!![p1]

    override fun getChildView(p0: Int, p1: Int, p2: Boolean, p3: View?, p4: ViewGroup?): View {
        val entryEntity = groupChildMap[groupList[p0].id]?.get(p1)
        val layoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val childLayout = layoutInflater.inflate(R.layout.exp_date_list_item, null)
        childLayout.exp_list_item_name.text = entryEntity!!.name

        return childLayout
    }

    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
        return false
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getGroupId(p0: Int): Long {
        return groupList[p0].id!!
    }

    override fun getChildId(p0: Int, p1: Int): Long {
        return groupChildMap[groupList[p0].id]!![p1].id!!
    }
}