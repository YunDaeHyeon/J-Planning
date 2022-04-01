package com.meonjicompany.planning.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meonjicompany.planning.DTO.PieceItemDTO;
import com.meonjicompany.planning.R;
import com.meonjicompany.planning.listener.OnPieceRoadItemClickListener;

import java.util.ArrayList;

public class PieceCardViewAdapter extends RecyclerView.Adapter<PieceCardViewAdapter.PieceCardViewHolder>{

    ArrayList<PieceItemDTO> pieceItemDTO;

    private Context context;

    public PieceCardViewAdapter(Context context, ArrayList<PieceItemDTO> pieceItemDTO) {
        this.context = context;
        this.pieceItemDTO = pieceItemDTO;
    }

    @NonNull
    @Override // 카드뷰 연결
    public PieceCardViewAdapter.PieceCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.repository_piece_cardview_item,
                parent,false);
        // ViewHolder 객체 생성
        PieceCardViewAdapter.PieceCardViewHolder pieceCardViewHolder = new PieceCardViewAdapter.PieceCardViewHolder(view);
        return pieceCardViewHolder;
    }

    @Override // 뷰 홀더 데이터 바인딩
    public void onBindViewHolder(@NonNull PieceCardViewAdapter.PieceCardViewHolder holder, int position) {
        holder.piece_time.setText(pieceItemDTO.get(position).getPieceTime());
        holder.piece_contents.setText(pieceItemDTO.get(position).getPieceContents());
    }

    @Override // 카드뷰 갯수 리턴
    public int getItemCount() {
        return (pieceItemDTO != null ? pieceItemDTO.size() : 0);
    }

    // 뷰 홀더에 저장되어 화면에 표시되고, 필요에 따라 생성과 재활용이 반복된다.
    public class PieceCardViewHolder extends RecyclerView.ViewHolder{
        TextView piece_time, piece_contents;
        public PieceCardViewHolder(@NonNull View itemView) {
            super(itemView);
            this.piece_time = itemView.findViewById(R.id.piece_time);
            this.piece_contents = itemView.findViewById(R.id.piece_contents);
        }
    }
}
