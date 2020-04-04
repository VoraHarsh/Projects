package com.harshvora.bookkarbooks;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.harshvora.bookkarbooks.Common.Common;
import com.harshvora.bookkarbooks.Modal.User;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignIn extends AppCompatActivity {

    EditText edtUsername,edtPassword;
    Button btnSignIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtUsername=(MaterialEditText)findViewById(R.id.edtusername);
        edtPassword=(MaterialEditText)findViewById(R.id.edtpassword);
        btnSignIn=(Button)findViewById(R.id.btnSignIn);

        //Init Firebase

        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference table_user=database.getReference("User");


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Common.isConnectedToInternet(getBaseContext())){



                    final ProgressDialog mdialog = new ProgressDialog(SignIn.this);
                mdialog.setMessage("Please wait....");
                mdialog.show();


                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (TextUtils.isEmpty(edtUsername.getText().toString().trim()) && (TextUtils.isEmpty(edtPassword.getText().toString().trim()))) {
                            mdialog.dismiss();

                            edtUsername.setError("Fields can't be Empty");
                            edtPassword.setError("Fields can't be Empty");
                        } else if (TextUtils.isEmpty(edtUsername.getText().toString().trim())) {
                            mdialog.dismiss();
                            edtUsername.setError("Fields can't be Empty");

                        } else if (TextUtils.isEmpty(edtPassword.getText().toString().trim())) {
                            mdialog.dismiss();

                            edtPassword.setError("Fields can't be Empty");

                        } else if (dataSnapshot.child(edtUsername.getText().toString()).exists()) {
                            mdialog.dismiss();

                            User user = dataSnapshot.child(edtUsername.getText().toString()).getValue(User.class);
                            user.setUsername(edtUsername.getText().toString());
                            if (user.getPassword().equals(edtPassword.getText().toString())) {
                                Intent homeIntent = new Intent(SignIn.this, Home.class);
                                Common.currentUser = user;
                                startActivity(homeIntent);
                                finish();

                            } else {
                                Toast.makeText(SignIn.this, "Wrong Password!", Toast.LENGTH_SHORT).show();
                            }
                        } else {

                            mdialog.dismiss();
                            Toast.makeText(SignIn.this, "User not exist !", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
                else
                    Toast.makeText(SignIn.this, "Please Check your Internet Connection!!", Toast.LENGTH_SHORT).show();
                    return;
        }



        });


    }
}
