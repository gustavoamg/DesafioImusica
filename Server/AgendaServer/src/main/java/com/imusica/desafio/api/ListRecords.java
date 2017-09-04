package com.imusica.desafio.api;

import com.imusica.desafio.domain.Record;
import com.imusica.desafio.service.AgendaService;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * Created by gustavoamg on 30/05/17.
 *
 * Returns a list of records in the agenda
 */

@Path("/agenda/records")
@Produces("application/json")
public class ListRecords {

    @GET
    public String listRecords() {

        AgendaService service = AgendaService.getService();
        List<Record> records = service.getRecords();

        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

        for (Record r : records){
            JsonObject record = Json.createObjectBuilder()
                    .add("id", r.getId())
                    .add("name", r.getName())
                    .add("phone", r.getPhone())
                    .build();

            jsonArrayBuilder.add(record);
        }

        return jsonArrayBuilder.build().toString();
    }
}
