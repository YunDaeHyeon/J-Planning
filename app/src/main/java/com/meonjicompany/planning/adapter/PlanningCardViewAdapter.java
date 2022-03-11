package com.meonjicompany.planning.adapter;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meonjicompany.planning.DTO.PlanningItemDTO;
import com.meonjicompany.planning.R;

import java.util.ArrayList;

public class PlanningCardViewAdapter extends RecyclerView.Adapter<PlanningCardViewAdapter.PlanningCardViewHolder>{

    ArrayList<PlanningItemDTO> planningItemDTO;

    public PlanningCardViewAdapter(ArrayList<PlanningItemDTO> planningItems) {
        this.planningItemDTO = planningItems;
    }

    @NonNull
    @Override // 카드뷰 연결
    public PlanningCardViewAdapter.PlanningCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.planning_cardview_item,
                parent,false);
        // ViewHolder 객체 생성
        PlanningCardViewHolder planningCardViewHolder = new PlanningCardViewHolder(view);
        planningCardViewHolder.delete_plan_item_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("삭제");
                builder.setMessage("해당 항목을 삭제하시겠습니까?");

                // "예"를 클릭했을 시
                builder.setPositiveButton("예",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                planningItemDTO.remove(planningCardViewHolder.getAbsoluteAdapterPosition());
                                notifyItemRemoved(planningCardViewHolder.getAbsoluteAdapterPosition());
                                notifyItemRangeChanged(planningCardViewHolder.getAbsoluteAdapterPosition(), planningItemDTO.size());
                            }
                        });
                // "아니요" 클릭 시
                builder.setNegativeButton("아니요",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                builder.show();
            }
        });
        return planningCardViewHolder;
    }

    @Override // 뷰 홀더 데이터 바인딩
    public void onBindViewHolder(@NonNull PlanningCardViewAdapter.PlanningCardViewHolder holder, int position) {
        holder.plan_date.setText(planningItemDTO.get(position).getDate());
        holder.plan_title.setText(planningItemDTO.get(position).getTitle());
        holder.plan_contents.setText(planningItemDTO.get(position).getContents());
    }

    @Override // 카드뷰 갯수 리턴
    public int getItemCount() {
        return (planningItemDTO != null ? planningItemDTO.size() : 0);
    }

    class PlanningCardViewHolder extends RecyclerView.ViewHolder{
        TextView plan_date, plan_title, plan_contents;
        Button delete_plan_item_btn;
        public PlanningCardViewHolder(@NonNull View itemView) {
            super(itemView);
            this.plan_date = itemView.findViewById(R.id.plan_date);
            this.plan_title = itemView.findViewById(R.id.plan_title);
            this.plan_contents = itemView.findViewById(R.id.plan_contents);
            this.delete_plan_item_btn = itemView.findViewById(R.id.delete_plan_item_btn);
        }
    }
}