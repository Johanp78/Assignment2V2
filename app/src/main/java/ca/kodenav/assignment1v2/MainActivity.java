package ca.kodenav.assignment1v2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import ca.kodenav.assignment1v2.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FirebaseAuth auth;
    private MovieViewModel movieViewModel;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();

        adapter = new MyAdapter(this, new ArrayList<>(), new MyAdapter.OnMovieClickListener() {
            @Override
            public void onEdit(MovieModel movie) {
                Intent intent = new Intent(MainActivity.this, AddEditMovieActivity.class);
                intent.putExtra("movie", movie);
                startActivity(intent);
            }

            @Override
            public void onDelete(MovieModel movie) {
                FirebaseFirestore.getInstance().collection("movies").document(movie.getId())
                        .delete()
                        .addOnSuccessListener(unused -> movieViewModel.loadMovies(auth.getCurrentUser().getUid()))
                        .addOnFailureListener(e -> Toast.makeText(MainActivity.this, "Failed to delete", Toast.LENGTH_SHORT).show());
            }
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

        binding.btnAddMovie.setOnClickListener(v -> {
            startActivity(new Intent(this, AddEditMovieActivity.class));
        });

        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        movieViewModel.getMovies().observe(this, movies -> {
            adapter.setMovieList(movies);
        });

        // Carga inicial de películas
        if (auth.getCurrentUser() != null) {
            movieViewModel.loadMovies(auth.getCurrentUser().getUid());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (auth.getCurrentUser() != null) {
            movieViewModel.loadMovies(auth.getCurrentUser().getUid());
        }
    }
}

