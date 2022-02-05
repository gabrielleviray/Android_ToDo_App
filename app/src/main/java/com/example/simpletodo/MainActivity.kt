package com.example.simpletodo

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

// In charge of main screen on our app
class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener {
            override fun onItemLongClicked(position: Int) {
                // 1. Remove the item from the List
                listOfTasks.removeAt(position)
                // 2. Notify the adapter that our data set has changed
                adapter.notifyDataSetChanged()

                saveItems()
            }
        }

        // Detect when the user presses the 'Add button'
//        findViewById<Button>(R.id.button).setOnClickListener {
//            // Code in here will be executed when user clicks on a button
//            Log.i("Caren", "User clicked on button")
//        }

        loadItems()

        // Look up recyclerView in Layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)
        // Attach the adapter to the recyclerview to populate items
        recyclerView.adapter = adapter
        // Set layout manager to position the items
        recyclerView.layoutManager = LinearLayoutManager(this)


        // Set up the button and input field, so that the user can enter a task
        // and add it to the list

        val inputTextField = findViewById<EditText>(R.id.addTask)
        // Get a reference to the button
        // and then set an onClick listener
        findViewById<Button>(R.id.button).setOnClickListener {
            // 1. Grab the text the user has inputted in to @id/addTaskField
            val userInputtedTask = inputTextField.text.toString()

            // 2. Add the string to our list of tasks: listOfTasks
            listOfTasks.add(userInputtedTask)

            // Notify the adapter that our data has been updated
            adapter.notifyItemInserted(listOfTasks.size - 1)

            // 3. Reset text field
            inputTextField.setText("")

            saveItems()
        }
    }

    // Save the data that the user has inputted when app is re-launched
    // Save data by writing/reading from a file (import library in gradle file)


    // Create a method to get the data file we need
    // returns file in our Android that stores everything we have
    fun getDataFile() : File {

        // Every lline is going to represent a specific task in our list of tasks
        // need java.io file to use this class(File)
        return File(filesDir, "userData.txt")
    }

    // Need another method to load the items by reading every line in our file
    fun loadItems(){
        try {
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch(ioException: IOException){
            ioException.printStackTrace()
        }
    }

    // Need another method to save the items(equivalent to writing data to a file)
    fun saveItems(){
        // if file doesn't exist or can't find file (avoid app crash)
        try{
            FileUtils.writeLines(getDataFile(), listOfTasks)
        } catch(ioException: IOException)
        {
                ioException.printStackTrace()
            
        } 
    }
}