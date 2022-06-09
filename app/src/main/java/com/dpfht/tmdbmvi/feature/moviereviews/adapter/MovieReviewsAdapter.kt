package com.dpfht.tmdbmvi.feature.moviereviews.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dpfht.tmdbmvi.data.model.remote.Review
import com.dpfht.tmdbmvi.databinding.RowReviewBinding
import com.squareup.picasso.Picasso
import javax.inject.Inject

class MovieReviewsAdapter @Inject constructor(
  private val reviews: ArrayList<Review>
): RecyclerView.Adapter<MovieReviewsAdapter.ReviewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewHolder {
    val binding = RowReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    return ReviewHolder(binding)
  }

  override fun getItemCount(): Int {
    return reviews.size
  }

  override fun onBindViewHolder(holder: ReviewHolder, position: Int) {
    holder.bindData(reviews[position])
  }

  class ReviewHolder(private val binding: RowReviewBinding): RecyclerView.ViewHolder(binding.root) {

    fun bindData(review: Review) {
      binding.tvAuthor.text = review.author
      binding.tvContent.text = review.content

      var imageUrl = review.authorDetails?.avatarPath
      if (imageUrl?.startsWith("/") == true) {
        imageUrl = imageUrl.replaceFirst("/", "")
      }
      if (imageUrl != null && imageUrl.isNotEmpty()) {
        Picasso.get().load(imageUrl)
          .error(android.R.drawable.ic_menu_close_clear_cancel)
          //.placeholder(R.drawable.loading)
          .into(binding.ivAuthor)
      }
    }
  }
}
