package ca.kodenav.assignment1v2;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import ca.kodenav.assignment1v2.databinding.ActivityAddEditMovieBinding;

public class AddEditMovieActivity extends AppCompatActivity {
    private ActivityAddEditMovieBinding binding;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private MovieModel movieToEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEditMovieBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        if (intent.hasExtra("movie")) {
            movieToEdit = (MovieModel) intent.getSerializableExtra("movie");
            binding.etTitle.setText(movieToEdit.getTitle());
            binding.etYear.setText(movieToEdit.getYear());
            binding.etThumbnail.setText(movieToEdit.getThumbnailUrl());
            binding.btnSave.setText("Update Movie");
        } else {
            binding.btnSave.setText("Add Movie");
        }

        binding.btnSave.setOnClickListener(v -> {
            String title = binding.etTitle.getText().toString();
            String year = binding.etYear.getText().toString();
            String thumbnail = binding.etThumbnail.getText().toString();

            if (movieToEdit != null) {
                Map<String, Object> updated = new HashMap<>();
                updated.put("title", title);
                updated.put("year", year);
                updated.put("thumbnailUrl", thumbnail);

                db.collection("movies").document(movieToEdit.getId()).update(updated)
                        .addOnSuccessListener(aVoid -> finish());
            } else {
                String userId = auth.getCurrentUser().getUid();
                Map<String, Object> newMovie = new HashMap<>();
                newMovie.put("title", title);
                newMovie.put("year", year);
                newMovie.put("thumbnailUrl", thumbnail);
                newMovie.put("userId", userId);

                db.collection("movies").add(newMovie)
                        .addOnSuccessListener(documentReference -> finish());
            }
        });

        binding.btnCancel.setOnClickListener(v -> finish());
    }
}

