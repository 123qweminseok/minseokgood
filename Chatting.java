package com.minseok.hongdroid1;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Chatting extends AppCompatActivity {

    String fileName;

    RecyclerView recyclerView;
    FloatingActionButton add_button;
    ImageView empty_imageview;
    TextView no_data;

    MyDatabaseHelper myDB;
    ArrayList<String> book_title ;
    ChattingAdatpter chattingAdatpter;
    Button btnSave;
    EditText editTextText3;

     String item;



    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.chatting);
            recyclerView = findViewById(R.id.recyclerView);
            myDB = new MyDatabaseHelper(Chatting.this);
            //생성자로 자기자신 클래스 넣어주면서 전달.
            btnSave = findViewById(R.id.btnSave);

            editTextText3 = findViewById(R.id.editTextText3);
    /*
    MakeFriend 클래스의 인스턴스가 SQLiteOpenHelper 생성자에 전달되면,
     데이터베이스 파일은 MakeFriend 클래스가 속한 애플리케이션의 디렉토리 내에 생성됩니다.
    이것은 데이터베이스 파일의 위치나 접근 권한 등과 관련이 있다. 이 클래스가(객체참조변수가) 핵심이라는 것이다. SQLiteOpenHelper의 생성자에
    해당 클래스를 전달했으니 해당 객체참조변수에 데이터베이스 자체가 저장되어 있다.

      */
            book_title = new ArrayList<>();
    //배열 목록도 초기화 해주고


            storeDataInArrays(); // 아래에서 데이터베이스 모두 읽어오고, 배열에다가 저장까지 해줌.
         /*   customAdapter = new ChattingAdatpter(Chatting.this,this, book_id, book_title, book_author,
                    book_pages);*/
            chattingAdatpter = new ChattingAdatpter(Chatting.this, this, book_title);
    //어댑터 호출하고ㄷ
            recyclerView.setAdapter(chattingAdatpter);
            //어댑터 실행하고.
            recyclerView.setLayoutManager(new LinearLayoutManager(Chatting.this));
    //채팅어댑터에있는



        //아래 부터 일기장 시작. 해당 일기장 써주기 위해서 어댑터 클래스에서 인터페이스를 정의해 줬다. AppCompatActivity를 상속받아야 쓸 수 있기 때문에

        chattingAdatpter.setOnItemClickListener(new ChattingAdatpter.OnItemClickListener() {

            //여기서 클릭리스너 정의(채팅 어댑터에 있는것) 및 인터페이스 구현.(아래 onItemClick은 Chatting어댑터에
                //매개변수에서 값이 들어오는것이다)

                //인터페이스를 만들어 클릭 이벤트를 정의해 준 것이다. 여기선 자동으로 실행된다고 보면 된다
                @Override
                public void onItemClick(View view, int position) {


                    //mListener.onItemClick(v, pos); 채팅어댑터 클래스에서 위로 ㄷㅍㄷ넘어온다고 생각하면 된다.!!! 생각하자
                    // 클릭 이벤트 처리
                    String itemTitle = book_title.get(position);

                 //   this.item=itemTitle;-->오류뜬다 OnItemClickListener 인터페이스의 인스턴스를 가리키는것이다.
                    item=itemTitle;

                    //해당 포지션 값 자동으로 들어가고 받아준다음에 화면에 띄워준다.
                    //저장되면위로 바뀌어짐
                    checkedDay(itemTitle);//클릭시 위치 받아옴.
                    //자동적으로 위치 받아옴
                    btnSave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            saveDiary(itemTitle);
                            Log.v("444","444");

                        }
                    });


                    // 세이트 버튼 누를시 저장

                }


            });


    }





    //-----------------------------아래부터 메서드.

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            recreate();
        }
    }

    //Cursor클래스는 주로 데이터베이스에서 데이터를 검색하고 결과를 처리하는 데사용된다.
    // 이 클래스를 사용하면 데이터베이스에서 검색된 결과 집합을 탐색하고 각 레코드의 열 값을 읽을 수 있습니다.



    //배열에 데이터 저장.
    void storeDataInArrays() {
        Cursor cursor = myDB.readAllData(); //데이터베이스의 모든 데이터를 읽어와서 저장하고
        //!!!!!!!!이것으로 데이터베이스 읽어와서 해당 위치에 저장시켜준다)
        //cursor.moveToNext()를 호출하여 다음 레코드(행)로 이동합니다. blooean값 반환한다.
        while (cursor.moveToNext()) {
            //      book_id.add(cursor.getString(0));
            book_title.add(cursor.getString(1));
//배열에 추가해주고
        }
    }


    //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ파일만들고, 저장하는 메서드ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
    //클릭시 파일 저장 위의 itemtitle 에서 받아서 감.
    private void saveDiary(String itemTitle) {
        FileOutputStream fos = null;
        try {
            String content = editTextText3.getText().toString();
            // 파일 이름 생성
            String fileName = itemTitle + ".txt";

            // 파일이 이미 존재하는지 여부 확인
            boolean fileExists = getFileStreamPath(fileName).exists();

            // 파일에 내용 저장
            fos = openFileOutput(fileName, Context.MODE_PRIVATE);
            //1.파일을 쓰기 모드로 연다. ,--> 현재 앱에서만 사용할 수 있게 해준다.
            //파일이 없어도 만들어준다.!
            fos.write(content.getBytes());
            //2.getBytes 문자열을 byte로 변환해준다.
            fos.close();
//3. 닫아줌. 파일 메모리 누수방지 .

            //1. 쓰기 모드로 염(파일 없으면 만들어줌 자동으로)-> 2. 텍스트에 적은 값을 변환해준다 문자열을 ->바이트로 변환해서 써준다.

            // 존재하는 파일이었을 경우 수정 버튼으로 변경
            if (fileExists) {
                btnSave.setText("수정하기");
            } else {
                btnSave.setText("새 일기 저장");
            }

            // 저장된 메시지 표시
            Toast.makeText(getApplicationContext(), "일기 " + (fileExists ? "수정" : "저장") + "됨", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "오류 발생", Toast.LENGTH_SHORT).show();
        }
    }

    //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ파일 여는 메서드
    private void checkedDay(String itemTitle) {
        try {
            String fileName = itemTitle + ".txt";
            FileInputStream fis = openFileInput(fileName);
            //내부 저장소 파일을 연다-> 열기만 함. 파일 없으면 아래 catch문으로 돌아간다.
            byte[] fileData = new byte[fis.available()];
            //읽어올 수 있는 데이터의 크기를 반환
            //1.파일염.->파일 내의 크기를 받아옴->받아온 크기만큼 바이트를 읽음.->string형태로 바꿈->settext로 나타냄ㄷㅍㄸ교

            fis.read(fileData);
            //파일에서 데이터를 읽어온다.
            fis.close();
//파일을 닫는다.

            String str = new String(fileData, "UTF-8");
            //
            editTextText3.setText(str);
//화면에 띄워줌. str에 이제 파일데이터를 문자열 형태로 바꿔줬으니 ㅇㅇ
            btnSave.setText("수정하기");

            Toast.makeText(getApplicationContext(), "일기 불러옴", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            editTextText3.setText("");
            btnSave.setText("새 일기 저장");
            Toast.makeText(getApplicationContext(), "일기 없는 날", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "오류 발생", Toast.LENGTH_SHORT).show();
        }


}}


//        1. 여기서 친구추가 버튼 클릭시 텍스트(이름,)이미지(추가)
//        2.데이터베이스에 저장되서 메인 액티비티에 도출되어야함.->메인 액티비티에 뜨게 하려면 어케하는지는 잘 모르겠음.. 그러니 일단 추가시 추가한 친구 목록 리스트만 뜨게 할 예정임.


