package com.dreldritch.tmmfinancecalculator.dialogs

import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.dreldritch.tmmfinancecalculator.R
import com.dreldritch.tmmfinancecalculator.model.entities.CategoryEntitiy
import kotlinx.android.synthetic.main.category_layout.view.*
import kotlinx.android.synthetic.main.fragment_category_dialog.*

class CategoryDialogFragment : DialogFragment() {

    //TODO Change string mock to db query

    private lateinit var recyclerView: RecyclerView

    private var mListenerCategoryDialog: OnCategoryInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_category_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*val viewManager = LinearLayoutManager(context)
        val viewAdapter = MyAdapter(arrayOf("E1", "E2"))

        recyclerView = getView()!!.findViewById<RecyclerView>(R.id.category_recycler_view).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }*/

        category_recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = CategoryAdapter(listOf(CategoryEntitiy(0, "Cat1"), CategoryEntitiy(1, "Cat2")))
        }
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

    class CategoryAdapter(val categories: List<CategoryEntitiy>): RecyclerView.Adapter<CategoryAdapter.ViewHolder>(){

        class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder{
            val view = LayoutInflater.from(parent.context).inflate(R.layout.category_layout, parent,false)
            return ViewHolder(view)
        }

        override fun getItemCount()= categories.count()


        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.view.category_txt.text = categories[position].category
        }
    }

    class MyAdapter(private val myDataset: Array<String>) :
            RecyclerView.Adapter<MyAdapter.ViewHolder>() {

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder.
        // Each data item is just a string in this case that is shown in a TextView.
        class ViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)


        // Create new views (invoked by the layout manager)
        override fun onCreateViewHolder(parent: ViewGroup,
                                        viewType: Int): MyAdapter.ViewHolder {
            // create a new view
            val textView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.test_text_view, parent, false) as TextView
            // set the view's size, margins, paddings and layout parameters ...
            return ViewHolder(textView)
        }

        // Replace the contents of a view (invoked by the layout manager)
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.textView.text = myDataset[position]
        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount() = myDataset.size
    }
}// Required empty public constructor
