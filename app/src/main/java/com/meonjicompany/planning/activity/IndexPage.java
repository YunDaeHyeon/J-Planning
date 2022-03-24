package com.meonjicompany.planning.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;
import com.kakao.sdk.auth.model.OAuthToken;
import com.meonjicompany.planning.R;
import com.meonjicompany.planning.fragment.PlanFragment;
import com.meonjicompany.planning.fragment.ProfileFragment;
import com.meonjicompany.planning.fragment.RepositoryFragment;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class IndexPage extends AppCompatActivity {
    PlanFragment planFragment; // 계획 프래그먼트 객체
    RepositoryFragment repositoryFragment; // 보관소 프래그먼트 객체
    ProfileFragment profileFragment; // 프로필 프래그먼트 객체

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
        // 카카오가 설치되어 있는지 확인 하는 메서드또한 카카오에서 제공 콜백 객체를 이용함
        Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                // 이때 토큰이 전달이 되면 로그인이 성공한 것이고 토큰이 전달되지 않았다면 로그인 실패
                if (oAuthToken != null) {
                }
                if (throwable != null) {

                }
                return null;
            }
        };
    }
}