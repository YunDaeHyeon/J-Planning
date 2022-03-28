package com.meonjicompany.planning.fragment;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.meonjicompany.planning.DTO.Piece;
import com.meonjicompany.planning.DTO.PlanPOJO;
import com.meonjicompany.planning.DTO.PlanningItemDTO;
import com.meonjicompany.planning.R;
import com.meonjicompany.planning.activity.IndexPage;
import com.meonjicompany.planning.adapter.PlanningCardViewAdapter;
import com.meonjicompany.planning.dialog.PlanDialog;
import com.meonjicompany.planning.retrofit.Message;
import com.meonjicompany.planning.retrofit.RetrofitAPI;
import com.meonjicompany.planning.retrofit.RetrofitClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlanFragment extends Fragment implements View.OnClickListener{
    public static int planId = 0; // 계획 식별자
    TextView selectDate;
    EditText title;
    Button add_plan_item_btn, save_plan_btn;
    // 리사이클러뷰 객체 생성
    RecyclerView recyclerView;
    // 리사이클러뷰 어뎁터 객체 생성
    PlanningCardViewAdapter planningCardViewAdapter;
    // 라사이클러뷰에 뿌려줄 DTO 객체 생성
    static ArrayList<PlanningItemDTO> planningItemDTO;
    // 캘린더 객체 생성
    Calendar calendar = Calendar.getInstance();
    // 다이얼로그 선언
    PlanDialog planDialog;
    private RetrofitAPI retrofitAPI; // 통신을 위한 Retrofit 객체
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
    public static PlanFragment newInstance(String param1, String param2) {
        PlanFragment fragment = new PlanFragment();
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
        title = (EditText) rootView.findViewById(R.id.title);
        add_plan_item_btn = (Button) rootView.findViewById(R.id.add_plan_item_btn);
        save_plan_btn = (Button) rootView.findViewById(R.id.save_plan_btn);
        // 텍스트뷰 현재 날짜 출력시키기
        selectDate.setText(simpleDateFormat.format(date));
        selectDate.setOnClickListener(this); // 텍스트뷰 클릭 이벤트 리스너 연동
        add_plan_item_btn.setOnClickListener(this);
        save_plan_btn.setOnClickListener(this);

        //roadPlanning(); // 계획 불러오기
        // 리사이클러뷰(카드뷰)에 데이터 바인딩
        recyclerView = rootView.findViewById(R.id.plan_RecyclearView);
        // 리사이클러뷰 DTO 객체 생성
        planningItemDTO = new ArrayList<>();
        // 리사이클러뷰 연결
        planningCardViewAdapter = new PlanningCardViewAdapter(getActivity(), planningItemDTO);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(planningCardViewAdapter);
        return rootView;
    }

//    public static ArrayList<PlanningItemDTO> planItemGetInstance(){
//        return planningItemDTO;
//    }

    // 클릭 리스너
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.selectDate: // 날짜 선택 TextView 클릭 시
                new DatePickerDialog(getActivity(),datePicker, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.add_plan_item_btn: // 추가 버튼 클릭 시
                planDialog = PlanDialog.getInstance();
                planDialog.show(getActivity().getSupportFragmentManager(), PlanDialog.TAG_PLAN_DIALOG);
                planDialog.setDialogResult(new PlanDialog.PlanAddItem() {
                    @Override
                    public void addPlan(String time, String contents) {
                        planningItemDTO.add(new PlanningItemDTO(time, contents));
                        planningCardViewAdapter.notifyItemInserted(planningItemDTO.size());
                    }
                });
                break;
            case R.id.save_plan_btn: // 저장 버튼 클릭 시
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("저장");
                builder.setMessage("저장하시겠습니까?");
                // "예"를 클릭했을 시
                builder.setPositiveButton("예",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                PlanPOJO planPOJO = createPOJO();
                                RetrofitClient retrofitClient = RetrofitClient.getInstance();
                                if(retrofitClient != null){
                                    retrofitAPI = RetrofitClient.getRetrofitAPI();
                                    retrofitAPI.savePlan(planPOJO).enqueue(new Callback<Message>() {
                                        @Override
                                        public void onResponse(Call<Message> call, Response<Message> response) {
                                            if(response.isSuccessful()){
                                                final Message message = response.body(); // message.getMessage() - 유저 식별자를 끌고옴.
                                                planId = Integer.parseInt(message.getMessage());
                                                Log.d("planId : ", String.valueOf(planId));
                                                Toast.makeText(getActivity(), "저장을 완료하였습니다.", Toast.LENGTH_SHORT).show();
                                            }else{
                                                Log.d("오류 발생","onResponse 실패 ( 3xx, 4xx 오류)");
                                                Toast.makeText(getActivity(), "onResponse 실패, 3xx, 4xx 오류", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        @Override
                                        public void onFailure(Call<Message> call, Throwable t) {
                                            t.printStackTrace();
                                            Toast.makeText(getActivity(), "서버와 통신중 에러가 발생하였습니다.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
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
                break;
        }
    }

    // 데이터 피커 날짜 업데이트 - TextView 변경
    private void updateLabel(){
        String dateFormat = "yyyy년 MM월 dd일"; // 날짜 출력 형식 지정
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.KOREA);
        selectDate.setText(simpleDateFormat.format(calendar.getTime()));
    }

    // 계획 불러오기
    private void roadPlanning(){
        int userId = IndexPage.userId;
    }

    private PlanPOJO createPOJO(){
        try{
            ArrayList<Piece> piece = new ArrayList<>();
            for(int i = 0 ; i < planningItemDTO.size(); i++){
                piece.add(new Piece(planningItemDTO.get(i).getDate(),
                        planningItemDTO.get(i).getContents()));
            }
            PlanPOJO planPOJO = new PlanPOJO(IndexPage.userId, title.getText().toString(),
                    selectDate.getText().toString(), piece);
            return planPOJO;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}