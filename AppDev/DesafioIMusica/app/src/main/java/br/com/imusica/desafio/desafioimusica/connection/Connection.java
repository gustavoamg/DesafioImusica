package br.com.imusica.desafio.desafioimusica.connection;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.AbstractMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import br.com.imusica.desafio.desafioimusica.DesafioApplication;
import br.com.imusica.desafio.desafioimusica.R;
import br.com.imusica.desafio.desafioimusica.exception.ConnectionException;
import br.com.imusica.desafio.desafioimusica.util.IOUtils;

public abstract class Connection extends AsyncTask<Void, Void, Integer> {

    private static final String TAG= Connection.class.getName();

    private ConnectionListener listener;
    private int index;
    private Object result;
    protected Exception error;
    private boolean running;

    public Connection(ConnectionListener listener) {
        this.listener = listener;
    }

    public void start() {
        running = true;
        this.execute();
    }

    public void cancel() {
        this.cancel(true);
        running = false;
    }

    protected abstract String getUrl();

    protected abstract String getMethod();

    protected abstract String getParameters() throws JSONException, IOException;

    protected abstract Object processResponse(String json) throws JSONException,
             ParseException;

    protected String getFullUrl() {
        return getBaseUrl() + getUrl();
    }

    protected String getBaseUrl() {
        return DesafioApplication.getBaseUrl();
    }

    protected String getEncoding() {
        return "UTF-8";
    }

    //Authed headers
//    protected Map.Entry<String, String> getHeadersAuth() {
//        Map.Entry<String, String> header = new AbstractMap.SimpleEntry("Authorization", "basic " + getToken());
//        return header;
//    }

    protected Map.Entry<String, String>[] addHeaders() {
        Map.Entry<String, String>[] headers = new Map.Entry[2];

        Map.Entry<String, String> header1 = new AbstractMap.SimpleEntry("Accept", "application/json");
        headers[0] = header1;

        Map.Entry<String, String> header2 = new AbstractMap.SimpleEntry("Content-type", "application/json");
        headers[1] = header2;

        return headers;
    }

    @Override
    protected Integer doInBackground(Void... params) {
        if (DesafioApplication.connected()) {
            try {
                result = sendHttp();

                return 0;
            } catch (ConnectionException e) {
                e.printStackTrace();
                error = e;
                return -1;
            }  catch (JSONException e) {
                e.printStackTrace();
                error = new Exception(DesafioApplication.getAppContext()
                        .getString(R.string.error_parse_exception), e);
                return -1;
            } catch (IOException e) {
                e.printStackTrace();
                error = new Exception(DesafioApplication.getAppContext()
                        .getString(R.string.error_unexpected_connection_fault), e);
                return -1;
            } catch (Exception e) {
                e.printStackTrace();
                if (e.getMessage() != null)
                    error = e;
                else {
                    error = new Exception(DesafioApplication.getAppContext()
                            .getString(R.string.error_unexpected_connection_fault), e);
                }
                return -1;
            }
        } else {
            error = new Exception(DesafioApplication.getAppContext().getString(
                    R.string.error_no_internet_connection));
            return -1;
        }
    }

    @Override
    protected void onPostExecute(Integer result) {
        if (listener != null) {
            if (result == 0) {
                Connection.this.listener
                        .connectionReturnedWithSuccess(Connection.this);
            } else {
                Connection.this.listener
                        .connectionReturnedWithFailure(Connection.this);
            }
        }
        running = false;
    }

    @Override
    protected void onCancelled() {
    }

    private Object sendHttp() throws Exception {
        HttpURLConnection urlConnection = null;
        Integer statusCode;

        String response;

        try {
            URL url = new URL(getFullUrl());

            Log.d(TAG, "URL -> [" + url + "]");


            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDefaultUseCaches(false);
            urlConnection.setRequestMethod(getMethod());

            //if (isAuthenticated()) {
            //    urlConnection.setRequestProperty(getHeadersAuth().getKey(),
            //            getHeadersAuth().getValue());
            //}

            Map.Entry[] headers = addHeaders();
            for (int i = 0; i < headers.length; i++) {
                Map.Entry<String, String> header = headers[i];
                if (header.getKey() != null) {
                    urlConnection.setRequestProperty(header.getKey(),
                            header.getValue());
                }
            }

            urlConnection.setDoInput(true);
            urlConnection.setConnectTimeout(120000);
            urlConnection.setReadTimeout(60000);

            String parameters = getParameters();
            if (parameters != null) {
                urlConnection.setDoOutput(true);
            } else {
                urlConnection.setDoOutput(false);
            }

            if (parameters != null) {
                OutputStream os = urlConnection.getOutputStream();
                os.write(parameters.getBytes());
                os.flush();
                os.close();
            }

            try {
                statusCode = urlConnection.getResponseCode();

            } catch (IOException e) {
                //retry
                statusCode = urlConnection.getResponseCode();
                if (statusCode != 401) {
                    throw e;
                }
            }

            InputStream instream = null;
            if (statusCode < HttpsURLConnection.HTTP_BAD_REQUEST) {
                try {
                    instream = urlConnection.getInputStream();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    instream = urlConnection.getErrorStream();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            response = null;

            if (instream != null) {
                response = IOUtils.convertStreamToString(instream, getEncoding());
                instream.close();
            }
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }

        if (statusCode == HttpURLConnection.HTTP_OK || statusCode == HttpURLConnection.HTTP_CREATED) {
            try {
                return processResponse(response);
            } catch (Exception e) {
                logResponse(statusCode, response);
                throw e;
            }
        }  else if (statusCode == HttpURLConnection.HTTP_BAD_REQUEST) {
            String erro = trataMensagemErro(response);
            logResponse(statusCode, response);
            throw new Exception(erro);
        } else {
            logResponse(statusCode, response);
            throw new Exception();
        }
    }


    private void logResponse(Integer status, String responseBody) {
        String body = "NO-BODY";

        if (responseBody != null) {
            body = responseBody;
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void setListener(ConnectionListener listener) {
        this.listener = listener;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Object getResult() {
        return result;
    }

    public Exception getError() {
        return error;
    }


    private String trataMensagemErro(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        String erro = jsonObject.getString("errors");

        if (erro != null) {
            return erro;
        }
        return DesafioApplication.getAppContext().getString(R.string.error_unexpected_connection_fault);
    }
}
