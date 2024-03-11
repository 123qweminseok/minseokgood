package com.minseok.hongdroid1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class Friendadd extends AppCompatActivity {



    Animation anitopdown;
    ImageView imageView;
ImageView button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendadd);

        anitopdown= AnimationUtils.loadAnimation(this,R.anim.topanddown);

        overridePendingTransition(R.anim.topanddown,R.anim.none);
//액티비티 틀어질때 애니메이션 효과 적용됨

        imageView=findViewById(R.id.imageView1);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Friendadd.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.real,R.anim.none);
                //왜 아래에서 위로 안가는지 모르겠따..
            }
        });

        button2=findViewById(R.id.imageView3);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Friendadd.this, AddActivity.class);
            startActivity(intent);
            }
        });

    }
//뒤로가기 눌렀을때
    @Override
    public void onBackPressed() {
        super.onBackPressed();


        overridePendingTransition(R.anim.none,R.anim.downup);

    }



}