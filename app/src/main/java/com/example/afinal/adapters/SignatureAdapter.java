package com.example.afinal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.afinal.R;
import com.example.afinal.Signature;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class SignatureAdapter extends RecyclerView.Adapter<SignatureAdapter.ViewHolder> {

    public List<Signature> mData;
    private LayoutInflater mInflater;
    private Context context;

    public SignatureAdapter(Context context, List<Signature> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_signature, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // When a cell is shown, loading data into view
        Signature signature = mData.get(position);
        holder.tvName.setText(signature.name);
        holder.tvId.setText(signature.id);
        holder.tvJobTitle.setText(signature.jobTitle);
        Date date = new Date(Long.parseLong(signature.date));
        holder.tvDate.setText(DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(date));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvName;
        TextView tvId;
        TextView tvJobTitle;
        TextView tvDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Finding views
            tvName = itemView.findViewById(R.id.tvName);
            tvId = itemView.findViewById(R.id.tvId);
            tvJobTitle = itemView.findViewById(R.id.tvJobTitle);
            tvDate = itemView.findViewById(R.id.tvDate);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
