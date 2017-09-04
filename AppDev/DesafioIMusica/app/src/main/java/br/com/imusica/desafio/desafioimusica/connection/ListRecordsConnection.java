package br.com.imusica.desafio.desafioimusica.connection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import br.com.imusica.desafio.desafioimusica.domain.AgendaRecord;

/**
 * Created by gustavoamg on 01/06/17.
 */

public class ListRecordsConnection extends Connection {

    public ListRecordsConnection(ConnectionListener listener) {
        super(listener);
    }

    @Override
    protected String getUrl() {
        return "records/";
    }

    @Override
    protected String getMethod() {
        return "GET";
    }

    @Override
    protected String getParameters() throws JSONException, IOException {
        return null;
    }

    @Override
    protected Object processResponse(String json) throws JSONException, ParseException {

        JSONArray recordsJsonArray = new JSONArray(json);

        List<AgendaRecord> recordsList = new ArrayList<>();

        for(int i = 0 ; i < recordsJsonArray.length(); i++){
            JSONObject recordJson = recordsJsonArray.getJSONObject(i);

            AgendaRecord record = new AgendaRecord();
            record.setId(recordJson.getInt("id"));
            record.setName(recordJson.getString("name"));
            record.setPhone(recordJson.getString("phone"));

            recordsList.add(record);
        }

        return recordsList;
    }
}
