package com.minseok.hongdroid1;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.util.LogWriter;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileOutputStream;
import java.util.ArrayList;

public class ChattingAdatpter extends RecyclerView.Adapter<ChattingAdatpter.MyViewHolder> {


    String fileName;
int position;
//ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
    private OnItemClickListener mListener;
    //2.리스너 객체 참조하는 변수 만듬
    public interface OnItemClickListener {

        void onItemClick(View view, int position);

    }//1.리스너 인터페이스 만듬.

    public void setOnItemClickListener(OnItemClickListener listener) {
        Log.v("222","222");

        this.mListener = listener ;
    } //3.리스너 객체 참조를 어댑터에 전달하는 메서드 만듬 해당 메서드 호출 통해 위 리스너 객체 참조변수 들어감.


//클릭 이벤트를 위한 인터페이스 정의 메서드 이름과 파라미터는 형식에 따라

//ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ여기까지가 클릭 리스너 정의
    private Context context;
    private Activity activity;
    private ArrayList book_title;
    // 요소의 타입을 지정하지 않으면 기본적으로 Object 타입으로 처리됩니다


    ChattingAdatpter(Activity activity, Context context,ArrayList book_title){
        this.activity = activity;
        this.context = context;
        this.book_title = book_title;

    }
//어댑터 생성자를 통해 위 book_title에 배열이 쌓인다.(Chatting클래스에서 데이터베이스에서 뽑은거 넘겨주는것이다)


//아랫것들 다 어댑터 생성( setAdapter)시 호출되는 함수이다.1. getItem,

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.chat, parent, false);

        return new ChattingAdatpter.MyViewHolder(view);
            }
    // 인플레이트시켜서 onBindViewHolder의 매개변수로 넘긴다,어댑터 연결시 자동 실행.
    //

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.book_title_txt.setText(String.valueOf(book_title.get(position)));
        ChattingAdatpter.this.position=position;
//뷰홀더 클래스 내에있는 book_title_txt는 chat.xml의 id 값이다. 즉 해당 id값에 대한것을 바꿔주는것이다.
        //그렇기에 해당 메서드가 실행될때마다 화면에 나타나는 값이 달라지는것이다. setText로 바꿔주니까
//그리고 각각의 holder가 다르기때문에
        Log.v("222222","222222");
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "안녕하세요!", Toast.LENGTH_SHORT).show();
            mListener.onItemClick(v,position);

            }
        });        // 여기서 호출할 수도 있음 근데 여기선 각 포지션값을
        //getAdapterPosition() 이 메서드는 뷰홀더 클래스 내에서만 사용 가능
    }

    @Override
    public int getItemCount() {
        Log.v("3333333","3333");
        return book_title.size();


    }//값 반환해서 onBindViewHolder로 넘긴다고 봐도 된다.
    //위 세 가지 메서드는 어댑터 클래스의 메서드이다. 뷰홀더 클래스가 아니다.


    /* 뷰홀더 클래스는 onCreateViewHolder메서드가 호출될때 생성한다 그리고 리턴한다.안에 인플레이트된
    xml파일에 대해서 들어감*/




    //onCreateViewHolder 메서드에 의해 객체가 만들어 진다. 이때 각각의 객체에 대해 값들이 세팅되고 데이터를 클릭할 수 있게 세팅도 가능하다.
    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView book_title_txt;
        LinearLayout mainLayout;

//itemView는 뷰홀더가 관리하는 아이템들에 대한 view이다. 즉 화면에 나타나는 각각의 아이템에 대한 View란소리다.
        //그래서 아래 itemView.setOclick리스너 설정하면 터치 이벤트를 쓸 수 있다.

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            book_title_txt = itemView.findViewById(R.id.book_title_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout1);
           /* mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    //해당 아이템 위치 얻음 각 포지션마다


                    if (pos != RecyclerView.NO_POSITION) {
                        // 리스너 객체의 메서드 호출.
                        if (mListener != null) {
                            mListener.onItemClick(v, pos);
                            Log.v("11","11");
                        } 이렇게 해도 되는걸 위에서 onBindViewHolder에서 구현해놨음.
                    }
                }
            });*/
        }
    }
}