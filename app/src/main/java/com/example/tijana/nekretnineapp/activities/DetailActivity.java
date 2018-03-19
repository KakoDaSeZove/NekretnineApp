package com.example.tijana.nekretnineapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tijana.nekretnineapp.R;
import com.example.tijana.nekretnineapp.adapters.ImageAdapter;
import com.example.tijana.nekretnineapp.db.DataBaseHelper;
import com.example.tijana.nekretnineapp.db.RealEstate;
import com.example.tijana.nekretnineapp.db.Slike;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_NO = "nekretninaNo";

    private RealEstate nekretnina = null;
    public ImageView nekretnineSlika = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }

    @Override
    protected void onResume() {
        super.onResume();

        int nekretninaNo = (Integer) getIntent().getExtras().get(EXTRA_NO);

        DataBaseHelper helper = new DataBaseHelper(this);
        Dao<RealEstate, Integer> realEstateDao = null;
        try {
            realEstateDao = helper.getRealEstateDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            nekretnina = realEstateDao.queryForId(nekretninaNo);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        TextView nekretninaIndetifikator = (TextView) findViewById(
                R.id.nekretnine_indentifikator_unos);
        nekretninaIndetifikator.setText((nekretnina.getmId() + ""));

        TextView nekretninaNaziv = (TextView) findViewById(R.id.nekretnine_naziv_unos);
        nekretninaNaziv.setText(nekretnina.getmNaziv());

        TextView nekretninaOpis = (TextView) findViewById(R.id.nekretnine_opis_unos);
        nekretninaOpis.setText(nekretnina.getmOpis());

        TextView nekretninaAdresa = (TextView) findViewById(R.id.nekretnine_adresa_unos);
        nekretninaAdresa.setText(nekretnina.getmAdresa());

        TextView nekretninaBrojTelefona = (TextView) findViewById(
                R.id.nekretnine_broj_telefona_unos);
        nekretninaBrojTelefona.setText(nekretnina.getmBrojTelefona());

        TextView nekretninaKvadratura = (TextView) findViewById(R.id.nekretnine_kvadratura_unos);
        nekretninaKvadratura.setText(nekretnina.getmKvadratura());

        TextView nekretninaBrojSoba = (TextView) findViewById(R.id.nekretnine_broj_soba_unos);
        nekretninaBrojSoba.setText(nekretnina.getmBrojSoba());

        TextView nekretninaCena = (TextView) findViewById(R.id.nekretnine_cena_unos);
        nekretninaCena.setText(nekretnina.getmCena());

        ArrayList<String> images = new ArrayList();
        for (Slike image : nekretnina.getmSlike()) {
            images.add(image.getmSlika());
        }

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this,images));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                    int position, long id) {
                Toast.makeText(getApplicationContext(), "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });


    }

}
