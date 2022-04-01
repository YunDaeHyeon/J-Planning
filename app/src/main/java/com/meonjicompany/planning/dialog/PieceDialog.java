package com.meonjicompany.planning.dialog;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meonjicompany.planning.DTO.PieceItemDTO;
import com.meonjicompany.planning.DTO.PieceRoadDTO;
import com.meonjicompany.planning.R;
import com.meonjicompany.planning.adapter.PieceCardViewAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PieceDialog extends DialogFragment{
    RecyclerView recyclerView; // 리사이클러뷰 객체 생성
    PieceCardViewAdapter pieceCardViewAdapter; // 리사이클러뷰 어뎁터 객체 생성
    ArrayList<PieceItemDTO> pieceItemDTO = new ArrayList<>(); // 리사이클러뷰에 뿌려줄 DTO 객체 생성

    public PieceDialog(){}

    public static PieceDialog getInstance(){
        PieceDialog pieceDialog = new PieceDialog();
        return pieceDialog;
    }

    public void setItemInstance(List<PieceRoadDTO> pieceRoadDTO){
        if(pieceRoadDTO != null){
            for(int i = 0 ; i < pieceRoadDTO.size(); i++){
                this.pieceItemDTO.add(new PieceItemDTO(pieceRoadDTO.get(i).getPieceId(),
                        pieceRoadDTO.get(i).getPieceTime(),pieceRoadDTO.get(i).getPieceContents()));
            }
        }else{
            Log.d("NPE","PieceRoadDTO의 값이 null입니다.");
        }
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
        View view = inflater.inflate(R.layout.fragment_repository_piece_dialog, container);
        // 리사이클러뷰 바인딩
        recyclerView = view.findViewById(R.id.piece_RecyclearView);
        // 리사이클러뷰 DTO 생성
        // pieceItemDTO = new ArrayList<>();
        // 리사이클러뷰 연결
        pieceCardViewAdapter = new PieceCardViewAdapter(getActivity(), pieceItemDTO);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(pieceCardViewAdapter);
        return view;
    }
}