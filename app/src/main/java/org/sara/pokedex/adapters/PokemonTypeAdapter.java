package org.sara.pokedex.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import org.sara.pokedex.R;
import org.sara.pokedex.entities.Pokemon;

import org.sara.pokedex.ViewHolder.PokemonTypeViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PokemonTypeAdapter extends RecyclerView.Adapter<PokemonTypeViewHolder> {

    private List<Pokemon> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;

    // la informacion pasa dentro del constructor
    public PokemonTypeAdapter(Context context, List<Pokemon> data) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // infla el diseño de la celda desde xml cuando es necesario
    @Override
    @NonNull
    public PokemonTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.pokemon_item_list, parent, false);
        return new PokemonTypeViewHolder(view, mClickListener);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull PokemonTypeViewHolder holder, int position) {
        Pokemon pokemon = mData.get(position);

        Glide.with(mContext).load(pokemon.getImage()).into(holder.iv_pokemon_image);
        holder.tv_pokemon_name.setText(pokemon.getName());
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // convenience method for getting data at click position
    public Pokemon getPokemon(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}