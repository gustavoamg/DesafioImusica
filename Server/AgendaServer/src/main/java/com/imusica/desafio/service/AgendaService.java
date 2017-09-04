package com.imusica.desafio.service;

import com.imusica.desafio.domain.Record;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gustavoamg on 30/05/17.
 *
 * This class is the main Agenda service.  Holds the records list and provide CRUD operations over the records.
 */
public class AgendaService {

    private static AgendaService instance;
    private static List<Record> agendaRecords;

    /**
     * Returns the current instance of the service. If there's no active instance a new one will be created
     * @return the active service instance
     */

    public static AgendaService getService(){
        if(instance == null)
            instance = new AgendaService();

        return instance;
    }

    private AgendaService() {
        //Initialize the records
        agendaRecords = new ArrayList<>();

        Record first = new Record(1, "Gustavo", "21972823390");
        Record second = new Record(2, "Thomas", "21333333303");
        Record third = new Record(3, "Samuel",  "21001001110");

        agendaRecords.add(first);
        agendaRecords.add(second);
        agendaRecords.add(third);

    }

    /**
     * Loads the record list
     * @return an ArrayList of Record
     */
    public List<Record> getRecords(){
        return new ArrayList<>(agendaRecords);
    }

    /**
     * Creates a new record to the agenda
     * @param name the name of the record
     * @param phone the phone of the record
     * @return the new created record
     * @throws NullPointerException if name or phone be null
     */

    public synchronized Record addRecod(String name, String phone) throws NullPointerException{
        if(name == null)
            throw new NullPointerException("Name can\'t be null");
        if(phone == null)
            throw new NullPointerException("Phone can\'t be null");

        int nextId = 0;
        for(Record r : agendaRecords) {
            if (r.getId() > nextId)
                nextId = r.getId();
        }
        nextId++;

        Record newRecord = new Record(nextId, name, phone);
        agendaRecords.add(newRecord);

        return newRecord;
    }

    /**
     * Updates a record in the agenda
     * @param id id of the record to be updated
     * @param name new value for name
     * @param phone new value for phone
     * @return true if a record was updated
     * @throws NullPointerException if name or phone be null
     */

    public synchronized boolean updateRecord(int id, String name, String phone)throws NullPointerException {
        if(name == null)
            throw new NullPointerException("Name can\'t be null");
        if(phone == null)
            throw new NullPointerException("Phone can\'t be null");

        for(Record r : agendaRecords) {
            if (r.getId() == id) {
                r.setName(name);
                r.setPhone(phone);
                return true;
            }
        }
        return false;
    }

    /**
     * Removes a record from the agenda
     * @param id id of the record to be removed
     * @return true if a record was removed
     */

    public synchronized boolean  removeRecord(int id){
        for(Record r : agendaRecords) {
            if (r.getId() == id) {
                agendaRecords.remove(r);
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a single record from the list
     * @param id the id of the record to be found
     * @return the record instance or null if not found
     */
    public Record getRecord(int id){
        for(Record r : agendaRecords){
            if(r.getId() == id)
                return r;
        }
        return null;
    }

}
