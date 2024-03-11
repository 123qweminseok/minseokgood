package com.minseok.hongdroid1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    EditText title_input, author_input, pages_input; //텍스트 입력하는것들.
    Button update_button, delete_button; //업데이트 및 삭제 클릭

    String id, title, author, pages;
//여기서 id및 다른 값들 화면 이동할때 데이터 받음.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        title_input = findViewById(R.id.title_input2);
        author_input = findViewById(R.id.author_input2);
        pages_input = findViewById(R.id.pages_input2);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);

// 아래 메서드 호출
      getAndSetIntentData();

//getSupportActionBar() 메서드는 현재 액티비티에서 사용 가능한 ActionBar 객체를 반환하는것이다.
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(title);
        }//이건 액션바를 쓰면 타이틀 제목과 같이 액션바 제목을 세팅해준다. 근데 나는 액션바 세팅을 하지 않아서 이건 안뜰것이다.


        //업데이트 버튼 클릭 리스너
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // EditText에서 입력된 정보 가져오기
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                //생성자로 현재 액티비티 정보 넘겨줌.(데이터베이스)

                title = title_input.getText().toString().trim();
                //EditText에 적어 놓은 값을 저장해줌 텍스트를 가져`옴.
                author = author_input.getText().toString().trim();
                pages = pages_input.getText().toString().trim();
                myDB.updateData(id, title, author, pages);


                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

                /*
                //여기에 id가 없는 이유는 적는 텍스트칸이 없어서 필요없다. 입력은 id를 해주지 않기 때문이다.
                * 여기가 이제 업데이트 되는거다 mainAd7uapter클래스와 연결된다! 해당걸로 데이터베이스에 업데이트
                * */
                //해당 메서드로 데이터에 값들을 추가시켜준다. MyDatabaseHelper에 있는 메서드이다.
                //즉 업데이트 버튼 눌러주면 클릭 리스너로 인해서 입력되어있는 값들을 updateData메서드로
                //데이터를 넘겨준다는 것이다.-> 데이터베이스에 해당 데이터 업데이트 시켜주는것이다!

                 }
        });
        //삭제 버튼 누를시
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });

    }


/*  즉 아래 메서드는 기존에 있는 데이터를 가져와서 세팅해주는것이다!. 클릭해서 들어가면 edittext에 이름 그대로 뜨지 않는가?
그걸 시켜준다. 액티비티 시작할때 데이터 있는지 확인해서 있으면 해당 데이터 가져와서 추가해준다. 즉 기존 데이터 화면에 띄워주는거다!
 */

//    Q.클릭하고 화면 넘어가는건 대체 어디에서 시작해준건가? 이 클래스에 있진 않을텐데!!!!!!!!!!!!->메인 어댑터
    void getAndSetIntentData(){

        if(getIntent().hasExtra("id") && getIntent().hasExtra("title") &&
                getIntent().hasExtra("author") && getIntent().hasExtra("pages")){
                //getIntent().hasExtra는 현재 액티비티를 시작할 때 인텐트(Intent)에 "id"라는 이름의 데이터가 있는지를 확인하는 메서드입니다.
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            author = getIntent().getStringExtra("author");
            pages = getIntent().getStringExtra("pages");
//Intent로부터 각각의 데이터를 가져와서 변수에 저장합니다.-> 즉 mainAdapter에서 putExtra가 여기서 실행되는것이다.
            //id값에 맞는 데이터를

            title_input.setText(title);
            author_input.setText(author);
            pages_input.setText(pages);
            //각각의 EditText 위젯에 도서의 제목(title), 작가(author), 페이지 수(pages)를 설정합니다.

            Log.d("stev", title+" "+author+" "+pages);
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();//인텐트에 데이터가 없을시 ㅇㅇ보여줌.
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + title + " ?");
        builder.setMessage("Are you sure you want to delete " + title + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.deleteOneRow(id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}