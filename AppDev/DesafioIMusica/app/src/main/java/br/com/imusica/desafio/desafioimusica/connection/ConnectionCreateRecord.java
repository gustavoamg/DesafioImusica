package br.com.imusica.desafio.desafioimusica.connection;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;

import br.com.imusica.desafio.desafioimusica.domain.AgendaRecord;


/**
 * Created by gustavoamg on 01/06/17.
 */

public class ConnectionCreateRecord extends Connection {

    private String name;
    private String phone;

    public ConnectionCreateRecord(ConnectionListener listener, String name, String phone) {
        super(listener);

        this.name  = name;
        this.phone = phone;
    }

    @Override
    protected String getUrl() {
        return "createrecord/";
    }

    @Override
    protected String getMethod() {
        return HttpMethod.POST.getMethod();
    }

    @Override
    protected String getParameters() throws JSONException, IOException {
        JSONObject params = new JSONObject();
        params.put("name", this.name);
        params.put("phone", this.phone);

        return params.toString();
    }

    @Override
    protected Object processResponse(String json) throws JSONException, ParseException {

        JSONObject result = new JSONObject(json);

        AgendaRecord agendaRecord = new AgendaRecord();
        agendaRecord.setId(result.getInt("id"));
        agendaRecord.setName(result.getString("name"));
        agendaRecord.setPhone(result.getString("phone"));

        return agendaRecord;
    }
}
