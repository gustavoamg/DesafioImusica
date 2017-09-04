package com.imusica.desafio.api;

import com.imusica.desafio.domain.Record;
import com.imusica.desafio.service.AgendaService;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.StringReader;

/**
 * Created by gustavoamg on 31/05/17.
 * Provide endpoints for single record manipulation
 */
@Path("/agenda/record/{id}")
@Produces("application/json")
public class SingleRecord {

    @GET
    public Response getRecord(@PathParam("id") String id){
        AgendaService service = AgendaService.getService();

        int _id;

        try{
            _id = Integer.parseInt(id);
        } catch (NumberFormatException nfe) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Record record = service.getRecord(_id);
        JsonObjectBuilder result = Json.createObjectBuilder();

        if(record != null) {
            result.add("id", record.getId())
                    .add("name", record.getName())
                    .add("phone", record.getPhone());
            return Response.ok(result.build().toString(), MediaType.APPLICATION_JSON_TYPE).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();

    }

    @POST
    @Consumes("application/json")
    public Response updateRecord(@PathParam("id") String id, String jsonData){
        AgendaService service = AgendaService.getService();

        int _id;

        try{
            _id = Integer.parseInt(id);
        } catch (NumberFormatException nfe) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }


        JsonObject data = Json.createReader(new StringReader(jsonData)).readObject();

        String name = data.getString("name");
        String phone = data.getString("phone");

        if(name == null || "".equals(name)){
            return Response.status(Response.Status.BAD_REQUEST).entity("Missing required parameter: name").build();
        }

        if(phone == null || "".equals(phone)){
            return Response.status(Response.Status.BAD_REQUEST).entity("Missing required parameter: phone").build();
        }

        if(service.updateRecord(_id, name, phone)){
            return Response.ok("result:OK", MediaType.APPLICATION_JSON_TYPE).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    public Response deleteRecord(@PathParam("id") String id){
        AgendaService service = AgendaService.getService();

        int _id;

        try{
            _id = Integer.parseInt(id);
        } catch (NumberFormatException nfe) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if(service.removeRecord(_id)){
            return Response.ok("result:OK", MediaType.APPLICATION_JSON_TYPE).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();

    }

}
