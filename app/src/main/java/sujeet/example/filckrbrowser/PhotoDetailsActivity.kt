/*
  1.This activity is for showing full screen photo.
  2.In its layout i.e. content_photo_details, sometimes image can get longer than screen hence we use scrollbarView widget.
  3.But remember that scrollView can only contain a single widget.
  4.If two or more views are there in card then it will be in top of one another hence use linear layout inside the card.
 */

package sujeet.example.filckrbrowser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class PhotoDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_details)
    }
}