package br.com.imusica.desafio.desafioimusica.connection;

public interface ConnectionListener {
	public void connectionReturnedWithSuccess(Connection connection);
	public void connectionReturnedWithFailure(Connection connection);
}
