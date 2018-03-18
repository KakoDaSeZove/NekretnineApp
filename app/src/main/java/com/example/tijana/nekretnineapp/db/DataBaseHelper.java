package com.example.tijana.nekretnineapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;


/**
 * Created by tijana on 18.3.18..
 */

public class DataBaseHelper extends OrmLiteSqliteOpenHelper {

    public static String DATABASE_NAME = "mojeNekretnine.db";
    public static int DATABASE_VERSION = 1;

    private Dao<RealEstate, Integer> mRealEstateDao = null;
    private Dao<Slike, Integer> mSlikeDao = null;

    //Potrebno je dodati konstruktor zbog pravilne inicijalizacije biblioteke
    public DataBaseHelper(Context context) {
        super(context,
                DATABASE_NAME,
                null,
                DATABASE_VERSION);
    }

    //Prilikom kreiranja baze potrebno je da pozovemo odgovarajuce metode biblioteke
    //prilikom kreiranja moramo pozvati TableUtils.createTable za svaku tabelu koju imamo
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, RealEstate.class);
            TableUtils.createTable(connectionSource, Slike.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //kada zelimo da izmenomo tabele, moramo pozvati TableUtils.dropTable za sve tabele koje imamo
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, RealEstate.class, true);
            TableUtils.dropTable(connectionSource, Slike.class, true);
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //jedan Dao objekat sa kojim komuniciramo. Ukoliko imamo vise tabela
    //potrebno je napraviti Dao objekat za svaku tabelu
    public Dao<RealEstate, Integer> getRealEstateDao() throws SQLException {
        if (mRealEstateDao == null) {
            mRealEstateDao = getDao(RealEstate.class);
        }

        return mRealEstateDao;
    }

    public Dao<Slike, Integer> getSlikeDao() throws SQLException {
        if (mSlikeDao == null) {
            mSlikeDao = getDao(Slike.class);
        }

        return mSlikeDao;
    }

    //obavezno prilikom zatvarnaj rada sa bazom osloboditi resurse
    @Override
    public void close() {
        mRealEstateDao = null;
        mSlikeDao = null;

        super.close();
    }
}
