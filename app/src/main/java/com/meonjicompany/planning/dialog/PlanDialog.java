package com.meonjicompany.planning.dialog;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.meonjicompany.planning.R;
import com.meonjicompany.planning.fragment.PlanFragment;

import java.util.Calendar;

public class PlanDialog extends DialogFragment implements View.OnClickListener {
    private Button add; // 추가 버튼
    private TextView time; // 시간 텍스트뷰
    private EditText contents; // 내용
    private TimePickerDialog timePickerDialog; // 타임피커 선언
    PlanAddItem planAddItem;
    public static final String TAG_PLAN_DIALOG = "plan_dialog";

    public PlanDialog(){}

    public static PlanDialog getInstance(){
        PlanDialog planDialog = new PlanDialog();
        return planDialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(getDialog().getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        Window window = getDialog().getWindow();
        window.setAttributes(layoutParams);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.fragment_planning_dialog, container);
        add = (Button) view.findViewById(R.id.planning_add_btn); // 추가 버튼
        time = (TextView) view.findViewById(R.id.planning_time);
        contents = (EditText) view.findViewById(R.id.planning_contents);
        clickEvents(); // 클랙 이벤트 실행
        setCancelable(false); // FragmentDialog 화면 밖 터치 시 dismiss 방지
        return view;
    }

    public void clickEvents(){
        add.setOnClickListener(this); // 추가 버튼 클릭 리스너 지정
        time.setOnClickListener(this); // 시간 텍스트 클릭 리스너 지정
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
                // 추가 버튼 클릭
            case R.id.planning_add_btn:
                planAddItem.addPlan(String.valueOf(time.getText()), String.valueOf(contents.getText()));
                dismiss(); // 다이얼로그 종료
                break;
                // 시간 설정 텍스트뷰 클릭
            case R.id.planning_time:
                final Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR);
                int minute = calendar.get(Calendar.MINUTE);
                timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        time.setText(String.format("%02d시 %02d분",hourOfDay,minute));
                    }
                },hour,minute,false);
                timePickerDialog.show();
                break;
        }
    }

    public void setDialogResult(PlanAddItem planAddItem){
        this.planAddItem = planAddItem;
    }

    public interface PlanAddItem{
        void addPlan(String time, String contents);
    }
}
