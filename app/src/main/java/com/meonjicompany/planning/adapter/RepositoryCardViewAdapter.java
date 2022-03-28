package com.meonjicompany.planning.adapter;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meonjicompany.planning.DTO.PlanningItemDTO;
import com.meonjicompany.planning.DTO.RepositoryItemDTO;
import com.meonjicompany.planning.R;

import java.util.ArrayList;
import java.util.Calendar;

public class RepositoryCardViewAdapter extends RecyclerView.Adapter<RepositoryCardViewAdapter.RepositoryCardViewHolder>{

    ArrayList<RepositoryItemDTO> repositoryItemDTO;
    private Context context;


    public RepositoryCardViewAdapter(Context context, ArrayList<RepositoryItemDTO> repositoryItemDTO) {
        this.context = context;
        this.repositoryItemDTO = repositoryItemDTO;
    }

    @NonNull
    @Override // 카드뷰 연결
    public RepositoryCardViewAdapter.RepositoryCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.repository_cardview_item,
                parent,false);
        // ViewHolder 객체 생성
        RepositoryCardViewAdapter.RepositoryCardViewHolder planningCardViewHolder = new RepositoryCardViewAdapter.RepositoryCardViewHolder(view);
        return planningCardViewHolder;
    }

    @Override // 뷰 홀더 데이터 바인딩
    public void onBindViewHolder(@NonNull RepositoryCardViewAdapter.RepositoryCardViewHolder holder, int position) {
        holder.plan_date.setText(repositoryItemDTO.get(position).getPlanDate());
        holder.plan_title.setText(repositoryItemDTO.get(position).getPlanTitle());
    }

    @Override // 카드뷰 갯수 리턴
    public int getItemCount() {
        return (repositoryItemDTO != null ? repositoryItemDTO.size() : 0);
    }


    class RepositoryCardViewHolder extends RecyclerView.ViewHolder{
        TextView plan_date, plan_title;
        public RepositoryCardViewHolder(@NonNull View itemView) {
            super(itemView);
            this.plan_date = itemView.findViewById(R.id.plan_date);
            this.plan_title = itemView.findViewById(R.id.plan_title);
        }
    }
}
