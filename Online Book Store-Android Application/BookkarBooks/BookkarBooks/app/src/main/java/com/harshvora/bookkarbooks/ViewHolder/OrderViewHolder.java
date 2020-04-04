package com.harshvora.bookkarbooks.ViewHolder;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;

import com.harshvora.bookkarbooks.Common.Common;
import com.harshvora.bookkarbooks.Interface.ItemClickListener;

import com.harshvora.bookkarbooks.R;



/**
 * Created by Harsh on 2/2/2018.
 */

 public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener ,View.OnCreateContextMenuListener {

    public TextView txtOrderId, txtOrderStatus, txtOrderPhone, txtOrderAddress, txtOrderAmount,txtOrdername;
    private ItemClickListener itemClickListener;

    public OrderViewHolder(View itemView) {
        super(itemView);

        txtOrderAddress = (TextView) itemView.findViewById(R.id.orderaddress);
        txtOrdername=(TextView)itemView.findViewById(R.id.ordername);
        txtOrderId = (TextView) itemView.findViewById(R.id.orderid);
        txtOrderPhone = (TextView) itemView.findViewById(R.id.orderphone);
        txtOrderStatus = (TextView) itemView.findViewById(R.id.orderstatus);
        txtOrderAmount = (TextView) itemView.findViewById(R.id.orderamount);

        itemView.setOnCreateContextMenuListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Select Action");
        menu.add(0, 0, getAdapterPosition(), Common.DELETE);

    }
}




