package br.com.imusica.desafio.desafioimusica.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gustavoamg on 31/05/17.
 */

public class AgendaRecord implements Parcelable {

    private int id;
    private String name;
    private String phone;

    public AgendaRecord() {
    }

    protected AgendaRecord(Parcel in) {
        id = in.readInt();
        name = in.readString();
        phone = in.readString();
    }

    public static final Creator<AgendaRecord> CREATOR = new Creator<AgendaRecord>() {
        @Override
        public AgendaRecord createFromParcel(Parcel in) {
            return new AgendaRecord(in);
        }

        @Override
        public AgendaRecord[] newArray(int size) {
            return new AgendaRecord[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.name);
        parcel.writeString(this.phone);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
