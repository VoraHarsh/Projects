package com.harshvora.bookkarbooks;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.harshvora.bookkarbooks.Common.Common;

import com.harshvora.bookkarbooks.Modal.Order;
import com.harshvora.bookkarbooks.Modal.Request;
import com.harshvora.bookkarbooks.ViewHolder.OrderViewHolder;


import java.util.ArrayList;
import java.util.List;

public class OrderStatus extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Request,OrderViewHolder> adapter;

    FirebaseDatabase database;
    DatabaseReference requests;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        database=FirebaseDatabase.getInstance();
        requests=database.getReference("Requests");

        recyclerView=(RecyclerView)findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        
        loadOrders(Common.currentUser.getMobileNo());



    }

    private void loadOrders(String mobileNo) {

        adapter=new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class,R.layout.orderlayout,OrderViewHolder.class,requests.orderByChild("phone").equalTo(mobileNo)
        ) {
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, Request model, int position) {
                viewHolder.txtOrderId.setText(adapter.getRef(position).getKey());
                viewHolder.txtOrderStatus.setText("Status: "+convertCodeToStatus(model.getStatus()));
                viewHolder.txtOrdername.setText("Customer Name: "+model.getName());
                viewHolder.txtOrderPhone.setText("Mobile No.:"+(model.getPhone()));
                viewHolder.txtOrderAddress.setText("Address: "+(model.getAddress()));
                viewHolder.txtOrderAmount.setText("Amount: "+(model.getTotal()));


            }
        };

        recyclerView.setAdapter(adapter);

    }
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().equals(Common.DELETE))
            deleteOrder(adapter.getRef(item.getOrder()).getKey());
        return true;
    }

    private void deleteOrder(final String key) {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(OrderStatus.this);
        alertDialog.setMessage("Are you sure you want to cancel the Order?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requests.child(key).removeValue();
                        Toast.makeText(OrderStatus.this, "Order Cancelled", Toast.LENGTH_SHORT).show();

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
    private String convertCodeToStatus(String status) {
        if (status.equals("0"))
            return "Placed";
        else if (status.equals("1"))
            return "On the Way";
        else
            return "Shipped";
    }

}
