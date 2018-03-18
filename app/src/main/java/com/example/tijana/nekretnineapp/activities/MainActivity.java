package com.example.tijana.nekretnineapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.tijana.nekretnineapp.R;
import com.example.tijana.nekretnineapp.db.DataBaseHelper;
import com.example.tijana.nekretnineapp.db.RealEstate;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static android.R.layout.simple_list_item_1;

public class MainActivity extends AppCompatActivity {

    private ListView listView = null;
    private List<RealEstate> nekretnina = null;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int SELECT_PICTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);


        AdapterView.OnItemClickListener itemClickListener =
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> listView,
                                            View v,
                                            int position,
                                            long id)

                    {
                        Intent intent = new Intent(MainActivity.this,
                                DetailActivity.class);
                        intent.putExtra(DetailActivity.EXTRA_NO, nekretnina.get(position).getmId());
                        startActivity(intent);
                    }


                };
        listView = (ListView) findViewById(R.id.lista_nekretnina);
        listView.setOnItemClickListener(itemClickListener);

    }

    @Override
    protected void onResume() {
        super.onResume();

        DataBaseHelper helper = new DataBaseHelper(this);
        Dao<RealEstate, Integer> realEstateDao = null;
        try {
            realEstateDao = helper.getRealEstateDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try {
            nekretnina = realEstateDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ArrayList<String> realEstateList = new ArrayList<>();
        for (RealEstate re : nekretnina) {
            realEstateList.add(re.getmId() + " " + re.getmNaziv());
        }

        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(
                this,
                simple_list_item_1,
                realEstateList) {
        };
        listView.setAdapter(listAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //! Add
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.action_add:
//
                Intent intent = new Intent(MainActivity.this,
                        RealEstateActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
