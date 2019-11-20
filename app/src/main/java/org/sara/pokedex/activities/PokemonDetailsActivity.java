package org.sara.pokedex.activities;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.sara.pokedex.R;
import org.sara.pokedex.adapters.RowTypesAdapter;
import org.sara.pokedex.database.AppDatabase;
import org.sara.pokedex.entities.PokemonDetails;
import org.sara.pokedex.interfaces.AsyncTaskHandler;
import org.sara.pokedex.network.PokemonDetailsAsyncTask;

import java.util.Arrays;

public class PokemonDetailsActivity extends AppCompatActivity implements AsyncTaskHandler {

    ImageView image;
    TextView name, types, weight, experience, id;
    RecyclerView rvDetailsTypes;

    AppDatabase database;


    // Informacion de los Pokemon
    String url;
    String pokemonName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_details);

        image = findViewById(R.id.details_image);
        name = findViewById(R.id.detatils_name);
        types = findViewById(R.id.detatils_type);
        weight = findViewById(R.id.detatils_weight);
        experience = findViewById(R.id.detatils_experience);
        id = findViewById(R.id.detatils_id);
        rvDetailsTypes = findViewById(R.id.rv_details_types);

        url = getIntent().getStringExtra("URL");

        PokemonDetailsAsyncTask pokemonDetailsAsyncTask = new PokemonDetailsAsyncTask();
        pokemonDetailsAsyncTask.handler = this;
        pokemonDetailsAsyncTask.execute(url);

        database = AppDatabase.getDatabase(this);
    }

    @Override
    public void onTaskEnd(Object result) {
        PokemonDetails details = (PokemonDetails) result;
        pokemonName = details.getName();
        Glide.with(this).load(details.getImage()).into(image);
        name.setText(details.getName());
        weight.setText("Peso: " + details.getWeight());
        experience.setText("Experiencia: " + details.getBaseExperience());
        id.setText("ID: " + details.getId());
        types.setText("Tipo: ");
        rvDetailsTypes.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvDetailsTypes.setAdapter(new RowTypesAdapter(this, Arrays.asList(details.getTypes())));


    }
}
