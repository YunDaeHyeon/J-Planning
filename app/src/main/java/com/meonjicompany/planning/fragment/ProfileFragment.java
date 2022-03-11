package com.meonjicompany.planning.fragment;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;
import com.meonjicompany.planning.R;
import com.meonjicompany.planning.activity.IndexPage;
import com.meonjicompany.planning.activity.LoginPage;

import org.w3c.dom.Text;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link com.meonjicompany.planning.fragment.ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener{
    private Button logout;
    private TextView user_nickname, user_email;
    private ImageView user_profile_image;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static com.meonjicompany.planning.fragment.ProfileFragment newInstance(String param1, String param2) {
        com.meonjicompany.planning.fragment.ProfileFragment fragment = new com.meonjicompany.planning.fragment.ProfileFragment();
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
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_my_profile,container,false);
        logout = (Button) rootView.findViewById(R.id.logout);
        user_profile_image = (ImageView) rootView.findViewById(R.id.user_profile_image);
        user_nickname = (TextView) rootView.findViewById(R.id.user_nickname);
        user_email = (TextView) rootView.findViewById(R.id.user_email);

        // 로그 아웃 버튼
        logout.setOnClickListener(this);
//
//        Function2<OAuthToken, Throwable, Unit> callback = new  Function2<OAuthToken, Throwable, Unit>() {
//            @Override
//            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
//                // 이때 토큰이 전달이 되면 로그인이 성공한 것이고 토큰이 전달되지 않았다면 로그인 실패
//                if(oAuthToken != null) {
//                }
//                if (throwable != null) {
//
//                }
//                return null;
//            }
//        };

        userProfileRoad(); // 프로필 로드 메소드 호출
        return rootView;
    }
    private void userProfileRoad(){
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {
                // 로그인이 되어있으면
                if (user!=null){
                    // 카카오에서 프로필 이미지, 닉네임, 이메일 연결
                    // 이미지 로드
                    Glide.with(user_profile_image).load(user.getKakaoAccount().getProfile().
                            getProfileImageUrl()).circleCrop().into(user_profile_image);
                    user_nickname.setText(user.getKakaoAccount().getProfile().getNickname());
                    user_email.setText(user.getKakaoAccount().getEmail());
                }else {
                    // 로그인이 되어 있지 않다면 위와 반대로
                    user_nickname.setText("닉네임 불러오기에 실패했습니다.");
                    user_email.setText("이메일 불러오기에 실패했습니다.");
                }
                return null;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.logout:{
                UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
                    @Override
                    public Unit invoke(Throwable throwable) {
                        Log.d("로그아웃 ","로그아웃 버튼 클릭함");
                        Intent intent = new Intent(getActivity(), LoginPage.class);
                        startActivity(intent);
                        Toast.makeText(getActivity(),"로그아웃 되었습니다.",Toast.LENGTH_SHORT).show();
                        return null;
                    }
                });
                break;
            }
        }
    }
}