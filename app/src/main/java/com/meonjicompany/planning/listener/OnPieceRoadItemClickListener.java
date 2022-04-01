package com.meonjicompany.planning.listener;

import android.view.View;

import com.meonjicompany.planning.adapter.RepositoryCardViewAdapter;

// Repository Fragment에 위치하는 아이템 터치 리스터 인터페이스
public interface OnPieceRoadItemClickListener {
    void onItemClick(View view, int position);
}
