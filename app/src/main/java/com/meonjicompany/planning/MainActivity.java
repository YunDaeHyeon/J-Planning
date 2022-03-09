package com.meonjicompany.planning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.navigation.NavigationBarView;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

public class MainActivity extends AppCompatActivity {
    MyProfile myProfile;
    Planning planning;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myProfile = new MyProfile();
        planning = new Planning();
        repository = new Repository();

        // 프래그먼트를 액티비티에 올리기 ( 초기화면 설정 - 계획(planning) )
        getSupportFragmentManager().beginTransaction().replace(R.id.containers,planning).commit();
        NavigationBarView navigationBarView = findViewById(R.id.bottom_navigationview);
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.ic_repository: // 보관소 프래그먼트
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers,repository).commit();
                        return true;
                    case R.id.ic_planning: // 계획 프래그먼트
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers,planning).commit();
                        return true;
                    case R.id.ic_profile: // 프로필 프래그먼트
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers,myProfile).commit();
                        return true;
                }
                return false;
            }
        });
        // 카카오가 설치되어 있는지 확인 하는 메서드또한 카카오에서 제공 콜백 객체를 이용함
        Function2<OAuthToken, Throwable, Unit> callback = new  Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                // 이때 토큰이 전달이 되면 로그인이 성공한 것이고 토큰이 전달되지 않았다면 로그인 실패
                if(oAuthToken != null) {
                }
                if (throwable != null) {

                }
                return null;
            }
        };
//        logoutButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
//                    @Override
//                    public Unit invoke(Throwable throwable) { // 로그아웃시 발생하는 이벤트
//                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                        startActivity(intent);
//                        return null;
//                    }
//                });
//            }
//        });
    }
}