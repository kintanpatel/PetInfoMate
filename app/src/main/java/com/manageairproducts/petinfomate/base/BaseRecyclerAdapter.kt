package com.wecare.vehicleinfomate.base

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.TextAppearanceSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.manageairproducts.petinfomate.base.singleClick
import java.util.*
import kotlin.collections.ArrayList


/**
 * @param T model that will represent in ArrayList
 */

abstract class BaseRecyclerAdapter<T, VB : ViewBinding>(val inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB) :
        RecyclerView.Adapter<BaseRecyclerAdapter.MyViewHolder<VB>>(), Filterable {
    private var mObject: ArrayList<T> = ArrayList()
    private var mCopyObjects: ArrayList<T> = ArrayList()
    private var filterConsumer: ArrayList<FilterConsumer<T>> = ArrayList()
    private var mSearching = ""
    private var mContext: Context? = null
    var onItemClick: ((position: Int, data: T) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder<VB> {
        mContext = parent.context
        return MyViewHolder(inflate.invoke(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = mObject.size

    override fun onBindViewHolder(holder: MyViewHolder<VB>, position: Int) {
        holder.binding.root.singleClick {
            onItemClick?.invoke(holder.adapterPosition, getItemAtPosition(holder.adapterPosition))
        }
        populateItem(holder.binding, getItemAtPosition(holder.adapterPosition), holder.adapterPosition)
        applySpannable(holder.binding)
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {

                mSearching = charSequence.toString()

                var list: MutableList<T> = ArrayList()

                val results = FilterResults()
                if (charSequence.toString().trim().isEmpty()) {
                    list = ArrayList(mCopyObjects)
                } else {
                    outerLoop@ for (item in mCopyObjects) {
                        for (fConsume in filterConsumer) {
                            if (fConsume.apply(item).contains(charSequence.toString(), true)) {
                                list.add(item)
                                continue@outerLoop
                            }
                        }
                    }
                }
                results.count = list.size
                results.values = list
                return results
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                mObject = filterResults.values as ArrayList<T>
                notifyDataSetChanged()
            }
        }
    }


    /**
     * @param itemView holder view which is inflate by you
     * @param item which was passed in T
     * @param position adapter position
     */

    abstract fun populateItem(binding: VB, item: T, position: Int)

    class MyViewHolder<VB : ViewBinding>(val binding: VB) : RecyclerView.ViewHolder(binding.root)

    /**
     * @param list add multiple item in list
     */
    fun addAll(@NonNull list: List<T>) {
        mObject = list as ArrayList<T>
        mCopyObjects = ArrayList(list)
        notifyDataSetChanged()
    }

    /**
     * @param item add single  item in list
     */
    fun add(@NonNull item: T) {
        mObject.add(item)
        mCopyObjects.add(item)
        notifyDataSetChanged()
    }

    /**
     * @param item add single  item in list
     */
    fun add(index: Int, @NonNull item: T) {
        mObject.add(index, item)
        mCopyObjects.add(index, item)
        notifyDataSetChanged()
    }

    /**
     * Returns the item at the specified position.
     *
     * @param position index of the item to return
     * @return the item at the specified position or {@code null} when not found
     */
    fun getItemAtPosition(position: Int) = mObject[position]

    /**
     * Clear all data
     */
    fun clear() {
        mObject.clear()
        mCopyObjects.clear()
        notifyDataSetChanged()
    }

    /**
     * Register a Filter to be invoked when an item in this AdapterView has
     * Search
     *
     * @param filterConsumer list of search item
     */
    fun setFilterConsumer(vararg filterConsumer: FilterConsumer<T>) {
        this.filterConsumer.addAll(filterConsumer)
    }

    fun getContext(): Context {
        return mContext!!
    }

    fun getAll(): ArrayList<T> {
        return mObject
    }

    fun getCopyAll(): ArrayList<T> {
        return mCopyObjects
    }


    interface FilterConsumer<T> {
        fun apply(item: T): String
    }


    /**
     * Apply Spannable text based on getSpannableViews id
     */
    private fun applySpannable(itemView: VB) {
        val spannableViews = getSpannableViews(itemView)
        spannableViews.forEach {
            it.text = getSpanText(it.text.toString())
        }
    }

    /**
     * Provide Spannable String which was searched by user
     * Connected with Search mSearchingText
     */
    private fun getSpanText(text: String): SpannableString {
        val spanText = SpannableString(text)
        if (mSearching.isNotEmpty()) {
            val searchWords = mSearching.split(" ")
            searchWords.forEach { word ->
                val startPosition = text.lowercase(Locale.getDefault()).indexOf(word.lowercase(Locale.getDefault()))
                val endPosition = startPosition + word.length
                if (startPosition != -1) {
                    val blueColor = ColorStateList(arrayOf(intArrayOf()), intArrayOf(Color.RED))
                    val highlightSpan = TextAppearanceSpan(null, Typeface.BOLD, -1, blueColor, null)
                    spanText.setSpan(highlightSpan, startPosition, endPosition, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
            }
        }
        return spanText
    }

    /**
     * Override this method and pass List of TextView which you want to apply Spannable behavior
     */
    open fun getSpannableViews(itemView: VB): ArrayList<TextView> {
        return ArrayList()
    }

}