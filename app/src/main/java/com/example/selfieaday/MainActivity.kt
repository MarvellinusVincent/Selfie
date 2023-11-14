package com.example.selfieaday

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Main activity of the application responsible for launching the app and displaying the main UI.
 */
class MainActivity : AppCompatActivity() {

    /**
     * Called when the activity is first created. This is where you should do all of your normal
     * static set up: create views, bind data to lists, etc.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down
     * then this Bundle contains the data it most recently supplied in
     * [onSaveInstanceState].
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the content view to the layout defined in activity_main.xml
        setContentView(R.layout.activity_main)
    }
}
