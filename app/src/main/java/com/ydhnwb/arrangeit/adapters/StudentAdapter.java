package com.ydhnwb.arrangeit.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;
import com.ydhnwb.arrangeit.R;
import com.ydhnwb.arrangeit.StudentFlowActivity;
import com.ydhnwb.arrangeit.models.StudentModel;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> implements FastScrollRecyclerView.SectionedAdapter {
    private List<StudentModel> studentList;
    private Context context;

    public StudentAdapter(List<StudentModel> studentList, Context context) {
        this.studentList = studentList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_student, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) { holder.binding(studentList.get(position), context); }

    @Override
    public int getItemCount() { return studentList.size(); }

    @NonNull
    @Override
    public String getSectionName(int position) { return studentList.get(position).getName().substring(0,1).toUpperCase(); }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView name, nim, letter;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            nim = itemView.findViewById(R.id.tv_nim);
            letter = itemView.findViewById(R.id.tv_letter);
        }

        void binding(final StudentModel student, final Context context){
            name.setText(student.getName());
            nim.setText(student.getNim());
            letter.setText(student.getName().substring(0,1).toUpperCase());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, StudentFlowActivity.class);
                    intent.putExtra("STUDENT", student);
                    intent.putExtra("ISNEW", false);
                    context.startActivity(intent);
                    //Toast.makeText(context, student.getName()+" tapped", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}