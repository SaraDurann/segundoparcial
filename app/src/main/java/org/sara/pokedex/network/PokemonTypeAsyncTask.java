package org.sara.pokedex.network;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.sara.pokedex.entities.Pokemon;
import org.sara.pokedex.entities.PokemonDetails;
import org.sara.pokedex.entities.PokemonType;
import org.sara.pokedex.interfaces.AsyncTaskHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.sara.pokedex.network.NetworkUtils.createUrl;
import static org.sara.pokedex.network.NetworkUtils.makeHttpRequest;


public class PokemonTypeAsyncTask extends AsyncTask<String, Void, PokemonType> {

    public AsyncTaskHandler handler;

    @Override
    protected PokemonType doInBackground(String... urls) {
        URL url = createUrl(urls[0]);
        // Hacemos la petición. Ésta puede tirar una exepción.
        String jsonResponse = "";
        try {
            jsonResponse = makeHttpRequest(url);
            return parsePokemonType(jsonResponse);
        } catch (IOException e) {
            Log.e("Download error", "Problem making the HTTP request.", e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(PokemonType pokemonType) {
        super.onPostExecute(pokemonType);
        if (handler != null) {
            handler.onTaskEnd(pokemonType);
        }
    }

    private PokemonType parsePokemonType(String jsonStr) {
        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
            String name = jsonObj.getString("name");
            JSONObject damageRelationsJson = jsonObj.getJSONObject("damage_relations");
            Map<String, List<String>> damageRelations = new HashMap<>();

            for (int i = 0; i < PokemonType.relationNames.length; i++) {
                List<String> typeNameList = new ArrayList<>();
                JSONArray jsonArray = damageRelationsJson.getJSONArray(PokemonType.relationNames[i]);

                for (int j = 0; j < jsonArray.length(); j++) {
                    String typeName = jsonArray.getJSONObject(j).getString("name");
                    typeNameList.add(typeName);
                }

                damageRelations.put(PokemonType.relationNames[i], typeNameList);
            }

            List<Pokemon> pokemons = new ArrayList<>();
            JSONArray pokemonsArray = jsonObj.getJSONArray("pokemon");

            for (int i = 0; i < pokemonsArray.length(); i++) {
                JSONObject pokemonJson = pokemonsArray.getJSONObject(i).getJSONObject("pokemon");
                String url = pokemonJson.getString("url");
                if (isFromGeneration(url)) {
                    String pokemonName = pokemonJson.getString("name");
                    pokemons.add(new Pokemon(pokemonName, url));

                    if (pokemons.size() == 6) {
                        break;
                    }
                }
            }

            PokemonType pokemonType = new PokemonType(name, damageRelations, pokemons);
            return pokemonType;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean isFromGeneration(String pokemonUrl) {
        String txtId = pokemonUrl.replace("https://pokeapi.co/api/v2/pokemon/", "")
                .replace("/", "");
        int id = Integer.valueOf(txtId);

        return id >= 0;
        //regresa pokemones de la misma generacion
        /*return id >= 386 && id <= 493;*/
    }
}