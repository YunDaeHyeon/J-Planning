package com.meonjicompany.planning.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationBarView;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;
import com.meonjicompany.planning.R;
import com.meonjicompany.planning.fragment.PlanFragment;
import com.meonjicompany.planning.fragment.ProfileFragment;
import com.meonjicompany.planning.fragment.RepositoryFragment;
import com.meonjicompany.planning.retrofit.Message;
import com.meonjicompany.planning.retrofit.RetrofitAPI;
import com.meonjicompany.planning.retrofit.RetrofitClient;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IndexPage extends AppCompatActivity {
    private long backKeyPressedTime = 0; // 뒤로가기 버튼을 눌렀던 시간
    public static int userId = 0; // 유저 식별자
    PlanFragment planFragment; // 계획 프래그먼트 객체
    RepositoryFragment repositoryFragment; // 보관소 프래그먼트 객체
    ProfileFragment profileFragment; // 프로필 프래그먼트 객체
    private RetrofitAPI retrofitAPI; // 통신을 위한 Retrofit 객체
    String userEmail = "";
    String userNickname = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        planFragment = new PlanFragment();
        // 프래그먼트를 액티비티에 올리기 ( 초기화면 설정 - 계획(planFragment) )
        getSupportFragmentManager().beginTransaction().replace(R.id.containers, planFragment).commit();
        NavigationBarView navigationBarView = findViewById(R.id.bottom_navigationview);
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_repository: // 보관소 프래그먼트
                        if (repositoryFragment == null) {
                            repositoryFragment = new RepositoryFragment();
                            getSupportFragmentManager().beginTransaction().add(R.id.containers, repositoryFragment).commit();
                        }

                        if (repositoryFragment != null)
                            getSupportFragmentManager().beginTransaction().show(repositoryFragment).commit();
                        if (planFragment != null)
                            getSupportFragmentManager().beginTransaction().hide(planFragment).commit();
                        if (profileFragment != null)
                            getSupportFragmentManager().beginTransaction().hide(profileFragment).commit();
                        return true;

                    case R.id.ic_planning: // 계획 프래그먼트
                        if (planFragment == null) {
                            planFragment = new PlanFragment();
                            getSupportFragmentManager().beginTransaction().add(R.id.containers, planFragment).commit();
                        }

                        if (repositoryFragment != null)
                            getSupportFragmentManager().beginTransaction().hide(repositoryFragment).commit();
                        if (planFragment != null)
                            getSupportFragmentManager().beginTransaction().show(planFragment).commit();
                        if (profileFragment != null)
                            getSupportFragmentManager().beginTransaction().hide(profileFragment).commit();
                        return true;
                    case R.id.ic_profile: // 프로필 프래그먼트
                        if (profileFragment == null) {
                            profileFragment = new ProfileFragment();
                            getSupportFragmentManager().beginTransaction().add(R.id.containers, profileFragment).commit();
                        }

                        if (repositoryFragment != null)
                            getSupportFragmentManager().beginTransaction().hide(repositoryFragment).commit();
                        if (planFragment != null)
                            getSupportFragmentManager().beginTransaction().hide(planFragment).commit();
                        if (profileFragment != null)
                            getSupportFragmentManager().beginTransaction().show(profileFragment).commit();
                        return true;
                }
                return false;
            }
        });
        firstApplicationRoadCheck(); // 사용자가 실행한 시점이 최초인지 확인하는 메소드
    }

    public void firstApplicationRoadCheck(){
        //최초 실행 여부 판단하는 구문
        SharedPreferences sharedPreferences = getSharedPreferences("CHECK", Activity.MODE_PRIVATE);
        boolean check = sharedPreferences.getBoolean("CHECK", false);
        if(check==false){
            Log.d("앱 실행 판단", "최초로 실행");
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("CHECK",true);
            editor.commit();
            //앱 최초 실행시 하고 싶은 작업
            UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
                @Override
                public Unit invoke(User user, Throwable throwable) {
                    // 로그인이 되어있으면
                    if (user!=null){
                        RetrofitClient retrofitClient = RetrofitClient.getInstance();
                        if(retrofitClient != null){
                            retrofitAPI = RetrofitClient.getRetrofitAPI();
                            retrofitAPI.sendUser(user.getKakaoAccount().getEmail(),user.getKakaoAccount().getProfile().getNickname()).enqueue(new Callback<Message>() {
                                @Override
                                public void onResponse(Call<Message> call, Response<Message> response) {
                                    if(response.isSuccessful()){
                                        final Message message = response.body(); // message.getMessage() - 유저 식별자를 끌고옴.
                                        userId = Integer.parseInt(message.getMessage());
                                        Log.d("userID : ", String.valueOf(userId));
                                        Toast.makeText(getApplicationContext(), "서버에 값을 전달하였습니다."+message.getMessage(), Toast.LENGTH_SHORT).show();
                                    }else{
                                        Log.d("오류 발생","onResponse 실패 ( 3xx, 4xx 오류)");
                                        Toast.makeText(getApplicationContext(), "onResponse 실패, 3xx, 4xx 오류", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                @Override
                                public void onFailure(Call<Message> call, Throwable t) {
                                    t.printStackTrace();
                                    Toast.makeText(getApplicationContext(), "서버와 통신중 에러가 발생하였습니다.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        Log.d("userEmail = ",userEmail);
                        Log.d("userNickname = ",userNickname);
                    }else {
                        Log.d("Error : ","userProfileRoad Error");
                    }
                    return null;
                }
            });
        }else{
            UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
                @Override
                public Unit invoke(User user, Throwable throwable) {
                    // 로그인이 되어있으면
                    if (user!=null){
                        RetrofitClient retrofitClient = RetrofitClient.getInstance();
                        if(retrofitClient != null){
                            retrofitAPI = RetrofitClient.getRetrofitAPI();
                            retrofitAPI.roadUserId(user.getKakaoAccount().getEmail()).enqueue(new Callback<Message>() {
                                @Override
                                public void onResponse(Call<Message> call, Response<Message> response) {
                                    if(response.isSuccessful()){
                                        final Message message = response.body(); // message.getMessage() - 유저 식별자를 끌고옴.
                                        userId = Integer.parseInt(message.getMessage());
                                        Log.d("userID : ", String.valueOf(userId));
                                        Toast.makeText(getApplicationContext(), "유저 식별값 : "+userId, Toast.LENGTH_SHORT).show();
                                    }else{
                                        Log.d("오류 발생","onResponse 실패 ( 3xx, 4xx 오류)");
                                        Toast.makeText(getApplicationContext(), "onResponse 실패, 3xx, 4xx 오류", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                @Override
                                public void onFailure(Call<Message> call, Throwable t) {
                                    t.printStackTrace();
                                    Toast.makeText(getApplicationContext(), "서버와 통신중 에러가 발생하였습니다.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }else {
                        Log.d("Error : ","userIdRoad Error");
                    }
                    return null;
                }
            });
            Log.d("앱 실행 판단", "최초로 실행된 것이 아님.");
        }
    }

    // 뒤로가기 버튼
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        // 기존 뒤로 가기 버튼의 기능을 막기 위해 주석 처리 또는 삭제

        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간에 2.5초를 더해 현재 시간과 비교 후
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간이 2.5초가 지났으면 Toast 출력
        // 2500 milliseconds = 2.5 seconds
        if (System.currentTimeMillis() > backKeyPressedTime + 2500) {
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(getApplicationContext(),"뒤로가기 버튼을 한 번 더 누르시면 종료됩니다.",Toast.LENGTH_SHORT).show();
            return;
        }
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간에 2.5초를 더해 현재 시간과 비교 후
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간이 2.5초가 지나지 않았으면 종료
        if (System.currentTimeMillis() <= backKeyPressedTime + 2500) {
            finish();
        }
    }
}