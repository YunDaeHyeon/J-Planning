package com.meonjicompany.planning.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meonjicompany.planning.DTO.PlanningItemDTO;
import com.meonjicompany.planning.R;

import java.util.ArrayList;

public class PlanningCardViewAdapter extends RecyclerView.Adapter<com.meonjicompany.planning.adapter.PlanningCardViewAdapter.PlanningCardViewHolder>{

    ArrayList<PlanningItemDTO> planningItems;

    public PlanningCardViewAdapter(ArrayList<PlanningItemDTO> planningItems) {
        this.planningItems = planningItems;
    }

    @NonNull
    @Override // 카드뷰 연결
    public com.meonjicompany.planning.adapter.PlanningCardViewAdapter.PlanningCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.planning_cardview_item,
                parent,false);
        return new com.meonjicompany.planning.adapter.PlanningCardViewAdapter.PlanningCardViewHolder(view);
    }

    @Override // 뷰 홀더 데이터 바인딩
    public void onBindViewHolder(@NonNull com.meonjicompany.planning.adapter.PlanningCardViewAdapter.PlanningCardViewHolder holder, int position) {
        holder.plan_date.setText(planningItems.get(position).getDate());
        holder.plan_title.setText(planningItems.get(position).getTitle());
        holder.plan_contents.setText(planningItems.get(position).getContents());
    }

    @Override // 카드뷰 갯수 리턴
    public int getItemCount() {
        return planningItems.size();
    }

    class PlanningCardViewHolder extends RecyclerView.ViewHolder{
        TextView plan_date;
        EditText plan_title, plan_contents;
        public PlanningCardViewHolder(@NonNull View itemView) {
            super(itemView);
            this.plan_date = itemView.findViewById(R.id.plan_date);
            this.plan_title = itemView.findViewById(R.id.plan_title);
            this.plan_contents = itemView.findViewById(R.id.plan_contents);
        }
    }
}