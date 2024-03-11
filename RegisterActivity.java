package com.minseok.hongdroid1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseauth;//Firebase 인증 객체

    private DatabaseReference mDatabaseRef;//실시간 데이터베이스 참소 객체
    private EditText mEtEmail,mEtpwd;
    private Button mBtnRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Firebase 인증 객체 초기화
        mFirebaseauth=FirebaseAuth.getInstance();

        // Firebase 데이터베이스 참조 객체 초기화
        mDatabaseRef= FirebaseDatabase.getInstance().getReference();




        mEtEmail=findViewById(R.id.et_email);
        mEtpwd=findViewById(R.id.et_pwd);
        mBtnRegister=findViewById(R.id.btn_register);
    mBtnRegister.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //회원가입 처리 시작.
            // 사용자가 입력한 이메일과 비밀번호 가져오기
            String strEmail = mEtEmail.getText().toString(); //입력한 텍스트를 불러와 문자열로 변환하고
            String strPwd = mEtpwd.getText().toString();



            // Firebase Auth를 사용하여 사용자를 생성하고 회원가입 진행
            mFirebaseauth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {

                        // 회원가입 성공 시
                        FirebaseUser firebaseUser = mFirebaseauth.getCurrentUser();

                        // 사용자 계정 정보를 생성하여 Firebase 데이터베이스에 저장
                        Useraccount account = new Useraccount();
                        account.setIdToken(firebaseUser.getUid());
                        account.setEmailId(firebaseUser.getEmail());
                        account.setPassword(strPwd);

                        // Firebase 데이터베이스에 사용자 계정 정보 저장
                        mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);
                        // 회원가입 성공 메시지 표시
                        Toast.makeText(RegisterActivity.this, "회원가입 성공", Toast.LENGTH_LONG).show();
                    }else{
                        // 회원가입 실패 시 실패 메시지 표시
                        Toast.makeText(RegisterActivity.this, "실패", Toast.LENGTH_LONG).show();


                    }

                    }

            });
        }
    });

    }

}
//사용자가 회원가입을 처리하는 데 사용됩니다.
//onCreate() 메서드에서 Firebase Auth 및 Firebase Realtime Database 인스턴스를 초기화합니다.
//사용자가 회원가입 버튼을 클릭하면, 사용자가 입력한 이메일과 비밀번호를 사용하여 Firebase Auth에 사용자를 등록합니다.
//회원가입이 성공하면, 현재 사용자의 Firebase UID를 가져와서 Useraccount 객체를 만들고, 이를 Firebase Realtime Database에 저장합니다.