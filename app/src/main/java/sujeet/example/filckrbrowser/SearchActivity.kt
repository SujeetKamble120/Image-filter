/*
  1.This activity is for launching the search page.
  2.Don't forget to add its parent in manifest.
 */

package sujeet.example.filckrbrowser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
    }
}