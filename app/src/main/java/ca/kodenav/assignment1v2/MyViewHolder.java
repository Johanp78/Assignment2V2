package ca.kodenav.assignment1v2;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import ca.kodenav.assignment1v2.databinding.ItemLayoutBinding;

public class MyViewHolder extends RecyclerView.ViewHolder {
    ItemLayoutBinding binding;

    public MyViewHolder(ItemLayoutBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(MovieModel movie, MyAdapter.OnMovieClickListener listener) {
        binding.tvTitle.setText(movie.getTitle());
        binding.tvYear.setText("Year: " + movie.getYear());

        new ImageLoader().loadImage(movie.getThumbnailUrl(), binding.ivPoster);

        binding.btnEdit.setOnClickListener(v -> listener.onEdit(movie));
        binding.btnDelete.setOnClickListener(v -> listener.onDelete(movie));
    }
}



