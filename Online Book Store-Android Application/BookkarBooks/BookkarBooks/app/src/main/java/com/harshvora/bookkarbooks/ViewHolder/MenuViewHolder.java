package com.harshvora.bookkarbooks.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.harshvora.bookkarbooks.Interface.ItemClickListener;
import com.harshvora.bookkarbooks.R;


/**
 * Created by Harsh on 1/28/2018.
 */

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView textBookname,textAuthorname,textPublication,textRatings,textPrice,BookName,BookPrice,BookDescription;
    public ImageView imageView,Bookimage;

    private ItemClickListener itemClickListener;
    public MenuViewHolder(View itemView) {
        super(itemView);

        textBookname=(TextView)itemView.findViewById(R.id.textBookName);
        textAuthorname=(TextView)itemView.findViewById(R.id.textAuthor);
        textPublication=(TextView)itemView.findViewById(R.id.textPublication);
        textRatings=(TextView)itemView.findViewById(R.id.textViewRating);
        textPrice=(TextView)itemView.findViewById(R.id.textViewPrice);
        imageView=(ImageView) itemView.findViewById(R.id.imageView);

        //BookName=(TextView)itemView.findViewById(R.id.book_name);
        //BookPrice=(TextView)itemView.findViewById(R.id.book_price);
       // BookDescription=(TextView)itemView.findViewById(R.id.book_description);
       // Bookimage=(ImageView)itemView.findViewById(R.id.img_view);


        itemView.setOnClickListener(this);


    }
  public void setItemClickListener(ItemClickListener itemClickListener){
      this.itemClickListener=itemClickListener;
  }


    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);

    }
}
