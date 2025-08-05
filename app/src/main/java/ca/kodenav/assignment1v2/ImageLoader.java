package ca.kodenav.assignment1v2;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ImageLoader {

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final OkHttpClient client = new OkHttpClient();

    public void loadImage(String imageUrl, ImageView imageView) {
        executor.execute(() -> {
            try {
                Request request = new Request.Builder().url(imageUrl).build();
                Response response = client.newCall(request).execute();

                if (response.isSuccessful() && response.body() != null) {
                    InputStream inputStream = response.body().byteStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    handler.post(() -> imageView.setImageBitmap(bitmap));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

