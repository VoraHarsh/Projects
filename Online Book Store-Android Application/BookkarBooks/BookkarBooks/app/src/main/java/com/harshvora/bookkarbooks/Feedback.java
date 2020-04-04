package com.harshvora.bookkarbooks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.harshvora.bookkarbooks.Common.Common;
import com.harshvora.bookkarbooks.Modal.Feedbacks;
import com.harshvora.bookkarbooks.Modal.User;

import info.hoang8f.widget.FButton;

public class Feedback extends AppCompatActivity {
    EditText txtFeedback;
    FButton  btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // toolbar.setTitle("Feedback");
        //setSupportActionBar(toolbar);

        txtFeedback=(EditText)findViewById(R.id.feedback);
        btnSubmit=(FButton)findViewById(R.id.btnSubmit);

        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference table_feedback=database.getReference("Feedback");

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtFeedback.getText().toString().isEmpty()){
                    Toast.makeText(Feedback.this, "Please Write Something", Toast.LENGTH_SHORT).show();

                }
                else {
                    table_feedback.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            Feedbacks feedback = new Feedbacks(txtFeedback.getText().toString());
                            table_feedback.child(Common.currentUser.getUsername().toString()).setValue(feedback);
                            Toast.makeText(Feedback.this, "FeedBack Submitted!", Toast.LENGTH_SHORT).show();
                            finish();


                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

    }
}
