package com.minseok.hongdroid1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {


//   ※해당 클래스는 추가하기 버튼을 눌렀을때 해당 액티비티로 넘어오고, 이제 거기에서 버튼을 클릭하면 MyDatabaseHelper클래스에서
    //메서드를 가려와 호출한다.
    EditText title_input, author_input, pages_input;
    Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
//


        title_input = findViewById(R.id.title_input);
        author_input = findViewById(R.id.author_input);
        pages_input = findViewById(R.id.pages_input);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            // MyDatabaseHelper 클래스의 인스턴스 생성

            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);
                //(내가 전에 만든 클래스임)
                // 입력된 도서 정보를 가져와 데이터베이스에 추가
/*여기 핵심임!!!! 데이터베이스 안에 넣는것!!!! */
                try {
                    myDB.addBook(title_input.getText().toString().trim(),// 제목 입력란의 텍스트 가져오기
                            //trim() 메서드는 텍스트 앞뒤의 공백을 제거합니다
                            author_input.getText().toString().trim(),//// 저자 입력란의 텍스트 가져오기
                            Integer.parseInt(pages_input.getText().toString().trim()));
                    //페이지 수 입력란의 텍스트를 정수로 변환
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);

                }
                catch(Exception e)  {
                    Toast.makeText(getApplicationContext(), "나머지 빈 칸도 나 채워주세요", Toast.LENGTH_SHORT).show();//인텐트에 데이터가 없을시 ㅇㅇ보여줌.
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                }


            //※즉 해당 클래스에서 MyDatabaseHelper 클래스의 메서드 addBook을 이용해 내가 입력한 데이터를 넣어 데이터베이스에 데이터를 추가하는것이다.
            //데이터를 보려면 sql브라우저 프로그램 다운후  해당 프로그램에 뜬다. 프로그램 다운받아야 한다
            // 그리고 보려면 무조건 Android 애뮬레이터에서 데이터베이스를 내보내야 한다. 자동으로 내보내지는게 아니다.
            // 그렇게 하렴녀 장치 파일에서 장치 선택후 한다. 링크는 올려두겠다
            //내 에뮬레이터 파일에 있는 데이터베이스 디렉터리를 바탕화면에 내보내는것이다.
            // 그러고 데이터베이스 sql브라우저 프로그램을 내보낸 파일들을 추가하는것이다.


//https://www.youtube.com/watch?v=RGzblJuat1M&list=PLSrm9z4zp4mGK0g_0_jxYGgg3os9tqRUQ&index=2

        });

    }
}