package org.sara.pokedex.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.bumptech.glide.Glide;

import org.sara.pokedex.R;

import org.sara.pokedex.adapters.PokemonAdapter;
import org.sara.pokedex.adapters.PokemonFavAdapter;

import org.sara.pokedex.database.AppDatabase;
import org.sara.pokedex.entities.Pokemon;

import java.util.List;

public class FavoritesActivity extends AppCompatActivity implements PokemonAdapter.ItemClickListener {

    PokemonAdapter adapter;
    RecyclerView recyclerView;
    AppDatabase database;
    List<Pokemon> favoritePokemons;
    int selectedPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_pokemon);

        database = AppDatabase.getDatabase(this);
        favoritePokemons = database.pokemonDao().getAll();

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new PokemonAdapter(this, favoritePokemons, true);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        favoritePokemons.clear();
        favoritePokemons.addAll(database.pokemonDao().getAll());
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onItemClick(View view, int position) {
        if(view.getId() == R.id.iv_pokemon_favorite) {
            selectedPosition = position;
            showAlert(this);
        }
        else {
            Pokemon selectedPokemon = adapter.getPokemon(position);

            Intent intent = new Intent(this, PokemonDetailsActivity.class);
            intent.putExtra("URL", selectedPokemon.getUrl());
            startActivity(intent);
        }
    }

    private void showAlert(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("¿Seguro que quieres eliminar este pokemón de favoritos?");

        // Add the buttons
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Pokemon pokemon = adapter.getPokemon(selectedPosition);
                database.pokemonDao().delete(pokemon);
                favoritePokemons.remove(pokemon);
                adapter.notifyItemRemoved(selectedPosition);

                if(favoritePokemons.isEmpty()) {
                    finish();
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        // Create the AlertDialog
        AlertDialog dialog = builder.create();

        // Show
        dialog.show();
    }
}
