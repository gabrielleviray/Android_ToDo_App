package com.example.simpletodo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Bridge to tell recyclerview how to display the data we give it(list of strings in listofTasks)
 */

// Renders the list of strings in recycler view list item by item
class TaskItemAdapter(val listOfItems: List<String>, val longClickListener: OnLongClickListener): RecyclerView.Adapter<TaskItemAdapter.ViewHolder>() {

    interface OnLongClickListener{
        fun onItemLongClicked(position: Int)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Get context
        val context = parent.context
        // Use context to get a layout inflator
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout for each item
        val contactView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    // Populates data into the item through holder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        // Get data model based on positions
        val item = listOfItems.get(position)

        // set text view to be text for specific task
        holder.textView.text = item
    }

    override fun getItemCount(): Int {
        return listOfItems.size
    }

    // grab references to the views needed so we can populate the data for the views
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        // store references to elements in our layout view

        // declare textView
        val textView: TextView

        init {
            // set textView
            textView = itemView.findViewById(android.R.id.text1)

            itemView.setOnLongClickListener{
                longClickListener.onItemLongClicked(adapterPosition)
                Log.i("Caren", "Long clicked on item: " + adapterPosition)
                true
            }
        }
    }

}
