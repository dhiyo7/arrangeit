package com.ydhnwb.arrangeit.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;
import com.ydhnwb.arrangeit.LecturerFlowActivity;
import com.ydhnwb.arrangeit.R;
import com.ydhnwb.arrangeit.models.LecturerModel;
import java.util.List;

public class LecturerAdapter extends RecyclerView.Adapter<LecturerAdapter.ViewHolder> implements FastScrollRecyclerView.SectionedAdapter {
    private List<LecturerModel> lecturerList;
    private Context context;

    public LecturerAdapter(List<LecturerModel> lecturerList, Context context) {
        this.lecturerList = lecturerList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_lecturer, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding(lecturerList.get(position), context);
    }

    @Override
    public int getItemCount() { return lecturerList.size(); }

    @NonNull
    @Override
    public String getSectionName(int position) {
        return lecturerList.get(position).getName().substring(0,1).toUpperCase();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView _name, _nipy, _letter;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            _letter = itemView.findViewById(R.id.tv_letter);
            _name = itemView.findViewById(R.id.tv_name);
            _nipy = itemView.findViewById(R.id.tv_nipy);
        }

        void binding(final LecturerModel lecturer,final Context context){
            _name.setText(lecturer.getName());
            _nipy.setText(lecturer.getNipy());
            _letter.setText(lecturer.getName().substring(0,1).toUpperCase());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, LecturerFlowActivity.class);
                    intent.putExtra("LECTURER", lecturer);
                    intent.putExtra("ISNEW", false);
                    context.startActivity(intent);
                }
            });
        }
    }
}