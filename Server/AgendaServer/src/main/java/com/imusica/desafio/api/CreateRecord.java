package com.imusica.desafio.api;

import com.imusica.desafio.domain.Record;
import com.imusica.desafio.service.AgendaService;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.StringReader;

/**
 * Created by gustavoamg on 31/05/17.
 * Endpoints for creating a new record
 */

@Path("/agenda/createrecord")
@Produces("application/json")
public class CreateRecord {

    @POST
    public Response createRecord(String jsonData) {
        AgendaService service = AgendaService.getService();

        JsonObject data = Json.createReader(new StringReader(jsonData)).readObject();

        String name = data.getString("name");
        String phone = data.getString("phone");

        if(name == null || "".equals(name)){
            return Response.status(Response.Status.BAD_REQUEST).entity("Missing required parameter: name").build();
        }

        if(phone == null || "".equals(phone)){
            return Response.status(Response.Status.BAD_REQUEST).entity("Missing required parameter: phone").build();
        }

        Record record = service.addRecod(name, phone);

        JsonObjectBuilder result = Json.createObjectBuilder();

        if(record != null) {
            result.add("id", record.getId())
                    .add("name", record.getName())
                    .add("phone", record.getPhone());
        }

        return Response.ok(result.build().toString(), MediaType.APPLICATION_JSON_TYPE).build();
    }
}
