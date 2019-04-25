package id.ac.umn.projectuas;

import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    EditText inputEmail,inputPassword;
    ProgressBar progressBar;
    Button btnSignIn,btnSignUp,btnResetPassword,btnRegister,btnLogin;
    DatabaseReference reff;
    profile profile;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get Firebase auth instance
        mAuth = FirebaseAuth.getInstance();

        btnSignIn = findViewById(R.id.sign_in_button);
        btnSignUp = findViewById(R.id.sign_up_button);
        inputEmail =  findViewById(R.id.email);
        inputPassword =  findViewById(R.id.password);
        progressBar =  findViewById(R.id.progressBar);
        btnResetPassword =  findViewById(R.id.btn_reset_password);

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ResetPasswordActivity.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //startActivitynya di taro abis pengecekan setelah dia selesai login
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(MainActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    startActivity(new Intent(MainActivity.this, uploadfile.class));
                                }
                            }
                        });

            }
        });
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        progressBar.setVisibility(View.GONE);
//    }
//
//        //punyakepon
//        mAuth = FirebaseAuth.getInstance();
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        //REGISTER FUNCTION
//        txtuName = findViewById(R.id.etUserName);
//        txtpWord = findViewById(R.id.etPassWord);
//        btn1 = findViewById(R.id.btnRegister);
//        btn2 = findViewById(R.id.btnLogin);
//        profile = new profile();
//        reff = FirebaseDatabase.getInstance().getReference().child("profile");
//
//        btn1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                profile.setPassword(txtpWord.getText().toString().trim());
//                profile.setUsername(txtuName.getText().toString().trim());
//
//                reff.push().setValue(profile);
//
//                Toast.makeText(MainActivity.this, "Data inserted", Toast.LENGTH_LONG);
//            }
//        });
//        //END OF REGISTER FUNCTION
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//    }
//
//    private void createAccount(String email, String password) {
//        //Log.d(Tag, "createAccount:" + email);
//        if (!validateForm()) {
//            return;
//        }
//
//        // showProgressDialog();
//
//        // [START create_user_with_email]
//        mAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                           // Log.d(Tag, "createUserWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                        } else {
//                            // If sign in fails, display a message to the user.
//                           // Log.w(Tag, "createUserWithEmail:failure", task.getException());
//                            Toast.makeText(MainActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//
//                        // [START_EXCLUDE]
//                        // hideProgressDialog();
//                        // [END_EXCLUDE]
//                    }
//                });
//        // [END create_user_with_email]
//    }
//
//    private void signIn(String email, String password) {
//       // Log.d(Tag, "signIn:" + email);
//        Toast.makeText(MainActivity.this,"Login",Toast.LENGTH_LONG);
//        if (!validateForm()) {
//            return;
//        }
//
//        // showProgressDialog();
//
//        // [START sign_in_with_email]
//        mAuth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                          //  Log.d(Tag, "signInWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                        } else {
//                            // If sign in fails, display a message to the user.
//                          //  Log.w(Tag, "signInWithEmail:failure", task.getException());
//                            Toast.makeText(MainActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//
//                        // [START_EXCLUDE]
//                        if (!task.isSuccessful()) {
//                            //mStatusTextView.setText(R.string.auth_failed);
//                        }
//                        //hideProgressDialog();
//                        // [END_EXCLUDE]
//                    }
//                });
//        // [END sign_in_with_email]
//    }
//
//    private void signOut() {
//        mAuth.signOut();
//    }
//
//    private void sendEmailVerification() {
//        // Disable button
//        //findViewById(R.id.verifyEmailButton).setEnabled(false);
//
//        // Send verification email
//        // [START send_email_verification]
//        final FirebaseUser user = mAuth.getCurrentUser();
//        user.sendEmailVerification()
//                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        // [START_EXCLUDE]
//                        // Re-enable button
//                        //findViewById(R.id.verifyEmailButton).setEnabled(true);
//
//                        if (task.isSuccessful()) {
//                            Toast.makeText(MainActivity.this,
//                                    "Verification email sent to " + user.getEmail(),
//                                    Toast.LENGTH_SHORT).show();
//                        } else {
//                         //   Log.e(Tag, "sendEmailVerification", task.getException());
//                            Toast.makeText(MainActivity.this,
//                                    "Failed to send verification email.",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                        // [END_EXCLUDE]
//                    }
//                });
//        // [END send_email_verification]
//    }
//
//    private boolean validateForm() {
//        boolean valid = true;
//
//        String email = txtuName.getText().toString();
//        if (TextUtils.isEmpty(email)) {
//            txtuName.setError("Required.");
//            valid = false;
//        } else {
//            txtuName.setError(null);
//        }
//
//        String password = txtpWord.getText().toString();
//        if (TextUtils.isEmpty(password)) {
//            txtpWord.setError("Required.");
//            valid = false;
//        } else {
//            txtpWord.setError(null);
//        }
//
//        return valid;
//    }
//
//    public void onClick(View v) {
//        int i = v.getId();
//        if (i == R.id.btnRegister) {
//            createAccount(txtuName.getText().toString(), txtpWord.getText().toString());
//        } else if (i == R.id.btnLogin) {
//            signIn(txtuName.getText().toString(), txtpWord.getText().toString());
////        } else if (i == R.id.signOutButton) {
////            signOut();
////        } else if (i == R.id.verifyEmailButton) {
////            sendEmailVerification();
////        }
//        }
//    }
}
