/*
  1.We can't catch an exception that is thrown by code executing in different code.Hence if our code has an
    error, onError() will catch it.
  2.ArrayList->dynamic, list->static.
  3.We use json java package to parse the data.
  4.Get the whole 'items' array->extract individual elements from it.Hence iterate over the array to get the elements.
  5.Here, items is the array of object, so we use getObject method to get individual elements.
 */

package sujeet.example.filckrbrowser

import android.os.AsyncTask
import android.util.Log
import org.json.JSONObject
import java.lang.Exception

class GetFlickrJsonData(private val listener:OnDataAvailable): AsyncTask<String,Void,ArrayList<Photo>>() {
    private val TAG = "GetFlickrJsonData"

    interface OnDataAvailable
    {
        fun onDataAvailable(data:List<Photo>)
        fun onError(exception: Exception)
    }
    override fun doInBackground(vararg params: String): ArrayList<Photo>
    {
        Log.d(TAG,"DoInBackground starts")
        val photoList=ArrayList<Photo>()
        try
        {
            val jsonData = JSONObject(params[0])
            val itemsArray = jsonData.getJSONArray("items")

            for (i in 0 until itemsArray.length())
            {
                val jsonPhoto = itemsArray.getJSONObject(i)
                val title = jsonPhoto.getString("title")
                val author = jsonPhoto.getString("author")
                val authorId = jsonPhoto.getString("author_id")
                val tags = jsonPhoto.getString("tags")

                val jsonMedia = jsonPhoto.getJSONObject("media")
                val photoUrl = jsonMedia.getString("m")
                val link = photoUrl.replaceFirst("_m.jpg", "_b.jpg")

                val photoObject = Photo(title, author, authorId, link, tags, photoUrl)

                photoList.add(photoObject)
                Log.d(TAG, ".doInBackground $photoObject")
            }
        }
        catch (e:Exception)
        {
            e.printStackTrace()
            cancel(true)
            listener.onError(e)
        }
        Log.d(TAG,"doInBackground ends")
        return photoList
    }

    override fun onPostExecute(result: ArrayList<Photo>) {
        super.onPostExecute(result)
        listener.onDataAvailable(result)
    }
}