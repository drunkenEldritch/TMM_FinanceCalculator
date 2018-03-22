package com.dreldritch.tmmfinancecalculator.dialogs

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import com.dreldritch.tmmfinancecalculator.R
import com.dreldritch.tmmfinancecalculator.model.entities.CategoryEntitiy
import kotlinx.android.synthetic.main.category_layout.view.*
import kotlinx.android.synthetic.main.fragment_category_dialog.*
import java.util.*


class CategoryDialogFragment : DialogFragment() {

    //TODO Change string mock to db query

    private var mListenerCategoryDialog: OnCategoryInteractionListener? = null
    lateinit var categories: List<CategoryEntitiy>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(arguments != null)
            categories = arguments.getParcelableArrayList(CATEGORIES)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_category_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        category_recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = CategoryAdapter(this@CategoryDialogFragment, categories)
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
        fun onCategoryDialogInteraction(category: String, iconColor: Int)
    }

    companion object {
        const val CATEGORIES = "categories"

        fun newInstance(categories: List<CategoryEntitiy>): CategoryDialogFragment {
            val dialog = CategoryDialogFragment()
            val bundle = Bundle()
            bundle.putParcelableArrayList(CATEGORIES, ArrayList(categories))
            dialog.arguments = bundle
            return dialog
        }
    }

    fun onItemClicked(category: String, color: Int){
        if(mListenerCategoryDialog != null)
            mListenerCategoryDialog!!.onCategoryDialogInteraction(category, color)
    }

    //TODO Remove CategoryDialogFragment from constructor
    //TODO Save & load color from DB?
    class CategoryAdapter(private val dialog: CategoryDialogFragment, private val categories: List<CategoryEntitiy>): RecyclerView.Adapter<CategoryAdapter.ViewHolder>(){

        inner class ViewHolder(val view: View, var iconColor: Int?)
            : RecyclerView.ViewHolder(view), View.OnClickListener{
            init { view.setOnClickListener(this) }

            override fun onClick(v: View?) {
                val category = v!!.category_txt.text.toString()
                dialog.onItemClicked(category, this.iconColor!!)
                dialog.dismiss()
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
            val view = LayoutInflater.from(parent.context).inflate(R.layout.category_layout, parent,false)
            return ViewHolder(view, null)
        }

        override fun getItemCount()= categories.count()

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            holder.view.category_txt.text = categories[position].category

            val background = dialog.resources.getDrawable(R.drawable.category_icon_drawable, null) as GradientDrawable
            background.setColor(categories[position].iconColor)
            holder.view.category_icon.background = background
            holder.iconColor = categories[position].iconColor

            holder.view.category_icon.text = categories[position].category.substring(0..1)
        }
    }

    class IconItemDecorator(private val verticalItemSpace: Int): RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
                           state: RecyclerView.State) {
            outRect.bottom = verticalItemSpace
        }
    }
}// Required empty public constructor
