package com.minseok.hongdroid1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//처음에 데이터베이스에 저장하고->데이터베이스에 있는거 가져오고 리스트에 다 저장시키고->리스트에 있는거
//
public class mainAdapter extends RecyclerView.Adapter<mainAdapter.MyViewHolder> {
/*리싸이클러 뷰에 대한 어댑터 클래스.
//리스트뷰에 사용자가 정의한 데이터를 표시해주기 위해선 어댑터를 사용해줘야 한다.
    커스텀 어댑터클래스 안엔 뷰홀더가 정의되어있어야 함. 그냥 규칙이고 3개의 필수 메서드
    그중 두개는 onBind메서드의 매개변수로 들어간다는것까지 확인하고.
    즉 arraylist배열에 쌓인것들을 넣어주는것임.
  */




    private Context context;
    private Activity activity;
    private ArrayList book_id, book_title, book_author, book_pages;
    // 요소의 타입을 지정하지 않으면 기본적으로 Object 타입으로 처리됩니다


    //생성자로 어댑터 호출할때
    mainAdapter(Activity activity, Context context, ArrayList book_id, ArrayList book_title, ArrayList book_author,
                ArrayList book_pages){
        this.activity = activity;
        this.context = context;
        this.book_id = book_id;
        this.book_title = book_title;
        this.book_author = book_author;
        this.book_pages = book_pages;

  //
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }//여기서 리턴되는값들은 싹다 배열이다 ㅇㅇ
    //리싸이클러가 화면에 뜰때 자동으로 실행된다. 리스트 수 만큼 아래 onbindbiew홀더메서드랑 계속해서 반복한다.
    // 인플레이트시켜서 onBindViewHolder의 매개변수로 넘긴다.(매개변수로 넘기기 전에 MyViewHolder 생성자 거쳐서 진행된다)

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.book_title_txt.setText(String.valueOf(book_title.get(position)));
        holder.book_author_txt.setText(String.valueOf(book_author.get(position)));
        holder.book_pages_txt.setText(String.valueOf(book_pages.get(position)));
        holder.book_id_txt.setText(String.valueOf(holder.getAdapterPosition()+1));
        /*매개값 들어오자마자 바로 해당 리싸이클러뷰 내용 다 수정함 setText로. 그리고 각각의 holder값은 다 다르다
        새로운 객체들을 만드는 new MyViewHolder 때문이다. 즉 주소값들이 다 다르다는것이다.
        // 리싸이클러 뷰가 나타나는 화면이  업데이트 되는 것이다.(배열 내용이 전부 다)
//onCreateViewHolder에서 리턴하는것, getItemCount이 리턴하는게 여기로 들어간다.position이 12개면 0부터 11까지
        총 12번 반복된다. 즉 arraylist의 0번째에 있는값들 다 저장하고, 1번째에 있는거 다 저장하며12번 반복된다.
         */
        //여기서 positon은 배열의 갯수이다!.아래 getItemCount메서드로 인해서 배열의 갯수만큼 positon이 실행된다.
       //!!! 이해 완료. 그렇게 position에 맞게  데이터의 값들이 세팅되는것이다. 아래 holder.main과는 별개임!
        //즉 해당 메서드 때문에 데이터에 있는



//!!아래에 정의한 main레이아웃의 클릭 리스너를 정한다. 즉 클릭시 무엇을 할건지 해당 레이아웃이
//해당 뷰를 클릭할때마다 아래 메서드가 진행됨 ㅇㅇ!!! 즉 화면에 뜨는 뷰를 클릭할때마다 자동으로 해당 holder객체참조변수에 맞게
        //아래 클릭 리스너가 호출되는거다!화면에 onBindViewHolder로 다 띄우고 나서 이제 클릭리스너를 설정해주면 해당 뷰를 클릭할 수 있다는 것이다

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                //UpdateActivity클래스로 이동하기 위한 인텐트를 생성한다. 아래에서 mainLayout에 대해 저장되어있다.)
                intent.putExtra("id", String.valueOf(book_id.get(position)));
                intent.putExtra("title", String.valueOf(book_title.get(position)));
                intent.putExtra("author", String.valueOf(book_author.get(position)));
                intent.putExtra("pages", String.valueOf(book_pages.get(position)));
                activity.startActivityForResult(intent, 1);

/*putExtra메서드는 값을 넣어주는 것인데, String.valueOf(book_id.get(position))는 실제 데이터입니다. book_id 리스트에서
 position 위치에 있는 값을 가져와서 문자열로 변환한 후에 "id" 키와 함께 Intent에 추가했습니다.


*/            }
        });
//리사이클러뷰의 아이템을 클릭하면 발생하는 이벤트이다.mainLayout은 해당 리사이클러뷰 자체이다.
// 이건 그냥 가져오는것이다




    }

    @Override
    public int getItemCount() {
        return book_id.size();
    }//값 반환해서 onBindViewHolder로 넘긴다



/* 뷰홀더 클래스는 onCreateViewHolder메서드가 호출될때 생성한다 그리고 리턴한다.안에 인플레이트된
xml파일에 대해서 들어감*/
    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView book_id_txt, book_title_txt, book_author_txt, book_pages_txt;
        LinearLayout mainLayout;


        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            book_id_txt = itemView.findViewById(R.id.book_id_txt);
            book_title_txt = itemView.findViewById(R.id.book_title_txt);
            book_author_txt = itemView.findViewById(R.id.book_author_txt);
            book_pages_txt = itemView.findViewById(R.id.book_pages_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            //여기서 mainLaout은 위 onCreateViewHolder에서 my_row에서 연결시켰기 때문에 가능한것이다. 배열수만큼 계속해서
            //객체마다 리턴된다. 즉 mainLaout에 저장되는 주소값이 계속해서 달라진다 . 왜냐? 리스트에 저장된 배열이 다 다르니까도 맞지만
            //확실한건 객체를 계속해서 생성해서 리턴하기 때문이다.
            //Animate Recyclerview
            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }

    }

}