package ca.kodenav.assignment1v2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import ca.kodenav.assignment1v2.databinding.ActivityRegisterBinding;

public class Register extends AppCompatActivity {

    private FirebaseAuth mAuth;


    ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        binding.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.emailEditText.getText().toString().trim();
                String password = binding.passwordEditText.getText().toString().trim();
                registerUser(email, password);
            }
        });

    }

    private void registerUser(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Log.d("tag","createUserWithEmail:success");
                    Intent intentObj = new Intent(getApplicationContext(), Login.class);
                    startActivity(intentObj);
                } else{
                    Log.d("tag","CreateUserWithEmail:failure", task.getException());
                    Toast.makeText(Register.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}