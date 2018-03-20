package com.example.tijana.nekretnineapp.activities;

import static com.example.tijana.nekretnineapp.activities.DetailActivity.EXTRA_NO;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.tijana.nekretnineapp.R;
import com.example.tijana.nekretnineapp.db.DataBaseHelper;
import com.example.tijana.nekretnineapp.db.RealEstate;
import com.example.tijana.nekretnineapp.db.Slike;
import com.j256.ormlite.dao.Dao;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

public class RealEstateActivity extends AppCompatActivity {

    private static final int SELECT_PICTURE = 1;
    public static final String NEKRETNINE_NAZIV = "nekretnineNaziv";
    public static final String NEKRETNINE_OPIS = "nekretnineOpis";
    public static final String NEKRETNINE_ADRESA = "nekretnineAdresa";
    public static final String NEKRETNINE_BROJ_TELEFONA = "nekretnineBrojTelefona";
    public static final String NEKRETNINE_KVADRATURA = "nekretninekvadratura";
    public static final String NEKRETNINE_BROJ_SOBA = "nekretnineBrojSoba";
    public static final String NEKRETNINE_CENA = "nekretnineCena";


    private RealEstate nekretnina = null;
    private Slike slika = null;

    private ImageView preview;
    private String imagePath = null;

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    private ArrayList<String> allImagesPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_estate);

        allImagesPath = new ArrayList<>();

        SharedPreferences.Editor editor = getPreferences(Context.MODE_PRIVATE).edit();
        editor.remove(NEKRETNINE_NAZIV);
        editor.remove(NEKRETNINE_OPIS);
        editor.remove(NEKRETNINE_ADRESA);
        editor.remove(NEKRETNINE_BROJ_TELEFONA);
        editor.remove(NEKRETNINE_KVADRATURA);
        editor.remove(NEKRETNINE_BROJ_SOBA);
        editor.remove(NEKRETNINE_CENA);
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        }

        preview = (ImageView) findViewById(R.id.real_estate_slika);


        EditText nekretninaNaziv = (EditText) findViewById(R.id.real_estate_naziv);
        EditText nekretninaOpis = (EditText) findViewById(R.id.real_estate_opis);
        EditText nekretninaAdresa = (EditText) findViewById(R.id.real_estate_adresa);
        EditText nekretninaBrojTelefona = (EditText) findViewById(R.id.real_estate_broj_telefona);
        EditText nekretninaKvadratura = (EditText) findViewById(R.id.real_estate_kvadratura);
        EditText nekretninaBrojSoba = (EditText) findViewById(R.id.real_estate_broj_soba);
        EditText nekretninaCena = (EditText) findViewById(R.id.real_estate_cena);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String nazivStr = sharedPref.getString(NEKRETNINE_NAZIV, null);
        if (nazivStr != null) {
            nekretninaNaziv.setText(nazivStr);
        }

        String opisStr = sharedPref.getString(NEKRETNINE_OPIS, null);
        if (opisStr != null) {
            nekretninaOpis.setText(opisStr);
        }

        String adresaStr = sharedPref.getString(NEKRETNINE_ADRESA, null);
        if (adresaStr != null) {
            nekretninaAdresa.setText(adresaStr);
        }

        String brTelefonaStr = sharedPref.getString(NEKRETNINE_BROJ_TELEFONA, null);
        if (brTelefonaStr != null) {
            nekretninaBrojTelefona.setText(brTelefonaStr);
        }

        String kvadraturaStr = sharedPref.getString(NEKRETNINE_KVADRATURA, null);
        if (kvadraturaStr != null) {
            nekretninaKvadratura.setText(kvadraturaStr);
        }

        String brojSobaStr = sharedPref.getString(NEKRETNINE_BROJ_SOBA, null);
        if (brojSobaStr != null) {
            nekretninaBrojSoba.setText(brojSobaStr);
        }

        String cenaStr = sharedPref.getString(NEKRETNINE_CENA, null);
        if (cenaStr != null) {
            nekretninaCena.setText(cenaStr);
        }


        if (getIntent().getExtras() != null) {
            //! We are performing update action
            Integer nekretninaNo = (Integer) getIntent().getExtras().get(EXTRA_NO);

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


            Dao<Slike, Integer> slikeDao = null;
            try {
                slikeDao = helper.getSlikeDao();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                slika = slikeDao.queryForId(nekretninaNo);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            //! Use value from database instead from previous dialog
            if (nazivStr == null) {
                nekretninaNaziv.setText(nekretnina.getmNaziv());
            }

            if (opisStr == null) {
                nekretninaOpis.setText(nekretnina.getmOpis());
            }

            if (adresaStr == null) {
                nekretninaAdresa.setText(nekretnina.getmAdresa());
            }

            if (brTelefonaStr == null) {
                nekretninaBrojTelefona.setText(nekretnina.getmBrojTelefona());
            }

            if (kvadraturaStr == null) {
                nekretninaKvadratura.setText(nekretnina.getmKvadratura());
            }
            if (brojSobaStr == null) {
                nekretninaBrojSoba.setText(nekretnina.getmBrojSoba());
            }
            if (cenaStr == null) {
                nekretninaCena.setText(nekretnina.getmCena());
            }


            //! Must, must, must!!!
            if (imagePath == null) {
                //! Opening update for first time
                imagePath = slika.getmSlika();
            }

            if (imagePath != null) {
                File imgFile = new File(imagePath);
                if (imgFile.exists()) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    preview.setImageBitmap(myBitmap);
                }
            }
        } else {
            //! We are performing add action
            if (imagePath != null) {
                File imgFile = new File(imagePath);
                if (imgFile.exists()) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    preview.setImageBitmap(myBitmap);
                }
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        EditText nekretninaNaziv = (EditText) findViewById(R.id.real_estate_naziv);
        EditText nekretninaOpis = (EditText) findViewById(R.id.real_estate_opis);
        EditText nekretninaAdresa = (EditText) findViewById(R.id.real_estate_adresa);
        EditText nekretninaBrojTelefona = (EditText) findViewById(R.id.real_estate_broj_telefona);
        EditText nekretninaKvadratura = (EditText) findViewById(R.id.real_estate_kvadratura);
        EditText nekretninaBrojSoba = (EditText) findViewById(R.id.real_estate_broj_soba);
        EditText nekretninaCena = (EditText) findViewById(R.id.real_estate_cena);

        SharedPreferences.Editor editor = getPreferences(Context.MODE_PRIVATE).edit();
        editor.putString(NEKRETNINE_NAZIV, nekretninaNaziv.getText().toString());
        editor.putString(NEKRETNINE_OPIS, nekretninaOpis.getText().toString());
        editor.putString(NEKRETNINE_ADRESA, nekretninaAdresa.getText().toString());
        editor.putString(NEKRETNINE_BROJ_TELEFONA, nekretninaBrojTelefona.getText().toString());
        editor.putString(NEKRETNINE_KVADRATURA, nekretninaKvadratura.getText().toString());
        editor.putString(NEKRETNINE_BROJ_SOBA, nekretninaBrojSoba.getText().toString());
        editor.putString(NEKRETNINE_CENA, nekretninaCena.getText().toString());
        editor.commit();
    }

    public void onClickOK(View v) {
        // dohvati sve UI komponente

        EditText nekretninaNaziv = (EditText) findViewById(R.id.real_estate_naziv);
        EditText nekretninaOpis = (EditText) findViewById(R.id.real_estate_opis);
        EditText nekretninaAdresa = (EditText) findViewById(R.id.real_estate_adresa);
        EditText nekretninaBrojTelefona = (EditText) findViewById(R.id.real_estate_broj_telefona);
        EditText nekretninaKvadratura = (EditText) findViewById(R.id.real_estate_kvadratura);
        EditText nekretninaBrojSoba = (EditText) findViewById(R.id.real_estate_broj_soba);
        EditText nekretninaCena = (EditText) findViewById(R.id.real_estate_cena);

        DataBaseHelper helper = new DataBaseHelper(this);

        Dao<RealEstate, Integer> realEstateDao = null;
        try {
            realEstateDao = helper.getRealEstateDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Dao<Slike, Integer> slikeDao = null;
        try {
            slikeDao = helper.getSlikeDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (getIntent().getExtras() == null) {
//Ovo je nova nekretnina
            RealEstate realEstateDB = new RealEstate();
            realEstateDB.setmNaziv(nekretninaNaziv.getText().toString());
            realEstateDB.setmOpis(nekretninaOpis.getText().toString());
            realEstateDB.setmAdresa(nekretninaAdresa.getText().toString());
            realEstateDB.setmBrojTelefona(nekretninaBrojTelefona.getText().toString());
            realEstateDB.setmKvadratura(nekretninaKvadratura.getText().toString());
            realEstateDB.setmBrojSoba(nekretninaBrojSoba.getText().toString());
            realEstateDB.setmCena(nekretninaCena.getText().toString());

            try {
                realEstateDao.create(realEstateDB);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            //! Create N images rows in database
            //! Set path to each image
            //! And set reference to realEstateDB
            for (String image : allImagesPath) {
                Slike slikeDB = new Slike();
                slikeDB.setmSlika(image);
                slikeDB.setmRealEstate(realEstateDB);

                try {
                    slikeDao.create(slikeDB);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
//Ovo je edit nekretnina
            Integer nekretninaNo = (Integer) getIntent().getExtras().get(EXTRA_NO);

            RealEstate realEstateDB = new RealEstate();
            realEstateDB.setmId(nekretninaNo);
            realEstateDB.setmNaziv(nekretninaNaziv.getText().toString());
            realEstateDB.setmOpis(nekretninaOpis.getText().toString());
            realEstateDB.setmAdresa(nekretninaAdresa.getText().toString());
            realEstateDB.setmBrojTelefona(nekretninaBrojTelefona.getText().toString());
            realEstateDB.setmKvadratura(nekretninaKvadratura.getText().toString());
            realEstateDB.setmBrojSoba(nekretninaBrojSoba.getText().toString());
            realEstateDB.setmCena(nekretninaCena.getText().toString());

            try {
                realEstateDao.update(realEstateDB);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            for (String image : allImagesPath) {
                Slike slikeDB = new Slike();
                slikeDB.setmSlika(imagePath);
                slikeDB.setmRealEstate(realEstateDB);

                try {
                    slikeDao.create(slikeDB);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        finish();
    }

    public void onClickCancel(View v) {
        finish();
    }


    public void onSelectImage(View v) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    /**
     * Sismtemska metoda koja se automatksi poziva ako se
     * aktivnost startuje u startActivityForResult rezimu
     * <p>
     * Ako je ti slucaj i ako je sve proslo ok, mozemo da izvucemo
     * sadrzaj i to da prikazemo. Rezultat NIJE sliak nego URI do te slike.
     * Na osnovu toga mozemo dobiti tacnu putnaju do slike ali i samu sliku
     */

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();

                imagePath = getRealPathFromURI(getApplicationContext(), selectedImageUri);

                //! Add image to array list and save it on click OK
                allImagesPath.add(imagePath);


                //! onResume will be called after this function return
            }
        }
    }

    public static String getRealPathFromURI(Context context, Uri uri) {

        String filePath = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && uri.getHost().contains(
                "com.android.providers.media")) {
            // Image pick from recent

            String wholeID = DocumentsContract.getDocumentId(uri);

            // Split at colon, use second item in the array
            String id = wholeID.split(":")[1];

            String[] column = {MediaStore.Images.Media.DATA};

            // where id is equal to
            String sel = MediaStore.Images.Media._ID + "=?";

            Cursor cursor = context.getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    column, sel, new String[]{id}, null);

            int columnIndex = cursor.getColumnIndex(column[0]);

            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex);
            }
            cursor.close();
            return filePath;
        } else {
            // image pick from gallery
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(uri, filePathColumn, null, null,
                    null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    return cursor.getString(columnIndex);
                }
                cursor.close();
            }
            return null;
        }

    }

}
