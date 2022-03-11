package com.meonjicompany.planning.fragment;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.meonjicompany.planning.DTO.PlanningItemDTO;
import com.meonjicompany.planning.R;
import com.meonjicompany.planning.adapter.PlanningCardViewAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PlanFragment extends Fragment implements View.OnClickListener{
    TextView selectDate;
    Button add_plan_item_btn;
    // 리사이클러뷰 객체 생성
    RecyclerView recyclerView;
    // 리사이클러뷰 어뎁터 객체 생성
    PlanningCardViewAdapter planningCardViewAdapter;
    // 라사이클러뷰에 뿌려줄 DTO 객체 생성
    ArrayList<PlanningItemDTO> planningItems;
    // 캘린더 객체 생성
    Calendar calendar = Calendar.getInstance();
    long now = System.currentTimeMillis();
    Date date = new Date(now);
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");

    // 날짜 선택을 위한 데이터 피커 리스너 설정
    DatePickerDialog.OnDateSetListener datePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
            calendar.set(Calendar.YEAR,year);
            calendar.set(Calendar.MONTH,month);
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            // 날짜 불러오기
            updateLabel();
        }
    };
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PlanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Planning.
     */
    // TODO: Rename and change types and number of parameters
    public static com.meonjicompany.planning.fragment.PlanFragment newInstance(String param1, String param2) {
        com.meonjicompany.planning.fragment.PlanFragment fragment = new com.meonjicompany.planning.fragment.PlanFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    // 메인 뷰 설정
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_planning,container,false);
        selectDate = (TextView) rootView.findViewById(R.id.selectDate);
        add_plan_item_btn = (Button) rootView.findViewById(R.id.add_plan_item_btn);
        // 텍스트뷰 현재 날짜 출력시키기
        selectDate.setText(simpleDateFormat.format(date));
        selectDate.setOnClickListener(this); // 텍스트뷰 클릭 이벤트 리스너 연동
        add_plan_item_btn.setOnClickListener(this);

        // 리사이클러뷰(카드뷰)에 데이터 바인딩
        recyclerView = rootView.findViewById(R.id.plan_RecyclearView);
        planningItems = new ArrayList<>();
//        planningItems.add(new PlanningItem("2022년 3월 9일","서울여행",
//                "더미데이터더미데이터더미데이터더미데이터더미데이터더미데이터더미데이터더미데이터더미데이터"));

        // 리사이클러뷰 연결
        planningCardViewAdapter = new PlanningCardViewAdapter(planningItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(planningCardViewAdapter);
        return rootView;
    }

    // 클릭 리스너
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.selectDate:
                new DatePickerDialog(getActivity(),datePicker, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.add_plan_item_btn:
                planningCardViewAdapter.notifyItemInserted(0);
                break;
        }
    }

    // 데이터 피커 날짜 업데이트 - TextView 변경
    private void updateLabel(){
        String dateFormat = "yyyy년 MM월 dd일"; // 날짜 출력 형식 지정
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.KOREA);
        selectDate.setText(simpleDateFormat.format(calendar.getTime()));
    }
}