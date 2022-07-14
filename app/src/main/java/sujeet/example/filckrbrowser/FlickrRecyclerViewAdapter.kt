/*
    1.PhotoList is a source for adapter which will be provided by mainActivity
    2.In recycler view, view holder pattern is mandatory while in listView, it is not.
    3.ViewHolder class should be created outside the adapter class to avoid memory leaks.
    4.Unlike listView, RecyclerView will always provide a view holder when it calls onBindViewHolder.
    5.onCreateViewHolder: inflates views from layout file and is called by layout manager when it needs new view.
    6.Attaching a view is nothing but adding a view to parent layout(just like dragging the views or adding the views as
      done in youtube app) that is what attach to root parameter.As recyclerView takes care of it, we set it to false.
    7.If second parameter of inflate is set to null, then inflater has no way of knowing things like what themes should
      be applied i.e. styling of widget is default.
    8.So onBindViewHolder method's called by the recycler view when it wants new data to be stored in a view holder so
      that it can display it.It is called by layout manager when it wants to fill data in existing view.
    9.Picasso library can be very handy if we want to add image but we have URL.It also caches the image hence preventing
      re-downloading.There should be only one picasso object in our app.
   10.Picasso downloads image on the background thread.
*/

package sujeet.example.filckrbrowser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class FlickrViewHolder(view: View) : RecyclerView.ViewHolder(view)
{
    var thumbnail: ImageView = view.findViewById(R.id.thumbnail)
    var title: TextView = view.findViewById(R.id.titleRecycleLayout)
}

class FlickrRecyclerViewAdapter(var photoList:List<Photo>): RecyclerView.Adapter<FlickrViewHolder>() {
    private val TAG="FlickrRecyclerViewAdapt"//Log tag can contain only 23 characters.
    //just initializes the view but doesn't fill the date
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlickrViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.browse, parent, false)
        return FlickrViewHolder(view)
    }

    fun loadNewData(newPhotos: List<Photo>) {
        photoList = newPhotos
        notifyDataSetChanged()
    }

    fun getPhoto(position: Int): Photo? {
        return if (photoList.isNotEmpty()) photoList[position] else null
    }
    //fetches the appropriate data and uses the data to fill in the view holder's layout
    //binds data to viewHolder or fills in the data at position
    override fun onBindViewHolder(holder: FlickrViewHolder, position: Int) {
        val photoItem = photoList[position]
        //Log.d(TAG, ".onBindViewHolder: ${photoItem.title} --> $position")
        Picasso.get().load(photoItem.image)
            .error(R.drawable.placeholder)
            .placeholder(R.drawable.placeholder)
            .into(holder.thumbnail)

        holder.title.text = photoItem.title
    }

    override fun getItemCount(): Int {
        return if(photoList.isNotEmpty()) photoList.size
        else 0
    }
}