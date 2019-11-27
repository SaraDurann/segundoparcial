package org.sara.pokedex.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.sara.pokedex.R;
import org.sara.pokedex.adapters.PokemonFavAdapter;

import androidx.recyclerview.widget.RecyclerView;

public class PokemonFavViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView pokemonName;
    public ImageView pokemonImage, fav;
    PokemonFavAdapter.ItemClickListener mClickListener;

    public PokemonFavViewHolder(View itemView, PokemonFavAdapter.ItemClickListener listener) {
        super(itemView);
        mClickListener = listener;
        pokemonName = itemView.findViewById(R.id.tv_pokemon_name);
        pokemonImage = itemView.findViewById(R.id.iv_pokemon_image);
        fav = itemView.findViewById(R.id.details_favorite);
        itemView.setOnClickListener(this);
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) mClickListener.onDeleteItem(getAdapterPosition());
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
    }
}