package br.com.imusica.desafio.desafioimusica.connection;

/**
 * Created by gustavoamg on 01/06/17.
 */

public enum HttpMethod {
    GET ("GET"),
    POST ("POST"),
    DELETE("DELETE");

    HttpMethod(String method) {
        this.method = method;
    }

    private String method;

    public String getMethod() {
        return method;
    }
}
