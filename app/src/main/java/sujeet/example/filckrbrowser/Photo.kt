/*
  1.Image will give url or photo to show in initial list and link will provide url of full size picture.
  2.Here, image is the field for m object.
*/

package sujeet.example.filckrbrowser

class Photo(val title: String, val author: String, val authorId: String, val link: String, val tags: String, val image: String)
{
    override fun toString(): String
    {
        return "Photo(title='$title', author='$author', authorId='$authorId', link='$link', tags='$tags', image='$image')"
    }
}