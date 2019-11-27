package org.sara.pokedex.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.sara.pokedex.R;
import org.sara.pokedex.adapters.DamageRelationAdapter;
import org.sara.pokedex.adapters.PokemonAdapter;
import org.sara.pokedex.adapters.PokemonTypeAdapter;
import org.sara.pokedex.entities.Pokemon;
import org.sara.pokedex.entities.PokemonType;
import org.sara.pokedex.interfaces.AsyncTaskHandler;
import org.sara.pokedex.network.PokemonTypeAsyncTask;
import org.sara.pokedex.utils.Utils;

public class PokemonTypeActivity extends AppCompatActivity implements AsyncTaskHandler, PokemonTypeAdapter.ItemClickListener {

    ImageView cover;
    TextView name;
    RecyclerView damageRelations;
    RecyclerView pokemons;
    PokemonTypeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_type);

        cover = findViewById(R.id.type_cover);
        name = findViewById(R.id.type_name);
        damageRelations = findViewById(R.id.type_damage_relations);
        pokemons = findViewById(R.id.type_pokemons);

        String type = getIntent().getStringExtra("TYPE");
        String url = "https://pokeapi.co/api/v2/type/" + type;

        PokemonTypeAsyncTask pokemonTypeAsyncTask = new PokemonTypeAsyncTask();
        pokemonTypeAsyncTask.handler = this;
        pokemonTypeAsyncTask.execute(url);
    }

    @Override
    public void onTaskEnd(Object result) {
        PokemonType pokemonType = (PokemonType) result;

        name.setText(pokemonType.getName());

        damageRelations.setLayoutManager(new LinearLayoutManager(this));
        damageRelations.setAdapter(new DamageRelationAdapter(this, pokemonType));

        pokemons.setLayoutManager(new GridLayoutManager(this, 1));
        adapter = new PokemonTypeAdapter(this, pokemonType.getPokemons());
        adapter.setClickListener(this);
        pokemons.setAdapter(adapter);

        String coverName = "type_" + pokemonType.getName();
        Glide.with(this).load(Utils.getDrawable(this, coverName)).into(cover);
    }

    @Override
    public void onItemClick(View view, int position) {
        Pokemon pokemon = adapter.getPokemon(position);

        Intent intent = new Intent(this, PokemonDetailsActivity.class);
        intent.putExtra("URL", pokemon.getUrl());
        startActivity(intent);
    }
}
