package com.harshvora.bookkarbooks;

import android.media.Image;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.harshvora.bookkarbooks.Common.Common;
import com.harshvora.bookkarbooks.Database.Database;
import com.harshvora.bookkarbooks.Modal.Category;
import com.harshvora.bookkarbooks.Modal.Order;
import com.harshvora.bookkarbooks.ViewHolder.MenuViewHolder;
import com.squareup.picasso.Picasso;

public class BookDetail extends AppCompatActivity {

    TextView BookName,BookPrice,BookDescription;
    ImageView Bookimage;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btn_cart;
    ElegantNumberButton numberButton;
    CoordinatorLayout coordinatorlayout;

    String bookid="";

    FirebaseDatabase database;
    DatabaseReference books;

    FirebaseRecyclerAdapter<Category,MenuViewHolder> adapter;

    Category currentbook;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        database=FirebaseDatabase.getInstance();
        books=database.getReference("Category");

        numberButton=(ElegantNumberButton)findViewById(R.id.numberbutton);
        btn_cart=(FloatingActionButton)findViewById(R.id.btncart);

        btn_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Database(getBaseContext()).addToCart(new Order(
                        bookid,

                        currentbook.getBookname(),
                        numberButton.getNumber(),
                        currentbook.getPrice(),
                        currentbook.getAuthorname()




                ));

                Toast.makeText(BookDetail.this, "Added to Cart", Toast.LENGTH_SHORT).show();
            }
        });

        BookName=(TextView)findViewById(R.id.book_name);
        BookPrice=(TextView)findViewById(R.id.book_price);
        BookDescription=(TextView)findViewById(R.id.book_description);
        Bookimage=(ImageView)findViewById(R.id.img_view);

        collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);


        if(getIntent()!=null){
            bookid=getIntent().getStringExtra("CategoryId");
        }
        if(!bookid.isEmpty() && bookid !=null){
            if (Common.isConnectedToInternet(this))
                getDetailBook(bookid);
            else
            {
                Toast.makeText(BookDetail.this, "Please Check your Internet Connection!!", Toast.LENGTH_SHORT).show();
                return;
            }
        }

    }

    private void getDetailBook(String bookid) {

       /* adapter=new FirebaseRecyclerAdapter<Category, MenuViewHolder>(Category.class,R.layout.activity_book_detail,MenuViewHolder.class,books) {
            @Override
            protected void populateViewHolder(MenuViewHolder viewHolder, Category model, int position) {
                viewHolder.BookName.setText(model.getBookname());
                viewHolder.BookPrice.setText(model.getPrice());
               // viewHolder.BookDescription.setText(model.getDescription());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.Bookimage);

            }
        };*/



        books.child(bookid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 currentbook=dataSnapshot.getValue(Category.class);


                Picasso.with(getBaseContext()).load(currentbook.getImage()).into(Bookimage);

                collapsingToolbarLayout.setTitle(currentbook.getBookname());
                BookPrice.setText("₹ "+currentbook.getPrice());
                BookDescription.setText(currentbook.getAuthorname());
                BookName.setText(currentbook.getBookname());



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
