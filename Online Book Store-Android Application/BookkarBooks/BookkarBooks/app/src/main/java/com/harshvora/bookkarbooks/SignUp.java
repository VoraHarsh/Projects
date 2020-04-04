package com.harshvora.bookkarbooks;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
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

import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    EditText edtUsername,edtPassword,edtRetypepassword,edtEmail,edtPhone;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtUsername=(MaterialEditText)findViewById(R.id.edtusername);
        edtPassword=(MaterialEditText)findViewById(R.id.edtpassword);
        edtRetypepassword=(MaterialEditText)findViewById(R.id.edtrepassword);
        edtEmail=(MaterialEditText)findViewById(R.id.edtemail);
        edtPhone=(MaterialEditText)findViewById(R.id.edtphone);

        btnSignUp=(Button)findViewById(R.id.btnSignUp);


        //Init Firebase

        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference table_user=database.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Common.isConnectedToInternet(getBaseContext())) {

                    final ProgressDialog mdialog = new ProgressDialog(SignUp.this);
                    mdialog.setMessage("Please wait....");
                    mdialog.show();

                    table_user.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (TextUtils.isEmpty(edtUsername.getText().toString().trim()) && TextUtils.isEmpty(edtPassword.getText().toString().trim()) && TextUtils.isEmpty(edtRetypepassword.getText().toString().trim()) && TextUtils.isEmpty(edtPhone.getText().toString().trim()) && TextUtils.isEmpty(edtEmail.getText().toString().trim())) {
                                mdialog.dismiss();
                                edtUsername.setError("Fields can't be Empty");
                                edtPassword.setError("Fields can't be Empty");
                                edtRetypepassword.setError("Fields can't be Empty");
                                edtPhone.setError("Fields can't be Empty");
                                edtEmail.setError("Fields can't be Empty");

                            } else if (TextUtils.isEmpty(edtUsername.getText().toString().trim())) {
                                mdialog.dismiss();
                                edtUsername.setError("Fields can't be Empty");
                            } else if (TextUtils.isEmpty(edtPassword.getText().toString().trim())) {
                                mdialog.dismiss();
                                edtPassword.setError("Fields can't be Empty");

                            } else if (TextUtils.isEmpty(edtRetypepassword.getText().toString().trim())) {
                                mdialog.dismiss();
                                edtRetypepassword.setError("Fields can't be Empty");

                            } else if (!edtRetypepassword.getText().toString().equals(edtPassword.getText().toString())) {
                                mdialog.dismiss();
                                edtRetypepassword.setError("Password Doesn't match");
                            } else if (TextUtils.isEmpty(edtPhone.getText().toString().trim())) {
                                mdialog.dismiss();
                                edtPhone.setError("Fields can't be Empty");

                            } else if (!isValidPhone(edtPhone.getText().toString())) {
                                mdialog.dismiss();
                                edtPhone.setError("Invalid PhoneNumber");

                            } else if (TextUtils.isEmpty(edtEmail.getText().toString().trim())) {
                                mdialog.dismiss();
                                edtEmail.setError("Fields can't be Empty");

                            } else if (!isValidEmaillId(edtEmail.getText().toString().trim())) {
                                mdialog.dismiss();
                                edtEmail.setError("Invalid Email Address");


                            } else if (dataSnapshot.child(edtUsername.getText().toString()).exists()) {
                                mdialog.dismiss();
                                Toast.makeText(SignUp.this, "Username already Registered !", Toast.LENGTH_SHORT).show();

                            } else {
                                mdialog.dismiss();
                                User user = new User(edtEmail.getText().toString(), edtPassword.getText().toString(), edtPhone.getText().toString());
                                table_user.child(edtUsername.getText().toString()).setValue(user);
                                Toast.makeText(SignUp.this, "SignUp Successfully !", Toast.LENGTH_SHORT).show();
                                finish();

                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else

                {
                    Toast.makeText(SignUp.this, "Please Check your Internet Connection!!", Toast.LENGTH_SHORT).show();
                    return;
                }
        }



        });

    }
    public boolean isValidEmaillId(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }
    private boolean isValidPhone(String phone)
    {
        boolean check=false;
        if(!Pattern.matches("[a-zA-Z]+", phone))
        {
            if(phone.length() < 10 || phone.length()>10)
            {
                check = false;

            }
            else
            {
                check = true;

            }
        }
        else
        {
            check=false;
        }
        return check;
    }

}
