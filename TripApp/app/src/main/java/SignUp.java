package com.example.tripapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {

    final int REQUEST_CODE = 1;
    public static final String TAG = "SignUp Activity";
    String name;
    String phonenum;
    String email;
    String uid;
    String pass;
    String ConfirmPass;
    Button signup;
    EditText Name;
    EditText PhoneNum;
    EditText Email;
    EditText Password;
    EditText ConfirmPassword;
    boolean isEmailValid;
    boolean isPasswordValid;
    FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);


        Name = (EditText) findViewById(R.id.name);
        PhoneNum = (EditText) findViewById(R.id.phone_num);
        Email = (EditText) findViewById(R.id.email);
        Password = (EditText) findViewById(R.id.passwordSignup);
        ConfirmPassword = (EditText) findViewById(R.id.confirm_password);
        signup = (Button) findViewById(R.id.signup);


        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();


    }

    public void SignUpUser(View view) {

        name = Name.getText().toString();
        phonenum = PhoneNum.getText().toString();
        email = Email.getText().toString();
        pass = Password.getText().toString();
        ConfirmPass = ConfirmPassword.getText().toString();

        if (name.isEmpty() || phonenum.isEmpty() || !ValidateEmailAddress() || !ValidatePassword() || !pass.contentEquals(ConfirmPass)) {
            Toast.makeText(getApplicationContext(), "Error Occured", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    uid = user.getUid();
                    UpdateUI(user);
                } else {
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

    public void UpdateUI(FirebaseUser user) {

        if (user == null) {
            Toast.makeText(getApplicationContext(), "Sorry . An error occured. Please try again.", Toast.LENGTH_SHORT)
                    .show();
        } else {
            Toast.makeText(getApplicationContext(), "Account created. Redirecting...", Toast.LENGTH_SHORT)
                    .show();
        }

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                User value = (User) dataSnapshot.getValue(User.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        String userId = user.getUid();

        User new_user = new User(name, phonenum, email, pass,userId);
        myRef.child("users").child(userId).setValue(new_user);

        Intent intent = new Intent(getApplicationContext(),MainPage.class);
        intent.putExtra("Name",new_user.name);
        intent.putExtra("Email",new_user.email);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);




    }


}
