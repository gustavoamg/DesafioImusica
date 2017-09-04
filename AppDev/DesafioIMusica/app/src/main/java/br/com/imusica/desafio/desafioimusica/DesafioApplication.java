package br.com.imusica.desafio.desafioimusica;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;

import java.util.Locale;

/**
 * Created by gustavoamg on 31/05/17.
 */

public class DesafioApplication extends Application {

    private static Context context;
    private static String baseUrl;

    @Override
    public void onCreate() {
        super.onCreate();
        setAppContext(getApplicationContext());

        baseUrl = getResources().getString(R.string.base_url);
    }

    public static Context getAppContext() {
        return context;
    }

    public static void setAppContext(Context _context) {
        context = _context;
    }

    public static boolean connected() {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() == null)
            return false;

        return cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public static String getBaseUrl() {
        return baseUrl;
    }

    public static void setBaseUrl(String baseUrl) {
        DesafioApplication.baseUrl = baseUrl;
    }
}
