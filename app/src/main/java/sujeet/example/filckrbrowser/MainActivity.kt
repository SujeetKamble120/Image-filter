/*
  1.What we have to do is notify the calling class that tae data is being downloaded and make data available to it.
  2.Calling class uses getRawData to download the data from some URL so getRawData does the downloading and when it
    finishes,it calls the class back using a callback method.(Like button tapping).
  3.We create a function that the called object will call when something interesting happens.
  4.We implemented interface because we cannot guarantee that MainActivity will have the onDownloadComplete function
  5.To keep content below the toolbar,set layout_behaviour attribute to appbar_scrolling_view_behaviour
  6.Coordinator layout is intended as a top level layout when we want the child views to interact so that you can do neat
    things like toolbar fold up while scrolling,etc and also it can include layouts in different file.It enables automatic
    motion of the components.
*/

package sujeet.example.filckrbrowser

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity() , GetRawData.OnDownloadComplete,GetFlickrJsonData.OnDataAvailable{
    private val TAG="MainActivity"
    private val flickrRecyclerViewAdapter=FlickrRecyclerViewAdapter(ArrayList())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)//This method sets the toolbar as the app bar for the activity.
        recycler_view.layoutManager=LinearLayoutManager(this)
        recycler_view.adapter=flickrRecyclerViewAdapter
        val url = createUri("https://api.flickr.com/services/feeds/photos_public.gne",
            "android,oreo","en-us", true)
        val getRawData=GetRawData(this) //to pass correctly the current activity instance
        getRawData.execute(url)

    }

    private fun createUri(baseURL:String,searchCriteria:String,lang:String,matchAll:Boolean):String
    {
        return Uri.parse(baseURL).
        buildUpon().
        appendQueryParameter("tags", searchCriteria).
        appendQueryParameter("tagmode", if (matchAll) "ALL" else "ANY").
        appendQueryParameter("lang", lang).
        appendQueryParameter("format", "json").
        appendQueryParameter("nojsoncallback", "1").
        build().toString()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        Log.d(TAG, "onCreateOptionsMenu called")
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Log.d(TAG, "onOptionsItemSelected called")
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDownloadComplete(result:String,status: DownloadStatus)
    {
        if(status==DownloadStatus.OK)
        {
            val getFlickrJsonData=GetFlickrJsonData(this)
            getFlickrJsonData.execute(result)
        }
        else
        {
            Log.d(TAG,"Download unsuccessful and the error message is $result")
        }
    }

    override fun onDataAvailable(data: List<Photo>) {
        Log.d(TAG,"onDataAvailable called")
        flickrRecyclerViewAdapter.loadNewData(data)
    }

    override fun onError(exception: Exception) {
        Log.d(TAG,"Error is ${exception.message}")
    }
}