package com.example.tripapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SignIn extends AppCompatActivity {

    final int REQUEST_CODE = 1;
    private EditText Email;
    String Getname;
    private EditText Password;
    private FirebaseAuth mAuth;
    private ProgressBar mProgress;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference myRef;
    FirebaseUser fb;
    private Button btn;
    List<User> _users = new ArrayList<>();
    List<User> user_name = new ArrayList<>();
    String uid;
    boolean isNameValid, isEmailValid, isPhoneValid, isPasswordValid;
    public static final String TAG = "Inside LoginActivity";
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Email = (EditText) findViewById(R.id.email);
        Password = (EditText) findViewById(R.id.password);
        btn = (Button) findViewById(R.id.LoginBut);
        mProgress = (ProgressBar) findViewById(R.id.loading);
        mAuth = FirebaseAuth.getInstance();


        fb = mAuth.getCurrentUser();
        String n = GetName(fb);
        if (fb != null) {
            Intent intent = new Intent(getApplicationContext(), MainPage.class);
            intent.putExtra("Name", n);
            intent.putExtra("Email", fb.getEmail());
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        HideProgressBar();

        //add user in the list of users declared globally
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference().child("users");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User data = new User();
                    data = ds.getValue(User.class);
                    data.setUID(ds.getKey());
                    _users.add(data);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


    }

    public void SignInUser(View view) {

        if (!ValidateEmailAddress() || !ValidatePassword()) {
            return;
        }

        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();
        ShowProgressBar();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            HideProgressBar();
                            UpdateUI();
                            Toast.makeText(getApplicationContext(), "User Not Logged In", Toast.LENGTH_SHORT).show();
                        } else {
                            HideProgressBar();
                            Toast.makeText(getApplicationContext(), "Error Occured", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    public boolean ValidatePassword() {

        // Check for a valid password.
        if (Password.getText().toString().isEmpty()) {
            Password.setError(getResources().getString(R.string.password_error));
            isPasswordValid = false;
        } else if (Password.getText().length() < 6) {
            Password.setError(getResources().getString(R.string.error_invalid_password));
            isPasswordValid = false;
        } else {
            isPasswordValid = true;
        }
        return isPasswordValid;
    }

    public boolean ValidateEmailAddress() {
        // Check for a valid email address.

        if (Email.getText().toString().isEmpty()) {
            Email.setError(getResources().getString(R.string.email_error));
            isEmailValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(Email.getText().toString()).matches()) {
            Email.setError(getResources().getString(R.string.error_invalid_email));
            isEmailValid = false;
        } else {
            isEmailValid = true;
        }

        return isEmailValid;
    }

    public void UpdateUI() {

        FirebaseUser User = mAuth.getCurrentUser();
        String name = null;

        if (User == null) {
            return;
        } else {
            uid = User.getUid();
            for (User user : _users) {
                if (user.getUID().contentEquals(uid)) {
                    name = user.getName();
                }
            }

        }

        final Bundle params = new Bundle();
        params.putString("ID",User.getUid());
        params.putString("Email",User.getEmail());
        params.putString("Name",name);
        mFirebaseAnalytics.logEvent("SignIn",params);

        Intent intent = new Intent(getApplicationContext(), MainPage.class);
        intent.putExtra("Name", name);
        intent.putExtra("Email", User.getEmail());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);


    }

    public void ShowProgressBar() {
        mProgress.setIndeterminate(true);
        mProgress.setVisibility(View.VISIBLE);
    }

    public void HideProgressBar() {
        mProgress.setVisibility(View.GONE);
    }

    public String GetName(FirebaseUser fb) {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference().child("users");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User data = new User();
                    data = ds.getValue(User.class);
                    data.setUID(ds.getKey());
                    user_name.add(data);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        for (User user : user_name) {
            if (user.getUID().contentEquals(fb.getUid())) {
                Getname = user.getName();
                Toast.makeText(getApplicationContext(), Getname, Toast.LENGTH_SHORT).show();
            }
        }
        return Getname;

    }


}
