package com.harshvora.bookkarbooks.ViewHolder;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;
import com.harshvora.bookkarbooks.Common.Common;
import com.harshvora.bookkarbooks.Interface.ItemClickListener;
import com.harshvora.bookkarbooks.Modal.Order;
import com.harshvora.bookkarbooks.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.zip.Inflater;

/**
 * Created by Harsh on 2/1/2018.
 */


class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    ,View.OnCreateContextMenuListener{

    public TextView textcartname,textprice;
    public ImageView imgcartcount;

    private ItemClickListener itemClickListener;

    public void setTextcartname(TextView textcartname) {
        this.textcartname = textcartname;
    }

    public CartViewHolder(View itemView) {
        super(itemView);

        textcartname=(TextView)itemView.findViewById(R.id.cartitemname);
        textprice=(TextView)itemView.findViewById(R.id.cartitemprice);
        imgcartcount=(ImageView)itemView.findViewById(R.id.cartitemcount);

        itemView.setOnCreateContextMenuListener(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        menu.setHeaderTitle("Select Action");
        menu.add(0,0,getAdapterPosition(), Common.DELETE);


    }
}


public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    private List<Order> listData=new ArrayList<>();
    private Context context;

    public CartAdapter(List<Order> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.cart,parent,false);
        return new CartViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        TextDrawable drawable=TextDrawable.builder().buildRound(""+listData.get(position).getQuantity(), Color.RED);
        holder.imgcartcount.setImageDrawable(drawable);

        Locale locale=new Locale("hi","IN");
        NumberFormat fmt=NumberFormat.getCurrencyInstance(locale);
        int price=(Integer.parseInt(listData.get(position).getPrice()))*(Integer.parseInt(listData.get(position).getQuantity()));
        holder.textprice.setText(fmt.format(price));
        holder.textcartname.setText(listData.get(position).getBookName());



    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
