package br.com.imusica.desafio.desafioimusica.connection;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;

import br.com.imusica.desafio.desafioimusica.domain.AgendaRecord;

/**
 * Created by gustavoamg on 01/06/17.
 */

public class ConnectionEditRecord extends Connection {

    private AgendaRecord recod;

    public ConnectionEditRecord(ConnectionListener listener, AgendaRecord record) {
        super(listener);
        this.recod = record;
    }

    @Override
    protected String getUrl() {
        return "record/" + recod.getId();
    }

    @Override
    protected String getMethod() {
        return HttpMethod.POST.getMethod();
    }

    @Override
    protected String getParameters() throws JSONException, IOException {
        JSONObject params = new JSONObject();
        params.put("id", recod.getId());
        params.put("name", recod.getName());
        params.put("phone", recod.getPhone());

        return params.toString();
    }

    @Override
    protected Object processResponse(String json) throws JSONException, ParseException {
        return null;
    }
}
