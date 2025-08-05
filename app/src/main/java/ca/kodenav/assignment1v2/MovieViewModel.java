package ca.kodenav.assignment1v2;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MovieViewModel extends ViewModel {

    MovieModel movieModel = new MovieModel();
    private final MutableLiveData<List<MovieModel>> movies = new MutableLiveData<>();

    public LiveData<List<MovieModel>> getMovies() {
        return movies;
    }

    public void loadMovies(String userId) {
        FirebaseFirestore.getInstance().collection("movies")
                .whereEqualTo("userId", userId)
                .limit(20)
                .get()
                .addOnSuccessListener(query -> {
                    List<MovieModel> movieList = new ArrayList<>();
                    for (DocumentSnapshot doc : query.getDocuments()) {
                        MovieModel movie = doc.toObject(MovieModel.class);
                        if (movie != null) {
                            movie.setId(doc.getId());
                            movieList.add(movie);
                        }
                    }
                    movies.postValue(movieList);
                })
                .addOnFailureListener(e -> {
                   Log.e("MainActivity", "Error loading movies", e);
                });
    }


}
