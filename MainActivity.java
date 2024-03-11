package com.minseok.hongdroid1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public BottomNavigationView navigationView;

    RecyclerView recyclerView;
    FloatingActionButton add_button;
    ImageView empty_imageview;
    TextView no_data;

    MyDatabaseHelper myDB;
    ArrayList<String> book_id, book_title, book_author, book_pages;
    //네가지 배열 목록을 만듬. 이것들이 아래  storeDataInArrays()메서드로 저장된다. (리스트뷰 ㅇㅇ)
    mainAdapter mainAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    Toolbar toolbar;


    ImageView friendadd;
            ImageView music;
    ImageView setting;
    //사용자 정의 xml레이아웃



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        book_id = new ArrayList<>();  // ArrayList 초기화
        book_title = new ArrayList<>();  // ArrayList 초기화
        book_author = new ArrayList<>();  // ArrayList 초기화
        book_pages = new ArrayList<>();  // ArrayList 초기화



navigationView=findViewById(R.id.bottom_bar);
        navigationView.setItemIconTintList(null);
//이거 넣어야 이미지넣은거 보임.

friendadd=findViewById(R.id.muti);
music=findViewById(R.id.library);
setting=findViewById(R.id.set);
        toolbar = findViewById(R.id.ttol1);
        setSupportActionBar(toolbar);


//여기부터 리싸이클러
        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);
        empty_imageview = findViewById(R.id.empty_imageview);
        no_data = findViewById(R.id.no_data);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            } //기본적인 화면전환 ㅇㅇ 데이터베이스에 데이터 추가할 액티비티로 넘어간다.
        });



        myDB = new MyDatabaseHelper(MainActivity.this);
        storeDataInArrays();







        mainAdapter = new mainAdapter(MainActivity.this,this, book_id, book_title, book_author,
                book_pages);
//어댑터 만들어주고(위에서 데이터베이스에 저장되어 있는값들 다 저장시켜줬으니)
        recyclerView.setAdapter(mainAdapter);
        //연결될때 어댑터에있는 필수 3가지 메서드 자동 호출->arraylist에있는 배열들 다 리스트뷰에 나타나짐.

        if (mainAdapter.getItemCount()>0){
            recyclerView.setVisibility(View.VISIBLE);
        }
        else{
            recyclerView.setVisibility(View.GONE);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

//각각의 버튼 누르면 해당  액티비티로 전환.(인탠트로)


//해당 객체 누를시 인텐트로 다 화면 전환해줌.

        friendadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this  , Friendadd.class);
                startActivity(intent);

            }
        });

        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Music.class );
                startActivity(intent);

            }
        });


        //세팅 누를시 menu.xml인플레이트로 아이템 목록 보여주게 함.
                setting .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        PopupMenu popup=new PopupMenu(MainActivity.this,setting);
                        MenuInflater inf=popup.getMenuInflater();

                        inf.inflate(R.menu.settingmenu,popup.getMenu());

                        //프래그먼트의 인플레이트랑 차이가 있다.
                        //공통점은 모두 xml파일을 메모리에 로드해 사용가능한 객체로 만들고,
                        //즉 settingmenu팡리엘 정의된 메뉴를 인플레이트해 팝업 메뉴에 추가해준다.
                      //PopupMenu 객체의 메뉴로 추가합니다
                        // 그것이다. 그 결과로 팝업 메뉴 객체(popup)가 settingmenu.xml에 정의된 메뉴 아이템들을 포함하게 됩니다
                        //여긴 좀 더 알아보자  나도 처음보는 것이다. 메뉴를 만들고 메뉴바를 인플레이트 시켜 클릭시 바로 띄우게 한다는게
                        //xml레이아웃을 해당 클래스에 넣고, 그걸 보여준다. 애초에  xml레이아웃을 메모링 객체로 생성해 넣어준
                        //클래스에서 사용이 가능한거잖아 이것도 그런것같다 popupMenu클래스에서 다 사용가능하게 해준것이다.
                        //근데 그게 왜 바로 나타나게 된건지 모르게ㅐㅆ다.
                        popup.show();


                    }
                });





//         !!중요하다 아래 하단 탭을 BottomNavigationView로 xml로 구성했는데 해당 xml은 menu와 이어져있다.
        //원래 다른 xml끼리는 연결이 안되는게 정상이다. 일일히 이벤트 처리를 해줘야 하는데, BottomNavigationView은 연결되어 있다.
        //하단 아이템을 클릭할지 시스템 내부에서 정해진 정수값을 가져온다.
        //안드로이드 앱에서 리소스 ID는 정수형(int)이다! 그렇기에 가능한것이다.
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.tab1) {
                    // "친구" 화면으로 이동
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.tab2) {
                    // "채팅" 화면으로 이동
                    Intent  in= new Intent(MainActivity.this, Chatting.class);
                    startActivity(new Intent(MainActivity.this, Chatting.class));

                    return true;
                } else if (item.getItemId() == R.id.tab3) {
                    // "캘린더" 화면으로 이동
                    startActivity(new Intent(MainActivity.this, calrender.class));
                    return true;
                } else if (item.getItemId() == R.id.tab4) {
                    // "더보기" 화면으로 이동
                    startActivity(new Intent(MainActivity.this, Setting.class));
                    return true;

                }



                return false;
            }
        });


    }





    /*
    * 다른 액티비티에서 데이터를 변경한 후에 변경된 데이터를 반영하기 위해 현재 액티비티를 갱신하는 것입니다.
    * 예를 들어, 다른 액티비티에서 데이터를 추가하거나 수정한 후에 해당 변경 내용을 즉시 반영하여 화면을 업데이트 하는것이다.
    * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate(); //현재 액티비티 종료하고 다시 생성한다. 즉 새로고침 하지 않아도 새로 만들어준다.
        }
    }

    /*
    * !!!!!!아래 메서드로  데이터베이스에 있는 값들을 Arraylist배열에 저장시킨다
    * 해당 부분이 데이터베이스에서 꺼내오는것이다.
    * !!

    * */
    void storeDataInArrays(){
        Cursor cursor = myDB.readAllData(); //데이터베이스의 모든 데이터를 읽어와서 저장하고
        if(cursor.getCount() == 0){  //레코드(행) 수 확인해서 0이면 보이게
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View
                    .VISIBLE);
        }else{
            //cursor.moveToNext()를 호출하여 다음 레코드(행)로 이동합니다. blooean값 반환한다.
            while (cursor.moveToNext()){
                book_id.add(cursor.getString(0));
                book_title.add(cursor.getString(1));
                book_author.add(cursor.getString(2));
                book_pages.add(cursor.getString(3));
            }
            /*
             add메서드로 첫째열부터 넷째 열까지 가져와 첫 번째 열을 의미하고 가져온걸 book_id배열 리스트에 추가된다.
            즉 반복문을 돌면서 각 행에 대한 열값을 (0행0열->1열->2열->3열) 이렇게 가져오는것이다.
            그렇게 돌면서 배열에 각각 저장된다.
            */
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
        }
    }
//해당메서드는 액티비티가 처음 생성될 때 호출된다. 인플레이터로 xml로 정의된 메뉴 리소스를 인플레이트 하고 액티비티의 메뉴로 추가된다.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    //super.onCreateOptionsMenu(menu)를 호출하여 부모 클래스의 메소드를 실행합니다.

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.delete_all){
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    } //옵션 메뉴 선택시 자동으로 호출되어 실행됨 menu폴더에 있는것임..
    //menu

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All?");
        builder.setMessage("Are you sure you want to delete all Data?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                myDB.deleteAllData();
                //Refresh Activity
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
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





