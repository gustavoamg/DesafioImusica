package br.com.imusica.desafio.desafioimusica.exception;

/**
 * Created by gustavoamg on 31/05/17.
 */

public class ConnectionException extends Exception {

    private static final long serialVersionUID = -125225607291734339L;

    public ConnectionException(String mensagem) {
        super(mensagem);
    }
}
