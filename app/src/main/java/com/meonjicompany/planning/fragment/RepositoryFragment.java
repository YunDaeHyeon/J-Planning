package com.meonjicompany.planning.fragment;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.meonjicompany.planning.DTO.PlanRoadDTO;
import com.meonjicompany.planning.DTO.PlanningItemDTO;
import com.meonjicompany.planning.DTO.RepositoryItemDTO;
import com.meonjicompany.planning.R;
import com.meonjicompany.planning.activity.IndexPage;
import com.meonjicompany.planning.adapter.RepositoryCardViewAdapter;
import com.meonjicompany.planning.retrofit.Message;
import com.meonjicompany.planning.retrofit.RetrofitAPI;
import com.meonjicompany.planning.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RepositoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RepositoryFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener{
    RecyclerView recyclerView; // 리사이클러뷰 객체 생성
    RepositoryCardViewAdapter repositoryCardViewAdapter; // 리사이클러뷰 어뎁터 객체 생성
    ArrayList<RepositoryItemDTO> repositoryItemDTO; // 리사이클러뷰에 뿌려줄 DTO 객체 생성
    SwipeRefreshLayout swipeRefreshLayout; // 새로고침을 위한 SwipeRefreshLayout 객체
    private RetrofitAPI retrofitAPI; // 통신을 위한 Retrofit 객체
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RepositoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.4
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Repository.
     */
    // TODO: Rename and change types and number of parameters
    public static RepositoryFragment newInstance(String param1, String param2) {
        RepositoryFragment fragment = new RepositoryFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_repository,container,false);
        // 새로고침 객체 바인딩
        swipeRefreshLayout = rootView.findViewById(R.id.swipeLayout);
        swipeRefreshLayout.setOnRefreshListener(this); // 새로고침 리스너 연결
        // 리사이클러뷰 바인딩
        recyclerView = rootView.findViewById(R.id.repository_RecyclearView);
        // 리사이클려뷰 DTO 생성
        repositoryItemDTO = new ArrayList<>();
        // 리사이클러뷰 연결
        repositoryCardViewAdapter = new RepositoryCardViewAdapter(getActivity(), repositoryItemDTO);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(repositoryCardViewAdapter);
        // Profile에 있는 리사이클러뷰 DTO 객체와 Repository에 있는 리사이클러뷰 DTO 객체 연동
        return rootView;
    }

    // 클릭 리스너
    @Override
    public void onClick(View view) {
    }

    // 새로고침 리스너
    @Override
    public void onRefresh() {
        // 원하는 기능
        roadPlanning();

        // 새로 고침 완료
        swipeRefreshLayout.setRefreshing(false);
    }

    private void roadPlanning(){
        RetrofitClient retrofitClient = RetrofitClient.getInstance();
        if(retrofitClient != null){
            retrofitAPI = RetrofitClient.getRetrofitAPI();
            retrofitAPI.roadPlan(IndexPage.userId).enqueue(new Callback<List<PlanRoadDTO>>() {
                @Override
                public void onResponse(Call<List<PlanRoadDTO>> call, Response<List<PlanRoadDTO>> response) {
                    if(response.isSuccessful()){
                        List<PlanRoadDTO> planRoadDTO = response.body();
                        for(int i = repositoryItemDTO.size() ; i < planRoadDTO.size(); i++){
                            if(planRoadDTO.size() != repositoryItemDTO.size()){ // 받아온 DTO와 기존에 있던 DTO의 크기가 다르다면 변동사항이 있다는 것.
                                // 불러온 데이터 리사이클러뷰 삽입
                                repositoryItemDTO.add(new RepositoryItemDTO(planRoadDTO.get(i).getPlanTitle(),
                                        planRoadDTO.get(i).getPlanDate()));
                            }else{ // 만약 크기가 같다면 변동사항이 없다는 것으로 아이템을 추가할 필요 없음.
                                Toast.makeText(getActivity(), "불러올 데이터가 없습니다.", Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                        repositoryCardViewAdapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(), "업데이트가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    }else{
                        Log.d("오류 발생","onResponse 실패 ( 3xx, 4xx 오류)");
                        Toast.makeText(getActivity(), "onResponse 실패, 3xx, 4xx 오류", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<List<PlanRoadDTO>> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(getActivity(), "서버와 통신중 에러가 발생하였습니다.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

//    private void connectingRecyclerViewDTO(){
//        ArrayList<PlanningItemDTO> planningItemDTO = PlanFragment.planItemGetInstance();
//        if(planningItemDTO != null){
//            for(int i = 0; i < planningItemDTO.size(); i++){
//                repositoryItemDTO.add(new RepositoryItemDTO(planningItemDTO.get(i).get))
//            }
//        }else{
//            Log.d("NPE 발생","planningItemDTO is NULL");
//        }
//    }
}