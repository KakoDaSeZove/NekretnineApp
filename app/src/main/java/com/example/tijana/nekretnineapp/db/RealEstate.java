package com.example.tijana.nekretnineapp.db;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by tijana on 18.3.18..
 */
@DatabaseTable(tableName = RealEstate.TABLE_NAME_NEKRETNINA)
public class RealEstate {

    public static final String TABLE_NAME_NEKRETNINA = "nekretnina";

    public static final String FIELD_NAME_ID = "_id";
    public static final String FIELD_NAME_NAZIV = "naziv";
    public static final String FIELD_NAME_OPIS = "opis";
    public static final String FIELD_NAME_ADRESA = "adresa";
    public static final String FIELD_NAME_BROJ_TELEFONA = "broj telefona";
    public static final String FIELD_NAME_KVADRATURA = "kvadratura";
    public static final String FIELD_NAME_BROJ_SOBA = "broj soba";
    public static final String FIELD_NAME_CENA = "cena";
    public static final String FIELD_NAME_SLIKE = "slike";

    @DatabaseField(columnName = FIELD_NAME_ID, generatedId = true)
    private int mId;

    @DatabaseField(columnName = FIELD_NAME_NAZIV)
    private String mNaziv;

    @DatabaseField(columnName = FIELD_NAME_OPIS)
    private String mOpis;

    @DatabaseField(columnName = FIELD_NAME_ADRESA)
    private String mAdresa;

    @DatabaseField(columnName = FIELD_NAME_BROJ_TELEFONA)
    private String mBrojTelefona;

    @DatabaseField(columnName = FIELD_NAME_KVADRATURA)
    private String mKvadratura;

    @DatabaseField(columnName = FIELD_NAME_BROJ_SOBA)
    private String mBrojSoba;

    @DatabaseField(columnName = FIELD_NAME_CENA)
    private String mCena;

    @ForeignCollectionField(columnName = FIELD_NAME_SLIKE, eager = true)
    private ForeignCollection<Slike> mSlike;

    public RealEstate() {

    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmNaziv() {
        return mNaziv;
    }

    public void setmNaziv(String mNaziv) {
        this.mNaziv = mNaziv;
    }

    public String getmOpis() {
        return mOpis;
    }

    public void setmOpis(String mOpis) {
        this.mOpis = mOpis;
    }

    public String getmAdresa() {
        return mAdresa;
    }

    public void setmAdresa(String mAdresa) {
        this.mAdresa = mAdresa;
    }

    public String getmBrojTelefona() {
        return mBrojTelefona;
    }

    public void setmBrojTelefona(String mBrojTelefona) {
        this.mBrojTelefona = mBrojTelefona;
    }

    public String getmKvadratura() {
        return mKvadratura;
    }

    public void setmKvadratura(String mKvadratura) {
        this.mKvadratura = mKvadratura;
    }

    public String getmBrojSoba() {
        return mBrojSoba;
    }

    public void setmBrojSoba(String mBrojSoba) {
        this.mBrojSoba = mBrojSoba;
    }

    public String getmCena() {
        return mCena;
    }

    public void setmCena(String mCena) {
        this.mCena = mCena;
    }

    public ForeignCollection<Slike> getmSlike() {
        return mSlike;
    }

    public void setmSlike(ForeignCollection<Slike> mSlike) {
        this.mSlike = mSlike;
    }

    @Override
    public String toString() {
        return mNaziv;
    }
}
