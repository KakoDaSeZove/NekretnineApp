package com.example.tijana.nekretnineapp.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by tijana on 18.3.18..
 */
@DatabaseTable(tableName = Slike.TABLE_NAME_SLIKE)
public class Slike {

        public static final String TABLE_NAME_SLIKE = "slike";

        public static final String FIELD_NAME_ID = "_id";
        public static final String FIELD_NAME_SLIKA = "slika";
        public static final String FIELD_NAME_REAL_ESTATE = "nekretnina";

        @DatabaseField(columnName = FIELD_NAME_ID, generatedId = true)
        private int mId;

        @DatabaseField(columnName = FIELD_NAME_SLIKA)
        private String mSlika;

        @DatabaseField(columnName = FIELD_NAME_REAL_ESTATE, foreign = true, foreignAutoRefresh =
                true)
        private RealEstate mRealEstate;

    public Slike() {
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmSlika() {
        return mSlika;
    }

    public void setmSlika(String mSlika) {
        this.mSlika = mSlika;
    }

    public RealEstate getmRealEstate() {
        return mRealEstate;
    }

    public void setmRealEstate(RealEstate mRealEstate) {
        this.mRealEstate = mRealEstate;
    }


    @Override
    public String toString() {
        return mSlika;
    }
}
