package com.imusica.desafio;
import com.imusica.desafio.service.AgendaService;
import com.sun.net.httpserver.HttpServer;
import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;

/**
 * Created by gustavoamg on 30/05/17.
 *
 * Levanta um server para responder as requisições
 */
@Path("/agenda")
public class AgendaServer {

    private static AgendaService service;

    @GET
    @Produces("text/plain")
    public String getClichedMessage() {

        StringBuilder documentation = new StringBuilder();

        documentation.append(getGreetingMessage());

        documentation.append("\n\n");

        documentation.append(" . [ LIST RECORDS ]\n");
        documentation.append("<server ip>:9998/agenda/records\n");
        documentation.append("Method:  GET\n");
        documentation.append("Returns:  A list of records\n");

        documentation.append("\n\n");

        documentation.append(" . [ LOAD SINGLE RECORD ]\n");
        documentation.append("<server ip>:9998/agenda/record/<record id>\n");
        documentation.append("Method:  GET\n");
        documentation.append("Returns:  A single record\n");

        documentation.append("\n\n");

        documentation.append(" . [ EDIT SINGLE RECORD ]\n");
        documentation.append("<server ip>:9998/agenda/record/<record id>\n");
        documentation.append("Method:  POST\n");
        documentation.append("Params:  name : String\n");
        documentation.append("         phone  String\n");
        documentation.append("Returns:  OK\n");

        documentation.append("\n\n");

        documentation.append(" . [ REMOVE SINGLE RECORD ]\n");
        documentation.append("<server ip>:9998/agenda/record/<record id>\n");
        documentation.append("Method:  DELETE\n");
        documentation.append("Returns:  OK\n");

        documentation.append("\n\n");

        documentation.append(" . [ CREATE NEW RECORD ]\n");
        documentation.append("<server ip>:9998/agenda/createrecord\n");
        documentation.append("Method:  POST\n");
        documentation.append("Params:  name : String\n");
        documentation.append("         phone : String\n");
        documentation.append("Returns:  The new record created\n");

        return documentation.toString();
    }

    public static void main(String[] args) throws IOException {

        service = AgendaService.getService();

        HttpServer server = HttpServerFactory.create("http://localhost:9998/");
        server.start();

        System.out.println(getGreetingMessage());
        System.out.println("Server running");
        System.out.println("Visit: http://localhost:9998/agenda");
        System.out.println("Hit return to stop...");
        System.in.read();
        System.out.println("Stopping server");
        server.stop(0);
        System.out.println("Server stopped");
    }

    private static String getGreetingMessage() {
        StringBuffer greeting = new StringBuffer();

        greeting.append("*************************************************\n");
        greeting.append("*****        Agenda v 1.0-SNAPSHOT          *****\n");
        greeting.append("*****                                       *****\n");
        greeting.append("***** Autor:  Gustavo A. Guimarães          *****\n");
        greeting.append("***** Data: 30/05/2017                      *****\n");
        greeting.append("*************************************************\n");

        return greeting.toString();
    }
}
