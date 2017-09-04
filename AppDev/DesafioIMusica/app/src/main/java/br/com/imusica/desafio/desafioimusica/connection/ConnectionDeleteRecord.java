package br.com.imusica.desafio.desafioimusica.connection;

import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;

/**
 * Created by gustavoamg on 01/06/17.
 */

public class ConnectionDeleteRecord extends Connection {

    private int id;

    public ConnectionDeleteRecord(ConnectionListener listener, int id) {
        super(listener);
        this.id = id;
    }

    @Override
    protected String getUrl() {
        return "record/" + id;
    }

    @Override
    protected String getMethod() {
        return HttpMethod.DELETE.getMethod();
    }

    @Override
    protected String getParameters() throws JSONException, IOException {
        return null;
    }

    @Override
    protected Object processResponse(String json) throws JSONException, ParseException {
        return null;
    }
}
