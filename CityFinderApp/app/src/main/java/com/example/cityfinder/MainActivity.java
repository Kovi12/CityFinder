package com.example.cityfinder;
import androidx.appcompat.widget.SearchView;

import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends ListActivity {
    public static final String TEXT_INPUT = "CITY_NAME";
    private TextView title;
    private SearchView search_bar;
    CityItemAdapter item_adapter;
    private JSONArray cities;
    private final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            ICityFinder service = ICityFinder.Stub.asInterface(iBinder);
            try {
                service.RetrieveInfo(new ICallback.Stub() {
                    @Override
                    public void OnSuccess(String responseString){
                        try{
                            cities = new JSONArray(responseString);
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                        runOnUiThread(() -> {
                            for (int i = 0; i < cities.length(); i++) {
                                try {
                                    int resID = getResources().getIdentifier(cities.getJSONObject(i).getString("name").toLowerCase(),"drawable", getPackageName());
                                    item_adapter.add(cities.getJSONObject(i).getString("name"),resID);
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                                setListAdapter(item_adapter);

                        });
                    }
                });
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title = (TextView) findViewById(R.id.title);
        search_bar = (SearchView) findViewById(R.id.search_bar);
        search_bar.clearFocus();
        item_adapter = new CityItemAdapter(this);

        search_bar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
    }

    private void filterList(String text) {
        ArrayList<CityItem> filteredList = new ArrayList<>();
        for (CityItem item : item_adapter.getList()){
            if (item.getName().toLowerCase().contains(text.toLowerCase()))
                filteredList.add(item);
        }

        if (filteredList.isEmpty())
            Toast.makeText(this,"No result found",Toast.LENGTH_SHORT).show();
        else
            item_adapter.setFilteredList(filteredList);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(this,CityPage.class);
        intent.putExtra(TEXT_INPUT,item_adapter.getItem(position).getName());
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, CityFinderService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(connection);
    }
}