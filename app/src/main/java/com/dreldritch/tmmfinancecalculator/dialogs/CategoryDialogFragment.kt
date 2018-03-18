package com.dreldritch.tmmfinancecalculator.dialogs

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import com.dreldritch.tmmfinancecalculator.R
import com.dreldritch.tmmfinancecalculator.model.entities.CategoryEntitiy
import kotlinx.android.synthetic.main.category_layout.view.*
import kotlinx.android.synthetic.main.fragment_category_dialog.*
import java.util.*
import android.view.MotionEvent
import android.view.GestureDetector
import android.widget.TextView


class CategoryDialogFragment : DialogFragment() {

    //TODO Change string mock to db query

    private lateinit var dialog: CategoryDialogFragment

    private var mListenerCategoryDialog: OnCategoryInteractionListener? = null

    init {
        dialog = this
    }

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
            adapter = CategoryAdapter(dialog, listOf(CategoryEntitiy(0, "Cat1"), CategoryEntitiy(1, "Cat2")))
            addItemDecoration(IconItemDecorator(20))
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
        fun onCategoryDialogInteraction(category: String, color: Int)
    }

    companion object {
        fun newInstance(): CategoryDialogFragment {
            return CategoryDialogFragment()
        }
    }

    fun onItemClicked(category: String, color: Int){
        if(mListenerCategoryDialog != null)
            mListenerCategoryDialog!!.onCategoryDialogInteraction(category, color)
    }

    //TODO Remove CategoryDialogFragment from constructor
    //TODO Save & load color from DB?
    class CategoryAdapter(private val dialog: CategoryDialogFragment, private val categories: List<CategoryEntitiy>): RecyclerView.Adapter<ViewHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
            val view = LayoutInflater.from(parent.context).inflate(R.layout.category_layout, parent,false)
            return ViewHolder(view, dialog, null)
        }

        override fun getItemCount()= categories.count()

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            holder.view.category_txt.text = categories[position].category

            val random = Random()
            val shape = dialog.resources.getDrawable(R.drawable.category_icon_drawable, null) as GradientDrawable
            val color = Color.argb(255, random.nextInt(255), random.nextInt(255), random.nextInt(255))
            shape.setColor(color)
            holder.view.category_icon.background = shape
            holder.iconColor = color


            holder.view.category_icon.text = categories[position].category.substring(0..1)
        }
    }

    class ViewHolder(val view: View, private val dialog: CategoryDialogFragment, var iconColor: Int?) : RecyclerView.ViewHolder(view), View.OnClickListener{
        init { view.setOnClickListener(this) }

        override fun onClick(v: View?) {
            val category = v!!.category_txt.text.toString()
            Log.d("RecyclerItemClick", category)
            val drawable = v.category_icon.background as GradientDrawable
            dialog.onItemClicked(category, this.iconColor!!)
            dialog.dismiss()
        }
    }

    class IconItemDecorator(private val verticalItemSpace: Int): RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
                           state: RecyclerView.State) {
            outRect.bottom = verticalItemSpace
        }
    }
}// Required empty public constructor
