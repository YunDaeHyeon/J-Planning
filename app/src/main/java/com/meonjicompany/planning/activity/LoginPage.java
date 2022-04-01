package com.meonjicompany.planning.activity;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;
import com.meonjicompany.planning.R;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class LoginPage extends AppCompatActivity {
    private long backKeyPressedTime = 0; // 뒤로가기 버튼을 눌렀던 시간
    private static final String TAG = "LoginActivity";
    private View loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = findViewById(R.id.login);

        // 카카오가 설치되어 있는지 확인 하는 메서드또한 카카오에서 제공 콜백 객체를 이용함
        Function2<OAuthToken, Throwable, Unit> callback = new  Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                // 이때 토큰이 전달이 되면 로그인이 성공한 것이고 토큰이 전달되지 않았다면 로그인 실패
                // 로그인 버튼을 클릭할 때 로그인 성공 시
                if(oAuthToken != null) {
                    Intent intent = new Intent(LoginPage.this, IndexPage.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),"로그인 되었습니다.",Toast.LENGTH_SHORT).show();
                }
                // 로그인 버튼을 클릭할 때 로그인 실패 시
                if (throwable != null) {
                    Toast.makeText(getApplicationContext(),"로그인 실패",Toast.LENGTH_SHORT).show();
                }
                return null;
            }
        };

        updateKakaoLoginUi();

        // 로그인 버튼
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 만약, 카카오톡이 설치되어 있으면 카카오톡 어플 연동해서 로그인
                // 만약, 카카오톡이 설치되어 있지 않으면 웹에서 로그인 진행
                // 로그인이 되거나 오류가 발생하면 Callback 함수 호출
                if(UserApiClient.getInstance().isKakaoTalkLoginAvailable(LoginPage.this)) {
                    UserApiClient.getInstance().loginWithKakaoTalk(LoginPage.this, callback);
                }else {
                    UserApiClient.getInstance().loginWithKakaoAccount(LoginPage.this, callback);
                }
            }
        });
    }
    private  void updateKakaoLoginUi(){ // 로그인, 로그아웃 레이아웃 함수. userApiCient로 로그인 여부 파악
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {
                // 로그인이 되어있으면 ( 세션이 존재한다면 )
                if (user!=null){ // 로그인이 되어 있는 상태의 레이아웃
                    Intent intent = new Intent(LoginPage.this, IndexPage.class);
                    startActivity(intent);
                    // 토스트메세지 전송
                    Toast.makeText(getApplicationContext(),"자동 로그인 되었습니다.",Toast.LENGTH_SHORT).show();
                }else {
                    // 로그인이 되어 있지 않다면
                }
                return null;
            }
        });
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