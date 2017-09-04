package br.com.imusica.desafio.desafioimusica;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Locale;

public class LanguageSelctionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Locale locale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_selction);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {

            toolbar.setTitle(R.string.title_language_selection);
            setSupportActionBar(toolbar);

            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.languages_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String language = (String) adapterView.getItemAtPosition(i);

        if (language.equals("Portugues")) {
            locale = new Locale("pt", "BR");
        }
        else {
            locale = new Locale("en");
        }
    }

    public void confirmButtonClick(View v){

        Configuration config = getResources().getConfiguration();
        config.setLocale(locale);
        Locale.setDefault(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        Intent refresh = new Intent(this, MainActivity.class);
        refresh.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();
        startActivity(refresh);

//        applyOverrideConfiguration(config);
//        mainActivity.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

//        Context configurationContext = createConfigurationContext(config);
//        Intent mainActivity = new Intent(configurationContext, MainActivity.class);
////
//        finish();
//        startActivity(mainActivity);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
