package com.minseok.hongdroid1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;

public class calrender extends AppCompatActivity {


    DatePicker datePicker;  // 날짜 선택을 위한 DatePicker
    TextView viewDatePick;  // 선택한 날짜를 보여주는 TextView
    EditText edtDiary;      // 일기 내용을 입력하는 EditText
    Button btnSave;         // 일기를 저장하는 Button
    String fileName;        // 저장할 일기 파일명을 저장하는 변수 . 처음엔null 값이 있다.

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calrender);
        setTitle("일기장");

        // 위젯 초기화
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        viewDatePick = (TextView) findViewById(R.id.viewDatePick);
        edtDiary = (EditText) findViewById(R.id.edtDiary);
        btnSave = (Button) findViewById(R.id.btnSave);

        // 오늘 날짜를 받게해주는 Calender(getInstance 메서드 사용시 현재 날짜들 다 리턴)
        Calendar c = Calendar.getInstance();
        //그아래로 각각 뽑아줌
        int Year = c.get(Calendar.YEAR);
        int Month = c.get(Calendar.MONTH);
        int Day = c.get(Calendar.DAY_OF_MONTH);

        // 첫 시작 시에는 오늘 날짜 일기 읽어주기 아래 메서드 실행시 날짜 아래 텍스트창에 settext메서드로 보여짐.
        checkedDay(Year, Month, Day);

        // datePick 기능 만들기
        //DatePicker.OnDateChangedListener는 DatePicker 위젯에서 날짜가 변경될 때마다 호출되는 콜백을 정의하는 인터페이스입니다.
        // 이 인터페이스는 onDateChanged() 메서드를 구현해야 합니다. 즉 해당 리스너는 클릭 리스너처럼 DatePicker의 달력
        // 날짜 변경될때
        //자동으로 호출된다!!!.

        datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(),
                new DatePicker.OnDateChangedListener() {
           //해당아래  메서드는 DatePicker 위젯의 날짜가 변경될 때 자동으로  호출된다. 현재 날짜에 맞게 매개변수가 들어간다.
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // 이미 선택한 날짜에 일기가 있는지 없는지 체크
                checkedDay(year, monthOfYear, dayOfMonth);//해당 메서드를 실행하며 날짜가 바뀐다.
            }
        });


        //두번째론

        // 저장 버튼 누르면 매개변수(파일)
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // fileName(파일명)을 넣고 저장 시키는 메소드를 호출 짜피 파일네임은 날짜 바뀔때 자동으로 만들어진다.
                saveDiary(fileName);
            }
        });
    }
    //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ여기까지

/*애초에 파일 이름은  checkedDay 메서드가 호출되면 생기는게 아니라! saveDiary를 해서 파일을 저장해줘야 생긴다!
openFileOutput해당 메서드가 실행되어서 파일을 만드는거다!
그후에 다시 checkedDay메서드 실행되면 try문을 읽어주면서 저장된fileName 에 맞는데이터를 가져오는것이다!
파일은 내부 저장소에 저장된다. 그리고 읽어올때도openFileInput로 내부 저장소에 있는 파일을 읽어오는것이다.

입출력 스트림의 특징인 일반적으로 txt파일 기록하고, 읽어오는것이다.

* */


    // 일기 파일 읽기
    private void checkedDay(int year, int monthOfYear, int dayOfMonth) {

        // 1.받은 날짜로 날짜 보여준다 텍스트창에. setText로.
        viewDatePick.setText(year + " - " + (monthOfYear+1) + " - " + dayOfMonth);

        //2. 파일 이름을 만들어준다. 파일 이름은 "현재 날짜에 맞게.txt" 이런식으로 나옴
        fileName = year + "" + monthOfYear + "" + dayOfMonth + ".txt";

        // 읽어봐서 읽어지면 일기 가져오고
        FileInputStream fis = null;




        try {
            fis = openFileInput(fileName);
//openFileInput이 메서드는 파일을 읽는것이다. 즉 읽은 파일을 fis에 저장시켜주는거지. (파일 없으면 예외처리로 넘어감)

            byte[] fileData = new byte[fis.available()];
            //FileInputStream메서드 available메서드로 파일의 바이트 수를 반환한다.
            //반드시 바이트 배열에 저장해야한다. 데이터의 흐름 및 이동은 바이트로 되기 때문이다.
            //바이트 "배열" 인 이유는

           // fis.read(new byte[fis.available()]); 이렇게 해줘도 된다.
            fis.read(fileData);//fileData에다가(배열에) 데이터를 저장하는 용도로 쓴다.
            //read가 fis에 있는 파일이다. 즉 배열에다가 데이터를 저장시킨다
            fis.close();
            String str = new String(fileData, "EUC-KR");
        //저장된 데이터를 문자열로 바꾼다.

            /*즉 흐름은-> 파일 읽어서 FileInputStream변수에 가져오고->
             해당 파일 바이트 배열수 저장(그냥 전체 읽는 걸로 해도 됨)
            ->바이트 배열에 read메서드로 파일 데이터 저장-> 저장된 데이터 문자열로 바꿔줌.
            이렇게 해주면 이제 파일안의 내용이 문자열로 바뀐것이다.

*/
//애초에 try-catch문이라 위에꺼에서 읽어올 파일이 있으면 아래껀 자동으로 존재한다
            Toast.makeText(getApplicationContext(), "일기 존재", Toast.LENGTH_SHORT).show();

            //문자열 가져온거 보여주고
            edtDiary.setText(str);
            btnSave.setText("수정하기");
            //해당 메서드 실행되면 텍스트창이 수정되는것이다.

            //아래부터는 위 메서드들이 실행되고 오류가 뜨면 넘어온다 , 즉 읽을 파일이 없으면 실행되는것이다.

        } catch (Exception e) { // UnsupportedEncodingException , FileNotFoundException , IOException
            // 없어서 오류가 나면 일기가 없는 것 -> 일기를 쓰게 한다.
            Toast.makeText(getApplicationContext(), "일기 없는 날", Toast.LENGTH_SHORT).show();
            edtDiary.setText("");
            btnSave.setText("새 일기 저장");
            e.printStackTrace();
        }
    }







    // 일기 저장하는 메소드. 버튼 클릭시 실행된다..

    private void saveDiary(String readDay) {

        FileOutputStream fos = null;

        try {

            //파일이 존재하지 않으면 새로운 파일을 만든다 openFileOutput 메서드이다.
            fos = openFileOutput(readDay, MODE_PRIVATE);
            //텍스트 데이터를 파일에 쓰기 위한 스트림을 제공합니다  파일이름과,앱 전용으로 접근 가능하며, 다른 앱에서는 접근할 수 없다는 것을 의미합니다.
            String content = edtDiary.getText().toString();
            //적어놓은거 가져와서 context에 문자열 형태로 저장시킴.
            
            //쓴다. content에 문자열 형태로 저장된걸. 즉 문자열->바이트 배열로 변환해서 파일에 쓴다.
            fos.write(content.getBytes());
            //fos.flush();
            fos.close();
            //위 과정 다 마치고 나면 메시지 띄우고 ㅇㅇ
            Toast.makeText(getApplicationContext(), "일기 저장됨", Toast.LENGTH_SHORT).show();

        } catch (Exception e) { // Exception - 에러 종류 제일 상위 // FileNotFoundException , IOException
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "오류오류", Toast.LENGTH_SHORT).show();
        }






    }



}

























/*CalendarView cal; //캘린더 클래스 객체 생성시 자동으로 오늘 날짜 가져오게 된다.
TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calrender);

cal=findViewById(R.id.cal);
textView=findViewById(R.id.text);


//클릭리스너 말고 클릭했을때 날짜가 달라지기 때문에 아래의 리스너를 써준다.
cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
    @Override
    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
        //인자값으로 3개를 받아와서 실행된다.즉 변경시 내부적으로 이 인자값들을 받고 실행됨.
        //된다고 한다
        textView.setText(year + "년 " + (month + 1) + "월 " + dayOfMonth + "일");

    }
});


    }
}*/

