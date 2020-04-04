package com.harshvora.bookkarbooks;

        import android.content.DialogInterface;
        import android.provider.ContactsContract;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.LinearLayout;
        import android.widget.RadioButton;
        import android.widget.RadioGroup;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.craftman.cardform.Card;
        import com.craftman.cardform.CardForm;
        import com.craftman.cardform.OnPayBtnClickListner;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.harshvora.bookkarbooks.Common.Common;
        import com.harshvora.bookkarbooks.Database.Database;
        import com.harshvora.bookkarbooks.Modal.Category;
        import com.harshvora.bookkarbooks.Modal.Order;
        import com.harshvora.bookkarbooks.Modal.Request;
        import com.harshvora.bookkarbooks.ViewHolder.CartAdapter;
        import com.rengwuxian.materialedittext.MaterialEditText;

        import java.text.NumberFormat;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.Locale;

        import info.hoang8f.widget.FButton;

public class Cart extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;

    TextView txtTotal;
    FButton btnplace;

    List<Order> cart=new ArrayList<>();

    CartAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        database=FirebaseDatabase.getInstance();
        requests=database.getReference("Requests");

        recyclerView=(RecyclerView)findViewById(R.id.listcart);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotal=(TextView)findViewById(R.id.total);
        btnplace=(FButton)findViewById(R.id.btnPlaceOrder);

        btnplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cart.size()>0)
                {
                    shoAlertDialog();

                }
                else {
                    Toast.makeText(Cart.this, "Please add something to cart", Toast.LENGTH_SHORT).show();

                }
            }
        });

        loadlistBook();

    }

    private void shoAlertDialog() {

        View mView = getLayoutInflater().from(Cart.this).inflate(R.layout.dialogbox, null);
        final AlertDialog.Builder alertDialog=new AlertDialog.Builder(Cart.this);
        alertDialog.setIcon(R.drawable.ic_local_grocery_store_black_24dp);
        alertDialog.setTitle("One More Step");
        //  alertDialog.setMessage("Enter your Address:");
        alertDialog.setView(mView);
        alertDialog.setCancelable(false);
        final MaterialEditText edtAddress  = (MaterialEditText) mView.findViewById(R.id.edtaddress);
        final RadioGroup rdg=(RadioGroup)mView.findViewById(R.id.rdg);
        final RadioButton cod=(RadioButton)mView.findViewById(R.id.cod);
        final RadioButton creditcard=(RadioButton)mView.findViewById(R.id.credit);
        final RadioButton debitcard=(RadioButton)mView.findViewById(R.id.debit);

     /*   final EditText edtAddress=new EditText(Cart.this);
        final RadioButton cod=new RadioButton(Cart.this);
        final LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        edtAddress.setLayoutParams(lp);
        alertDialog.setView(cod);
        alertDialog.setView(edtAddress);
        alertDialog.setIcon(R.drawable.ic_local_grocery_store_black_24dp);*/

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (edtAddress.getText().toString().isEmpty()){
                 //   || !(cod.isChecked()|creditcard.isChecked()|debitcard.isChecked())
                    //  edtAddress.setLayoutParams(lp);
                    //alertDialog.setView(edtAddress);
                    //alertDialog.setIcon(R.drawable.ic_local_grocery_store_black_24dp);
                    Toast.makeText(Cart.this, "Invalid Address", Toast.LENGTH_SHORT).show();}
                //or Payment not chosen

                else if (cod.isChecked()){
                    Request request = new Request(
                            Common.currentUser.getMobileNo(),
                            Common.currentUser.getUsername(),

                            edtAddress.getText().toString(),
                            txtTotal.getText().toString(),
                            cart

                    );

                    requests.child(String.valueOf(System.currentTimeMillis()))
                            .setValue(request);

                    new Database(getBaseContext()).cleanCart();
                    Toast.makeText(Cart.this, "Thank You, Order Place", Toast.LENGTH_SHORT).show();
                    finish();


                }
                else if (debitcard.isChecked()){

                    setContentView(R.layout.dccard);

                    CardForm cardForm=(CardForm)findViewById(R.id.cardform);
                    TextView textView=(TextView)findViewById(R.id.payment_amount);
                    Button btnPay=(Button)findViewById(R.id.btn_pay);
                    textView.setText(txtTotal.getText().toString());
                    btnPay.setText(String.format("Pay %s",textView.getText().toString()));

                    cardForm.setPayBtnClickListner(new OnPayBtnClickListner() {
                        @Override
                        public void onClick(Card card) {
                            Request request = new Request(
                                    Common.currentUser.getMobileNo(),
                                    Common.currentUser.getUsername(),
                                    edtAddress.getText().toString(),
                                    txtTotal.getText().toString(),
                                    cart

                            );

                            requests.child(String.valueOf(System.currentTimeMillis()))
                                    .setValue(request);

                            new Database(getBaseContext()).cleanCart();
                            Toast.makeText(Cart.this, "Thank You, Order Place", Toast.LENGTH_SHORT).show();
                            finish();

                        }
                    });



                }
                else if (creditcard.isChecked()){

                    setContentView(R.layout.dccard);

                    CardForm cardForm=(CardForm)findViewById(R.id.cardform);
                    TextView textView=(TextView)findViewById(R.id.payment_amount);
                    Button btnPay=(Button)findViewById(R.id.btn_pay);
                    textView.setText(txtTotal.getText().toString());
                    btnPay.setText(String.format("Pay %s",textView.getText().toString()));

                    cardForm.setPayBtnClickListner(new OnPayBtnClickListner() {
                        @Override
                        public void onClick(Card card) {
                            Request request = new Request(
                                    Common.currentUser.getMobileNo(),
                                    Common.currentUser.getUsername(),
                                    edtAddress.getText().toString(),
                                    txtTotal.getText().toString(),
                                    cart

                            );

                            requests.child(String.valueOf(System.currentTimeMillis()))
                                    .setValue(request);

                            new Database(getBaseContext()).cleanCart();
                            Toast.makeText(Cart.this, "Thank You, Order Place", Toast.LENGTH_SHORT).show();
                            finish();

                        }
                    });



                }



                else {
                    Request request = new Request(
                            Common.currentUser.getMobileNo(),
                            Common.currentUser.getUsername(),

                            edtAddress.getText().toString(),
                            txtTotal.getText().toString(),
                            cart

                    );

                    requests.child(String.valueOf(System.currentTimeMillis()))
                            .setValue(request);

                    new Database(getBaseContext()).cleanCart();
                    Toast.makeText(Cart.this, "Thank You, Order Place", Toast.LENGTH_SHORT).show();
                    finish();

                }
            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();

    }

    private void loadlistBook() {

        cart=new Database(this).getCarts();
        adapter=new CartAdapter(cart,this);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        int total=0;
        for(Order order:cart)

            total += (Integer.parseInt(order.getPrice())) * (Integer.parseInt(order.getQuantity()));


        Locale locale = new Locale("hi","IN");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        txtTotal.setText(fmt.format(total));


    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().equals(Common.DELETE))
            deleteCarts(item.getOrder());
        return true;
    }

    private void deleteCarts(int position) {
        cart.remove(position);
        new Database(this).cleanCart();
        for (Order item:cart)
            new Database(this).addToCart(item);
        loadlistBook();
    }
}
