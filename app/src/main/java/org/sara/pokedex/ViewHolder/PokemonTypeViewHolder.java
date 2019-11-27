package org.sara.pokedex.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.sara.pokedex.R;
import org.sara.pokedex.adapters.PokemonAdapter;
import org.sara.pokedex.adapters.PokemonTypeAdapter;

import androidx.recyclerview.widget.RecyclerView;

public class PokemonTypeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tv_pokemon_name;
    public ImageView iv_pokemon_image;
    private PokemonTypeAdapter.ItemClickListener mClickListener;

    public PokemonTypeViewHolder(View itemView, PokemonTypeAdapter.ItemClickListener listener) {
        super(itemView);
        mClickListener = listener;
        tv_pokemon_name = itemView.findViewById(R.id.tv_pokemon_name);
        iv_pokemon_image = itemView.findViewById(R.id.iv_pokemon_image);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
    }
}

