package ca.kodenav.assignment1v2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ca.kodenav.assignment1v2.databinding.ItemLayoutBinding;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private List<MovieModel> movieList;
    private final Context context;
    private final OnMovieClickListener listener;

    public interface OnMovieClickListener {
        void onEdit(MovieModel movie);
        void onDelete(MovieModel movie);
    }

    public MyAdapter(Context context, List<MovieModel> movieList, OnMovieClickListener listener) {
        this.context = context;
        this.movieList = movieList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemLayoutBinding binding = ItemLayoutBinding.inflate(inflater, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MovieModel movie = movieList.get(position);
        holder.bind(movie, listener);
    }

    @Override
    public int getItemCount() {
        return movieList != null ? movieList.size() : 0;
    }

    public void setMovieList(List<MovieModel> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }
}

