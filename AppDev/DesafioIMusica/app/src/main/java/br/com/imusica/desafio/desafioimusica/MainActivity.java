package br.com.imusica.desafio.desafioimusica;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import br.com.imusica.desafio.desafioimusica.adapter.RecordsAdapter;
import br.com.imusica.desafio.desafioimusica.connection.Connection;
import br.com.imusica.desafio.desafioimusica.connection.ConnectionListener;
import br.com.imusica.desafio.desafioimusica.connection.ListRecordsConnection;
import br.com.imusica.desafio.desafioimusica.domain.AgendaRecord;

public class MainActivity extends AppCompatActivity implements ConnectionListener, RecordsAdapter.OnItemClick{

    private static final int EDIT_RECORD = 22;
    private static final int CREATE_RECORD = 23;



    RecyclerView recyclerView;
    RecordsAdapter recordsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {

            toolbar.setTitle(R.string.app_name);
            setSupportActionBar(toolbar);

            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        recyclerView = (RecyclerView) findViewById(R.id.activity_main_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        ListRecordsConnection listRecordsConnection = new ListRecordsConnection(this);
        listRecordsConnection.start();
    }

    @Override
    public void connectionReturnedWithSuccess(Connection connection) {
        if(connection instanceof ListRecordsConnection){
            List<AgendaRecord> connectionResults = (List<AgendaRecord>) connection.getResult();
            recordsAdapter = new RecordsAdapter(connectionResults, this);

            recyclerView.setAdapter(recordsAdapter);
        }
    }

    @Override
    public void connectionReturnedWithFailure(Connection connection) {

    }

    @Override
    public void onItemClick(AgendaRecord record) {
        openEditActivity(record);
    }

    private void openEditActivity(AgendaRecord record){
        Intent itemDetailIntent = new Intent(this, EditRecordActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("record", record);
        itemDetailIntent.putExtras(bundle);
        startActivityForResult(itemDetailIntent, EDIT_RECORD);
    }

    public void addButtonClick(View v){
        openCreateActivity();
    }

    private void openCreateActivity(){
        Intent itemDetailIntent = new Intent(this, EditRecordActivity.class);
        startActivityForResult(itemDetailIntent, CREATE_RECORD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            ListRecordsConnection listRecordsConnection = new ListRecordsConnection(this);
            listRecordsConnection.start();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_language){
            openLanguageSettingsActivity();
        }

        return super.onOptionsItemSelected(item);
    }

    private void openLanguageSettingsActivity(){
        Intent languageSelectIntent = new Intent(this, LanguageSelctionActivity.class);
        startActivity(languageSelectIntent);
    }
}
