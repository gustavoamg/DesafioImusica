package br.com.imusica.desafio.desafioimusica;

import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItem;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.com.imusica.desafio.desafioimusica.connection.Connection;
import br.com.imusica.desafio.desafioimusica.connection.ConnectionCreateRecord;
import br.com.imusica.desafio.desafioimusica.connection.ConnectionDeleteRecord;
import br.com.imusica.desafio.desafioimusica.connection.ConnectionEditRecord;
import br.com.imusica.desafio.desafioimusica.connection.ConnectionListener;
import br.com.imusica.desafio.desafioimusica.domain.AgendaRecord;

public class EditRecordActivity extends AppCompatActivity implements ConnectionListener{

    private AgendaRecord record;

    private EditText inputName;
    private EditText inputPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_record);

        Bundle params = getIntent().getExtras();
        if(params != null){
            if(params.containsKey("record")){
                record = params.getParcelable("record");
            }
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {

            if(record == null)
                toolbar.setTitle(R.string.title_create_record);
            else {
                toolbar.setTitle(R.string.title_edit_rcord);
            }

            setSupportActionBar(toolbar);

            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        inputName = (EditText) findViewById(R.id.nameEditText);
        inputPhone = (EditText) findViewById(R.id.phoneEditText);

        if(record != null) {
            inputName.setText(record.getName());
            inputPhone.setText(record.getPhone());
        }
    }

    public void saveButtonClickAction(View v) {
        if("".equals(inputName.getText().toString())){
            Toast.makeText(this, R.string.message_name_cant_be_empty, Toast.LENGTH_LONG).show();
            return;
        }

        if("".equals(inputPhone.getText().toString())){
            Toast.makeText(this, R.string.messge_phone_cant_be_empty, Toast.LENGTH_LONG).show();
        }

        if(record != null) {
            record.setName(inputName.getText().toString());
            record.setPhone(inputPhone.getText().toString());

            ConnectionEditRecord connectionEditRecord = new ConnectionEditRecord(this, record);
            connectionEditRecord.start();
        }
        else {
            ConnectionCreateRecord connectionCreateRecord = new ConnectionCreateRecord(this, inputName.getText().toString(), inputPhone.getText().toString());
            connectionCreateRecord.start();
        }

    }

    @Override
    public void connectionReturnedWithSuccess(Connection connection) {

        if(connection instanceof ConnectionDeleteRecord){
            Toast.makeText(this, R.string.message_record_successfull_deleted, Toast.LENGTH_LONG).show();
        }

        if(connection instanceof ConnectionCreateRecord) {
            record = (AgendaRecord) connection.getResult();
            Toast.makeText(this, R.string.message_record_was_successfully_created, Toast.LENGTH_LONG).show();
        }

        if(connection instanceof ConnectionEditRecord){
            Toast.makeText(this, R.string.message_changes_were_saved, Toast.LENGTH_LONG).show();
        }

        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void connectionReturnedWithFailure(Connection connection) {
        Toast.makeText(this, R.string.message_there_was_an_error_updating, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if(record != null)
            getMenuInflater().inflate(R.menu.edit_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_delete){
           displayConfirmDeleteDialog();
        }
        else {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void displayConfirmDeleteDialog() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.title_remove_record)
                .setMessage(R.string.question_do_you_really_want_to_delete_record)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ConnectionDeleteRecord connectionDeleteRecord = new ConnectionDeleteRecord(EditRecordActivity.this, record.getId());
                        connectionDeleteRecord.start();
                    }

                })
                .setNegativeButton(R.string.no, null)
                .show();

    }
}
