package com.harshvora.bookkarbooks;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.harshvora.bookkarbooks.Common.Common;
import com.harshvora.bookkarbooks.Interface.ItemClickListener;
import com.harshvora.bookkarbooks.Modal.Category;
import com.harshvora.bookkarbooks.Modal.User;
import com.harshvora.bookkarbooks.ViewHolder.MenuViewHolder;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseDatabase database;
    DatabaseReference category;


    RecyclerView recyclermenu;
    RecyclerView.LayoutManager layoutManager;



     FirebaseRecyclerAdapter<Category,MenuViewHolder> adapter;
    FirebaseRecyclerAdapter<Category,MenuViewHolder> searchadapter;
    List<String> suggestList=new ArrayList<>();
    MaterialSearchBar materialSearchBar;
    Category list=new Category();
    MenuViewHolder view;

    TextView txtFullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);

        database=FirebaseDatabase.getInstance();
        category=database.getReference("Category");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cartIntent=new Intent(Home.this,Cart.class);
                startActivity(cartIntent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView=navigationView.getHeaderView(0);
       txtFullName=(TextView)headerView.findViewById(R.id.txtFullName);
        txtFullName.setText("Hello, "+Common.currentUser.getUsername());

        recyclermenu=(RecyclerView)findViewById(R.id.recyclermenu);
        recyclermenu.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclermenu.setLayoutManager(layoutManager);

        if (Common.isConnectedToInternet(this))
            loadBooks();
        else{

            Toast.makeText(this, "Please Check your Internet Connection!!", Toast.LENGTH_SHORT).show();
            return;}

        materialSearchBar=(MaterialSearchBar) findViewById(R.id.searchBar);
        materialSearchBar.setHint("Enter your Book");

        loadSuggest();
        materialSearchBar.setLastSuggestions(suggestList);
        materialSearchBar.setCardViewElevation(10);
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<String> suggest=new ArrayList<String>();
                for (String search:suggestList)
                {
                    if (search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                        suggest.add(search);
                }
                materialSearchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if(!enabled)
                    recyclermenu.setAdapter(adapter);
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
              if (text.toString().isEmpty())
                  Toast.makeText(Home.this, "Please add Bookname", Toast.LENGTH_SHORT).show();
                else
                    startSearchPublication(text);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });



    }

    private void startSearchPublication(CharSequence text) {

        searchadapter = new FirebaseRecyclerAdapter<Category, MenuViewHolder>(Category.class, R.layout.menuitem, MenuViewHolder.class, category.orderByChild("Publicationname").equalTo(text.toString())) {

            @Override
            protected void populateViewHolder(MenuViewHolder viewHolder, Category model, int position) {


                viewHolder.textBookname.setText(model.getBookname());
                viewHolder.textAuthorname.setText(model.getAuthorname());
                viewHolder.textPublication.setText(model.getPublicationname());
                viewHolder.textRatings.setText(model.getRatings());
                viewHolder.textPrice.setText(model.getPrice());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.imageView);
                final Category clickitem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent bookDetail = new Intent(Home.this, BookDetail.class);
                        bookDetail.putExtra("CategoryId", searchadapter.getRef(position).getKey());
                        startActivity(bookDetail);
                    }
                });
            }

        };
        recyclermenu.setAdapter(searchadapter);
    }

    private void startSearchAuthor(CharSequence text) {

        searchadapter = new FirebaseRecyclerAdapter<Category, MenuViewHolder>(Category.class, R.layout.menuitem, MenuViewHolder.class, category.orderByChild("Authorname").equalTo(text.toString())) {

            @Override
            protected void populateViewHolder(MenuViewHolder viewHolder, Category model, int position) {


                viewHolder.textBookname.setText(model.getBookname());
                viewHolder.textAuthorname.setText(model.getAuthorname());
                viewHolder.textPublication.setText(model.getPublicationname());
                viewHolder.textRatings.setText(model.getRatings());
                viewHolder.textPrice.setText(model.getPrice());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.imageView);
                final Category clickitem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent bookDetail = new Intent(Home.this, BookDetail.class);
                        bookDetail.putExtra("CategoryId", searchadapter.getRef(position).getKey());
                        startActivity(bookDetail);
                    }
                });
            }

        };
        recyclermenu.setAdapter(searchadapter);
    }

    private void startSearch (final CharSequence text) {



                searchadapter = new FirebaseRecyclerAdapter<Category, MenuViewHolder>(Category.class, R.layout.menuitem, MenuViewHolder.class, category.orderByChild("Bookname").equalTo(text.toString())) {

                    @Override
                    protected void populateViewHolder(MenuViewHolder viewHolder, Category model, int position) {


                        viewHolder.textBookname.setText(model.getBookname());
                        viewHolder.textAuthorname.setText(model.getAuthorname());
                        viewHolder.textPublication.setText(model.getPublicationname());
                        viewHolder.textRatings.setText(model.getRatings());
                        viewHolder.textPrice.setText(model.getPrice());
                        Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.imageView);
                        final Category clickitem = model;
                        viewHolder.setItemClickListener(new ItemClickListener() {
                            @Override
                            public void onClick(View view, int position, boolean isLongClick) {
                                Intent bookDetail = new Intent(Home.this, BookDetail.class);
                                bookDetail.putExtra("CategoryId", searchadapter.getRef(position).getKey());
                                startActivity(bookDetail);
                            }
                        });
                    }

                };
                recyclermenu.setAdapter(searchadapter);






    }

    private void loadSuggest() {
        category.orderByChild("CategoryId").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    Category item=postSnapshot.getValue(Category.class);

                   // Map<String,String> map=postSnapshot.getValue(Map.class);

                  //  String Bookname=map.get("Bookname");
                    //String Authorname=map.get("Authorname");
                    //String Publicationname=map.get("Publicationname");


                        int counter=0;



                  //  suggestList.add(item.getBookname());
                    //suggestList.add(item.getAuthorname());
                    suggestList.add(item.getPublicationname());

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void loadBooks(){


      adapter =  new FirebaseRecyclerAdapter<Category, MenuViewHolder>(Category.class,R.layout.menuitem,MenuViewHolder.class,category) {
            @Override
            protected void populateViewHolder(MenuViewHolder viewHolder, Category model, int position) {

                viewHolder.textBookname.setText(model.getBookname());
                viewHolder.textAuthorname.setText(model.getAuthorname());
                viewHolder.textPublication.setText(model.getPublicationname());
                viewHolder.textRatings.setText(model.getRatings());
                viewHolder.textPrice.setText("₹ " +model.getPrice());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.imageView);
                final Category clickitem=model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent bookDetail=new Intent(Home.this,BookDetail.class);
                        bookDetail.putExtra("CategoryId",adapter.getRef(position).getKey());
                        startActivity(bookDetail);
                    }
                });

            }
        };
        recyclermenu.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        //noinspection SimplifiableIfStatement
        if (item.getItemId() == R.id.refresh) {
            loadBooks();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_profile) {
            Intent profileIntent=new Intent(Home.this,Profile.class);
            startActivity(profileIntent);
        }

        else if (id == R.id.nav_home) {
                Intent homeIntent=new Intent(Home.this,Home.class);
                startActivity(homeIntent);

        } else if (id == R.id.nav_cart) {
            Intent cartIntent=new Intent(Home.this,Cart.class);
            startActivity(cartIntent);

        } else if (id == R.id.nav_orders) {
            Intent orderIntent=new Intent(Home.this,OrderStatus.class);
            startActivity(orderIntent);

        } else if (id == R.id.nav_feedback) {
            Intent feedbackIntent=new Intent(Home.this,Feedback.class);
            startActivity(feedbackIntent);

        }
        else if (id == R.id.nav_signout) {
            Intent main=new Intent(Home.this,MainActivity.class);
            main.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(main);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
