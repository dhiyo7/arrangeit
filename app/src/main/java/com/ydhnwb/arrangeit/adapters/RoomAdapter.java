package com.ydhnwb.arrangeit.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ydhnwb.arrangeit.R;
import com.ydhnwb.arrangeit.RoomFlowActivity;
import com.ydhnwb.arrangeit.models.RoomModel;
import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {
    private List<RoomModel> roomList;
    private Context context;

    public RoomAdapter(List<RoomModel> roomList, Context context) {
        this.roomList = roomList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_room, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) { holder.binding(roomList.get(position), context);}

    @Override
    public int getItemCount() { return roomList.size(); }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView _name,_desc;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            _name = itemView.findViewById(R.id.tv_name);
            _desc = itemView.findViewById(R.id.tv_desc);
        }

        void binding(final RoomModel room, final Context context){
            _name.setText(room.getName());
            _desc.setText(room.getDescription());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, RoomFlowActivity.class);
                    i.putExtra("ROOM", room);
                    i.putExtra("ISNEW", false);
                    context.startActivity(i);
                }
            });
        }
    }
}