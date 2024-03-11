package com.minseok.hongdroid1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LOGINACTIVITY extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;//파이어베이스 인증
    private DatabaseReference mDatabaseRef;//실시간 데이터베이스
    private EditText mEtEmail, mEtpwd;
    private Button mBtnRegister, mBtnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);


        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        mEtEmail = findViewById(R.id.et_email);
        mEtpwd = findViewById(R.id.et_pwd);
        mBtnRegister = findViewById(R.id.btn_register);
        mBtnLogin = findViewById(R.id.btn_login);
        // 로그인 버튼 클릭 시 동작 설정

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 사용자가 입력한 이메일과 비밀번호 가져오기

                String strEmail = mEtEmail.getText().toString(); //텍스트를 불러와 문자열로 변환하고
                String strPwd = mEtpwd.getText().toString();

                // FirebaseAuth를 사용하여 사용자를 인증하고 로그인 진행
                mFirebaseAuth.signInWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(LOGINACTIVITY.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Intent intent = new Intent(LOGINACTIVITY.this, MainActivity.class);
                            startActivity(intent);
                            finish(); //화면 전환 해주는거임.
                        } else {
                            Toast.makeText(LOGINACTIVITY.this, "로그인 실패", Toast.LENGTH_LONG).show();
                            // 로그인 실패 시 메시지 표시

                        }


                    }
                });
            }
        });

        // 회원가입 버튼 클릭 시 동작 설정

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            // 회원가입 액티비티로 이동

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LOGINACTIVITY.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


//사용자가 로그인을 처리하는 데 사용됩니다.
//onCreate() 메서드에서 Firebase Auth 인스턴스를 초기화합니다.
//사용자가 로그인 버튼을 클릭하면, 입력한 이메일과 비밀번호를 사용하여 Firebase Auth를 통해 사용자를 인증하고 로그인합니다.
//로그인이 성공하면, 메인 액티비티로 이동합니다.

    }
}