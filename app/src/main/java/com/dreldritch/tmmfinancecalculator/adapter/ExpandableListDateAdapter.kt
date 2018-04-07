package com.dreldritch.tmmfinancecalculator.adapter

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import com.dreldritch.tmmfinancecalculator.R
import com.dreldritch.tmmfinancecalculator.model.entities.FullTransactionData
import kotlinx.android.synthetic.main.exp_date_header.view.*
import kotlinx.android.synthetic.main.exp_date_list_item.view.*
import java.text.DecimalFormat
import java.util.*

class ExpandableListDateAdapter(val context: Context, private val transactionList:List<FullTransactionData>)
    : BaseExpandableListAdapter() {

    private val DATE_ADAPTER_TAG = "DateAdapter"

    private val dateList = transactionList.map{ it.date}.toSet().toList()
    private val groupChildMap = dateList.map { date -> date to transactionList.filter { date == it.date}}.toMap()

    override fun getGroupCount(): Int {
        Log.d(DATE_ADAPTER_TAG, "numGroup: ${dateList.size}")
        return dateList.size
    }

    override fun getGroup(p0: Int): Any = dateList[p0]

    override fun getGroupView(p0: Int, p1: Boolean, p2: View?, p3: ViewGroup?): View {
        val layoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val groupLayout = layoutInflater.inflate(R.layout.exp_date_header, null)
        groupLayout.exp_header_date.text = dateList[p0]
        groupLayout.exp_header_total_sum.text = String.format(Locale.ROOT, "%.2f", groupChildMap[dateList[p0]]?.sumByDouble { it.price })

        return groupLayout
    }

    override fun getChildrenCount(p0: Int): Int{
        Log.d(DATE_ADAPTER_TAG, "ChildrenCount: ${groupChildMap[dateList[p0]]!!.size}")
        return groupChildMap[dateList[p0]]!!.size
    }

    override fun getChild(p0: Int, p1: Int): Any = groupChildMap[dateList[p0]]!![p1]

    override fun getChildView(p0: Int, p1: Int, p2: Boolean, p3: View?, p4: ViewGroup?): View {

        val transactionEntity = groupChildMap[dateList[p0]]?.get(p1)

        val layoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val childLayout = layoutInflater.inflate(R.layout.exp_date_list_item, null)

        childLayout.exp_list_item_name.text = transactionEntity!!.name
        childLayout.exp_list_item_price.text = String.format(Locale.ROOT, "%.2f", transactionEntity!!.price)

        /*if(transactionEntity.category_id != null){
            childLayout.exp_list_category_icon.apply {
                text = transactionEntity.category?.substring(0..1)
                val shape = resources.getDrawable(R.drawable.category_icon_drawable, null) as GradientDrawable
                shape.setColor(transactionEntity.icon_color!!)
                background = shape
            }
        }*/

            childLayout.exp_list_category_icon.apply {
                text = transactionEntity.category?.substring(0..1)
                val shape = resources.getDrawable(R.drawable.category_icon_drawable, null) as GradientDrawable

                if(transactionEntity.icon_color != null) shape.setColor(transactionEntity.icon_color)
                else shape.setColor(ContextCompat.getColor(context, R.color.icon_default))
                background = shape
            }


        return childLayout
    }

    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
        return false
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getGroupId(p0: Int): Long {
        return transactionList.filter { it.date == dateList[p0] }[0].date_id!!
    }

    override fun getChildId(p0: Int, p1: Int): Long {
        return groupChildMap[dateList[p0]]!![p1].id!!
    }
}