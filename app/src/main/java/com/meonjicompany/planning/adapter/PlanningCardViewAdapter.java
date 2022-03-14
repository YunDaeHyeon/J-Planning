package com.meonjicompany.planning.adapter;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.DisplayMetrics;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meonjicompany.planning.DTO.PlanningItemDTO;
import com.meonjicompany.planning.R;
import com.meonjicompany.planning.dialog.PlanDialog;

import java.util.ArrayList;
import java.util.Calendar;

public class PlanningCardViewAdapter extends RecyclerView.Adapter<PlanningCardViewAdapter.PlanningCardViewHolder>{

    ArrayList<PlanningItemDTO> planningItemDTO;
    private TimePickerDialog timePickerDialog; // 타임피커 선언
    private Context context;


    public PlanningCardViewAdapter(Context context, ArrayList<PlanningItemDTO> planningItems) {
        this.context = context;
        this.planningItemDTO = planningItems;
    }

    @NonNull
    @Override // 카드뷰 연결
    public PlanningCardViewAdapter.PlanningCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
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
        holder.plan_contents.setText(planningItemDTO.get(position).getContents());
    }

    @Override // 카드뷰 갯수 리턴
    public int getItemCount() {
        return (planningItemDTO != null ? planningItemDTO.size() : 0);
    }


    class PlanningCardViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        TextView plan_date, plan_contents;
        Button delete_plan_item_btn;
        public PlanningCardViewHolder(@NonNull View itemView) {
            super(itemView);
            this.plan_date = itemView.findViewById(R.id.plan_date);
            this.plan_contents = itemView.findViewById(R.id.plan_contents);
            this.delete_plan_item_btn = itemView.findViewById(R.id.delete_plan_item_btn);
            itemView.setOnCreateContextMenuListener(this); // 메뉴 클릭 리스너 연결
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            // 컨텍스트 메뉴를 생성시킨 뒤 메뉴 항목 선택 시 호출되는 리스너 등록
            MenuItem Edit = contextMenu.add(Menu.NONE, 1001, 1, "수정");
            Edit.setOnMenuItemClickListener(onEditMenu);
        }

        // 켄틱스트 메뉴에서 항목 클릭시 동작 지정
        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case 1001 : // 수정 항목 선택 시
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        View view = LayoutInflater.from(context).inflate(R.layout.fragment_planning_edit_dialog,null,false);
                        builder.setView(view);

                        final TextView edit_planning_time = (TextView) view.findViewById(R.id.edit_planning_time);
                        final EditText edit_planning_contents = (EditText) view.findViewById(R.id.edit_planning_contents);
                        final Button planning_edit_btn = (Button) view.findViewById(R.id.planning_edit_btn);

                        // 입력되어있던 데이터를 다이얼로그에 뿌려줌
                        edit_planning_time.setText(planningItemDTO.get(getAbsoluteAdapterPosition()).getDate());
                        edit_planning_contents.setText(planningItemDTO.get(getAbsoluteAdapterPosition()).getContents());

                        final AlertDialog dialog = builder.create();

                        // 시간 클릭 시
                        edit_planning_time.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                final Calendar calendar = Calendar.getInstance();
                                int hour = calendar.get(Calendar.HOUR);
                                int minute = calendar.get(Calendar.MINUTE);
                                timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        edit_planning_time.setText(String.format("%02d시 %02d분",hourOfDay,minute));
                                    }
                                },hour,minute,false);
                                timePickerDialog.show();
                            }
                        });
                        // 수정버튼 클릭 시
                        planning_edit_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String date = edit_planning_time.getText().toString();
                                String contents = edit_planning_contents.getText().toString();
                                PlanningItemDTO editItem = new PlanningItemDTO(date, contents);
                                planningItemDTO.set(getAbsoluteAdapterPosition(), editItem);
                                notifyItemChanged(getAbsoluteAdapterPosition());
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                        break;
                }
                return true;
            }
        };
    }
}