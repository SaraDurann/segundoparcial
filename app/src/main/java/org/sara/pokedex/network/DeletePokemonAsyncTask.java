package org.sara.pokedex.network;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.sara.pokedex.database.AppDataBaseSingleton;
import org.sara.pokedex.database.AppDatabase;
import org.sara.pokedex.entities.Pokemon;
import org.sara.pokedex.interfaces.AsyncTaskHandler;

public class DeletePokemonAsyncTask extends AsyncTask<Pokemon, Void, Integer> {

    private Context context;
    AsyncTaskHandler handler;

    public DeletePokemonAsyncTask(Context context, AsyncTaskHandler handler) {
        this.context = context;
        this.handler = handler;
    }

    @Override
    protected Integer doInBackground(Pokemon... pokemonShorts) {
        if (pokemonShorts.length == 0) return 0;
        AppDatabase db = AppDataBaseSingleton.getInstance(context).appDatabase;
        db.pokemonDao().delete(pokemonShorts[0]);
        return 1;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        String message = (integer > 0) ? "Pokemon was deleted" : "Something went wrong";
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        if (handler != null) handler.onTaskEnd(null);
    }
}