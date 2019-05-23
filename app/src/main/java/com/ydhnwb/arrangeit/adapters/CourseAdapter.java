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
import com.ydhnwb.arrangeit.CourseFlowActivity;
import com.ydhnwb.arrangeit.R;
import com.ydhnwb.arrangeit.models.CourseModel;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> implements FastScrollRecyclerView.SectionedAdapter {
    private List<CourseModel> courseList;
    private Context context;

    public CourseAdapter(List<CourseModel> courseList, Context context) {
        this.courseList = courseList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_course, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding(courseList.get(position), context);
    }

    @Override
    public int getItemCount() { return courseList.size(); }

    @NonNull
    @Override
    public String getSectionName(int position) {
        return courseList.get(position).getName().substring(0,1).toUpperCase();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView _name, _code, _letter;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            _name = itemView.findViewById(R.id.tv_name);
            _code = itemView.findViewById(R.id.tv_code);
            _letter = itemView.findViewById(R.id.tv_letter);
        }

        void binding(final CourseModel course, final Context context){
            _name.setText(course.getName());
            _code.setText(course.getCode());
            _letter.setText(course.getName().substring(0,1).toUpperCase());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, CourseFlowActivity.class);
                    i.putExtra("ISNEW", false);
                    i.putExtra("COURSE", course);
                    context.startActivity(i);
                }
            });
        }
    }
}