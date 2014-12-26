package com.trasselbackstudios.dexter;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.trasselbackstudios.dexter.data.PokemonContract;
import com.trasselbackstudios.dexter.data.PokemonDatabase;

import java.util.ArrayList;
import java.util.Locale;

public class PokemonFragment extends Fragment {
    private ListView listView;

    public PokemonFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        setHasOptionsMenu(true);

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(rootView.getContext(), R.layout.list_item_pokemon, R.id.list_item_pokemon_textview, new ArrayList<String>());
        adapter.addAll(getListItems());
        listView = (ListView) rootView.findViewById(R.id.listview_pokemon);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = adapter.getItem(position);
                String pkID  = item.substring(0, item.indexOf(" ")).replaceAll("^0+", "");
                Intent intent = new Intent(getActivity(), DetailsActivity.class).putExtra(Intent.EXTRA_TEXT, pkID);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        return rootView;
    }

    private ArrayList<String> getListItems(){
        PokemonDatabase db = new PokemonDatabase(getActivity());
        Cursor c = db.getPokemonItems();
        ArrayList<String> names = new ArrayList<>();
        String id = c.getString(c.getColumnIndex(PokemonContract.PokemonEntry.COLUMN_ID));
        names.add(String.format("%03d", Integer.parseInt(id)) + " " + c.getString(c.getColumnIndex(PokemonContract.PokemonEntry.COLUMN_NAME)));
        while (c.moveToNext()) {
            String name = c.getString(c.getColumnIndex(PokemonContract.PokemonEntry.COLUMN_NAME));
            id = c.getString(c.getColumnIndex(PokemonContract.PokemonEntry.COLUMN_ID));
            names.add(String.format("%03d", Integer.parseInt(id)) + " " + name);
        }
        c.close();
        db.close();
        return names;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_pokemon, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                for (int i = 0; i < listView.getCount(); i++) {
                    String listItem = listView.getItemAtPosition(i).toString()
                            .toUpperCase(Locale.getDefault());
                    if (listItem.contains(s.toUpperCase(Locale.getDefault())))
                        listView.setSelection(i);
                }
                // Reset to the top of the list if search bar is empty
                if (s.equals(""))
                    listView.setSelection(0);
                return false;
            }
        });
    }
}
