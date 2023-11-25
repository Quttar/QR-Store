package com.example.appqrstore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CartElemAdapter  extends RecyclerView.Adapter<CartElemAdapter.ViewHolder> {


    private final LayoutInflater inflater;
    private final List<CartElem> elems;

    CartElemAdapter(Context context, List<CartElem> elems) {
        this.elems = elems;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public CartElemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartElemAdapter.ViewHolder holder, int position) {
        CartElem cartElem = elems.get(position);

        Picasso.get()
                .load(cartElem.imageUrl)
                .placeholder(R.drawable.ic_image)
                .into(holder.cartElemImage);

        holder.buttonMinus.setOnClickListener((v) -> {
            CartElem item = AppData.Cart.get(position);
            item.count--;
            if (item.count == 0){
                AppData.Cart.remove(position);
                AppData.Adapter.notifyDataSetChanged();
            }
            else{
                AppData.Adapter.notifyItemChanged(position);
            }

        });

        holder.buttonPlus.setOnClickListener((v) -> {
            CartElem item = AppData.Cart.get(position);
            item.count++;
            AppData.Adapter.notifyItemChanged(position);
        });


        holder.cartElemName.setText(cartElem.name);
        holder.cartElemDescription.setText(cartElem.description);
        holder.cartElemProductCount.setText(Integer.toString(cartElem.count));
        holder.cartElemProductPrice.setText(Integer.toString(cartElem.count * cartElem.price) + " руб");
    }

    @Override
    public int getItemCount() {
        return elems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
            final ImageView cartElemImage;
            final TextView cartElemName;
//
            final TextView cartElemDescription;
//
            final TextView cartElemProductPrice;
            final TextView cartElemProductCount;
            final Button buttonPlus;
            final Button buttonMinus;
        ViewHolder(View view){
            super(view);
            cartElemImage = view.findViewById(R.id.imageView_cartElemImage);
            buttonMinus = view.findViewById(R.id.button_productCountMinus);
            buttonPlus = view.findViewById(R.id.button_productCountPlus);

            cartElemName = view.findViewById(R.id.textView_cartElemName);
            cartElemDescription = view.findViewById(R.id.textView_cartElemDescription);
            cartElemProductPrice = view.findViewById(R.id.textView_productPrice);
            cartElemProductCount = view.findViewById(R.id.textView_productCount);
        }
    }
}
