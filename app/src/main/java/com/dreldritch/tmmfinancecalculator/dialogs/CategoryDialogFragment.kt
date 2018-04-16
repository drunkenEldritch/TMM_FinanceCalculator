package com.dreldritch.tmmfinancecalculator.dialogs

import android.app.Application
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.Toast
import com.dreldritch.tmmfinancecalculator.R
import com.dreldritch.tmmfinancecalculator.model.EntryDbRepository
import com.dreldritch.tmmfinancecalculator.model.entities.CategoryEntity
import com.dreldritch.tmmfinancecalculator.viewmodel.AddTransactionViewModel
import kotlinx.android.synthetic.main.category_layout.view.*
import kotlinx.android.synthetic.main.fragment_category_dialog.*
import java.util.*


class CategoryDialogFragment : DialogFragment() {

    private var mListenerCategoryDialog: OnCategoryInteractionListener? = null
    private lateinit var categories: List<CategoryEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(arguments != null)
            categories = arguments!!.getParcelableArrayList(CATEGORIES)
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

        category_add_btn.setOnClickListener {
            val text = category_new_edit_text.text.toString()

            if(text.isEmpty()){
                Toast.makeText(context, R.string.no_category_error, Toast.LENGTH_SHORT).show()
            }else{
                val colors = resources.getIntArray(R.array.icon_colors)
                val color = colors[Random().nextInt(colors.size)]
                val category = CategoryEntity(null, text, color)

                val viewmodel = ViewModelProviders.of(activity!!).get(AddTransactionViewModel::class.java)
                viewmodel.insertCategory(category)
                viewmodel.getCategoryEntity(category.category).observe(activity!!, android.arch.lifecycle.Observer {category ->
                    if(category != null){
                        onItemClicked(category)
                        dismiss()
                    }
                })
            }
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
        fun onCategoryDialogInteraction(categoryEntity: CategoryEntity)
    }

    companion object {
        const val CATEGORIES = "categories"

        fun newInstance(categories: List<CategoryEntity>): CategoryDialogFragment {
            val dialog = CategoryDialogFragment()
            val bundle = Bundle()
            bundle.putParcelableArrayList(CATEGORIES, ArrayList(categories))
            dialog.arguments = bundle
            return dialog
        }
    }

    fun onItemClicked(categoryEntity: CategoryEntity){
        if(mListenerCategoryDialog != null)
            mListenerCategoryDialog!!.onCategoryDialogInteraction(categoryEntity)
    }

    //TODO Remove CategoryDialogFragment from constructor
    class CategoryAdapter(private val dialog: CategoryDialogFragment, private val categories: List<CategoryEntity>): RecyclerView.Adapter<CategoryAdapter.ViewHolder>(){

        val categoryMap = categories.map { it.category to it }.toMap()

        inner class ViewHolder(val view: View, var iconColor: Int?)
            : RecyclerView.ViewHolder(view), View.OnClickListener{
            init { view.setOnClickListener(this) }

            override fun onClick(v: View?) {
                val categoryEntity = categoryMap[v!!.category_txt.text.toString()]
                dialog.onItemClicked(categoryEntity!!)
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
