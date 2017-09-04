package br.com.imusica.desafio.desafioimusica.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import br.com.imusica.desafio.desafioimusica.R;
import br.com.imusica.desafio.desafioimusica.domain.AgendaRecord;

/**
 * Created by gustavoamg on 01/06/17.
 */

public class RecordsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private OnItemClick callback;

    public interface OnItemClick {
        void onItemClick (AgendaRecord record);
    }

    private List<AgendaRecord> recordList;

    class AgendaRecordsViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView, phoneTextView;

        public AgendaRecordsViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.item_agenda_record_name);
            phoneTextView = (TextView) itemView.findViewById(R.id.item_agenda_record_phone);
        }
    }

    public RecordsAdapter(List<AgendaRecord> recordList, OnItemClick onItemClickListener) {
        this.recordList = recordList;
        this.callback = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_agenda_record, parent, false);
        return new AgendaRecordsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof AgendaRecordsViewHolder) {
            final AgendaRecord record = recordList.get(position);

            AgendaRecordsViewHolder itemViewHolder = (AgendaRecordsViewHolder) holder;
            itemViewHolder.nameTextView.setText(record.getName());
            itemViewHolder.phoneTextView.setText(record.getPhone());

            itemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.onItemClick(record);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(recordList != null)
            return recordList.size();
        return 0;
    }
}
