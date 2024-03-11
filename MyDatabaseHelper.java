package com.minseok.hongdroid1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;


//SQLiteOpenHelper로 본격적으로 데이터베이스를 쓸 수 있게 한다.
//두가지 메서드 오버라이딩 해야함(구현해야함), onCreate,onUpgrade
//그리고 생성자도 만들어주고




class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "BookLibrary.db";
    private static final int DATABASE_VERSION = 2;
    //데이터베이스 이름 및 버전임(권장사항)
    private static final String TABLE_NAME = "my_library";
    //내 라이브러리와 같은 이름으로 지정해야함
    //아래부터 데이터베이스의 열을 지정한다. 즉 4개의 열이 설정된거다.
     final String COLUMN_ID = "_id"   ;
    private static final String COLUMN_TITLE = "book_title";
    private static final String COLUMN_AUTHOR = "book_author";
    private static final String COLUMN_PAGES = "book_pages";


    MyDatabaseHelper(@Nullable Context context) { //(자동으로 구현해야함(
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        //1.DATABASE_NAME은 DB파일명임. 이 파일안에 여러개의 테이블이 들어간다.
        //SQLiteOpenHelper의 생성자를 호출하는 것이다.(super해주면 상속받은쪽 먼저 들어가니까)
    }

    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_AUTHOR + " TEXT, " +
                COLUMN_PAGES + " INTEGER);";
        db.execSQL(query);//이게 이제 하나의 테이블을 만드는 것이다.


        //데이터베이스 처음 만들때 딱 한번 호출됨. 데이터베이스 생성 및 초기 테이블 만듬.
        // 데이터베이스가 생성될 때 이 메서드가 호출되어 데이터베이스 내에 테이블을 생성합니다.
/*테이블을 생성하기 위한 SQL 쿼리를 문자열로 작성합니다. 이 쿼리는 테이블의 스키마를 정의합니다. 쿼리는
CREATE TABLE 문으로 시작하며, 테이블 이름과 각 열에 대한 정의를 포함합니다.
*///db.execSQL() 호출: execSQL() 메서드를 사용하여 데이터베이스에 위에 짜준 쿼리문을 넣어준다
/*
CREATE TABLE : 새 테이블을 생성하는 SQL 문입니다.
TABLE_NAME : 생성할 테이블의 이름입니다. 코드에서 (젤 위에)상수로 정의되어 있습니다.
(COLUMN_ID-> ,INTEGER PRIMARY KEY AUTOINCREMENT(이건 행이 추가될수록 자동으로 값이 증가되는것임 (근데 없어도 뭔 차인지 모르겠음),
테이블의 열을 정의하는 부분입니다. 여기서는 네 개의 열이 정의되어 있습니다.
COLUMN_ID : INTEGER 형식으로 정의된 테이블의 기본 키(primary key) 열입니다. AUTOINCREMENT 키워드로 자동 증가되는 값으로 설정되어 있습니다.
이렇게 해서 각각의  총 4개의 열이 결정되었다.데이터 베이스 형식이기 때문에 암기해야한다. TABLE_NAME 제외한 나머지 4개는 열을 나타내준다.
이렇게 짜준걸 execSQL()메서드로 데이터베이스에 넣는것이다.
 */
    }
    @Override// DB업그래이드 필요 시 자동으로 호출됨.(version값에 따라 반응)
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }




    /*!!!!!!!해당 메서드 호출시 새로운 값들을 데이터베이스에 추가함.!!!!!1*/
    void addBook(String title, String author, int pages)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        //쓸수있는 형태로 데이터베이스  가져오고->this.이니까 자기 자신 클래스 및 상속받은 메서드임
        ContentValues cv = new ContentValues();
//  ContentValues 이 객체는 데이터베이스에 추가될 값을 저장하는 데 사용됩니다.

//  첫 번째 매개변수는 키, 호출 이름과 값이다.
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_AUTHOR, author);
        cv.put(COLUMN_PAGES, pages);
//키(해당 열임), 쌍 값으로 데이터 입력

        //해당
/*insert() 메소드를 사용하여 데이터베이스 테이블에 새로운 행을 추가합니다
TABLE_NAME은 테이블의 이름을 나타내며, cv에 저장된 값들이 해당 테이블에 추가됨 、중간에 ｎｕｌｌ은 비어있는 ｃｖ속 객체 전달받을시 처리할 값임。
추가된 행의 ID가 result 변수에 반환됩니다.
*/
        long result = db.insert(TABLE_NAME,null , cv);
//여기에서 어떤 테이블명에 넣을건지 선별해주는것이다. 그래서 여러개의 테이블을 들고와도 상관
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }

        //삽입이 성공하면 새로 삽입된 행의 키 값을 반환합니다. 실패하면 -1을 반환합니다.
    }
//해당 메서드는  AddActivity 액티비티에서 호출한다. 그러면서





    //데이터베이스에서 모든 객체를
    // Cursor 클래스는 데이터베이스 결과 집합을 표현하는 데 사용된다.
    /*

    ※즉 아래 메서드는 데이터베이스에서 데이터를 읽는것이다!Cusor. DB브라우저에 잇는 값들을 읽는데 사용되는것이다.
    DB브라우저에 데이터를 저장시키고->저장된 데이터를 화면에 가져오는것이다.

    */
//    중요하다 아래 메서드가 !!!!!!!!!!!!!!!!!!!!!!!!!!
    public Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        //모든 데이터 선택
        //SELECT * FROM은 데이터베이스에서 모든 열을 선택하고 TABLE_NAME은 데이터를 가져올 테이블의 이름을 나타냅니다
        //SELECT * FROM이거 자체가 데이터베이스에 관련된 구문이다!
        SQLiteDatabase db = this.getReadableDatabase();
//읽기 형식으로 열어줌.

        Cursor cursor = null; //cursor객체를 null로 초기화한다. ※null값은 기본 데이터 타입은 못 가지고 참조 타입 변수만 즉 객체참조 변수만 가질 수 있다.
        if(db != null) {
            cursor = db.rawQuery(query, null);

            //rawQuery() 메서드의 매개변수는 왼쪽은 실행할 sql쿼리를 나타내고,우측은 바인딩할 매개변수이다(null로 해주는걸로 )
            /*
       ※ 이 메서드는 사용자가 작성한 직접적인 SQL 쿼리를 실행하고 그 결과를
       나타내는 Cursor 객체를 반환합니다. 그래서 그 안에 데이터들을 넣어주고 보내주는것이다.

       sql쿼리를 실행한다는 말은 데이터베이스에 있는 명령어를 실행한다는 것을 의미한다 데이터 읽고,쓰기 이런것이다.

       즉 흐름을 보면 SQLiteDatabase의 rawQuery메서드로 내부에 데이터베이스의 데이터들을 넣어주고, 리턴타입이 Corsor클래스로
       반환한다. 그렇게 반환하면 이제 객체참조변수 cursor로 직접적으로 sql쿼리를 실행하고 결과를 나타낼수 있는 초석이 마련된 것이다.
 즉 그냥 데이터 다룰주있게끔 해주기 위해 cursor객체안에 데이터베이스 다 선택해 넣고  리턴. ㅇㅇ
 */
        }
        return cursor;
    }




    //이 메서드는 데이터베이스에서 특정 행을 업데이트 한다.
    void updateData(String row_id, String title, String author, String pages){
        SQLiteDatabase db = this.getWritableDatabase();
        //getWritableDatabase() 메서드를 호출하여 쓰기 모드로 열어줌.
        ContentValues cv = new ContentValues();
        //ContentValues 객체에 업데이트할 데이터를 추가합니다
        /* 아래 메서드로 값들이 바뀌는거다!!!!!!!!!!!!!!! 데이터베이스 값들이 바뀌었으니 재실행해주면
        해당 값들로 다시 화면에 띄워지는것이다 --> 근데 나는 수정하자마자 (새로고침 없이) 화면에 띄우고 싶으니
        ! */

        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_AUTHOR, author);
        cv.put(COLUMN_PAGES, pages);
        //키값들은 열이다!
        int result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
//데이터베이스 레코드에 업데이트한다. (화면에 띄워주는건 아니다!!)->화면에 띄워지는건 자동으로 된다.

        /*
        여기서 TABLE_NAME은 업데이트할 테이블의 이름을 나타내고, cv는 업데이트할
        데이터를 포함하는 ContentValues 객체입니다. "_id=?"는 업데이트할 레코드를 식별하기 위한 조건을 나타냅니다.
        여기서는 "_id" 열의 값이 row_id와 일 row_id치하는 레코드를 업데이트하도록 설정합니다. 세 번째 매개변수로
        전달되는 배열은 조건에 들어갈 매개변수로, 실제 값이 여기에 포함됩니다. update() 메서드는 업데이트된 레코드의 수를 반환합니다.
        */


        //-1는 작업에 실패했을때
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }
//결과가 -1이면 실패로 간주하고, 그렇지 않으면 성공 메시지를 토스트로 표시합니다
        //-1은 데이터베이스 작업이 실패했을 때 insert() 메서드가 반환하는 값입니다.
    }


    //이 메서드는 데이터베이스에서 특정 행을 삭제합니다.
    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase(); //쓰기 가능한 데이터를 다 가져온다.
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
/*
첫 번째 매개변수는 삭제할 행이 있는 테이블의 이름입니다.
두 번째 매개변수는 삭제할 행을 식별하는 조건을 지정합니다. 여기서는 "_id=?"로 지정되어 있으며, 이는 _id 열이 주어진 row_id 값과 일치하는 행을 삭제하도록 지정합니다.
세 번째 매개변수는 첫 번째 매개변수에서 사용된 조건에 대응하는 값으로, ?에 해당하는 값들을 여기에 전달합니다. 여기서는 row_id 값이 해당합니다.
delete() 메서드는 삭제된 행의 수를 반환합니다.
*/
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);

   }
//이 메서드는 데이터베이스의 모든 데이터를 삭제합니다.
//먼저 getWritableDatabase() 메서드를 호출하여 쓰기 가능한 데이터베이스를 가져옵니다.
//db.execSQL() 메서드를 사용하여 "DELETE FROM " + TABLE_NAlME 쿼리를 실행하여 모든 데이터를 삭제합니다.
}
/*
* SQLite 데이터베이스는 읽기 전용 모드와 쓰기 가능한 모드로 열 수 있습니다.
*  쓰기 가능한 모드로 데이터베이스를 열면 데이터를 수정하고 변경할 수 있습니다.
* 이는 데이터베이스의 내용을 갱신하거나 새 데이터를 추가하거나 삭제하는 등의 작업을
* 수행할 수 있음을 의미합니다.
*즉
* 따라서 getWritableDatabase() 메서드는 쓰기 가능한 데이터베이스를 반환합니다.
*  이 메서드를 사용하여 데이터베이스에 대한 쓰기 작업을 수행할 수 있습니다.
* */