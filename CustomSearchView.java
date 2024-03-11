import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;

public class CustomSearchView extends SearchView {
    private ImageView searchIcon;

    public CustomSearchView(Context context) {
        super(context);
        init(context);
    }

    public CustomSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        // SearchView 내부의 ImageView를 찾습니다.
        int searchIconId = getContext().getResources().getIdentifier("android:id/search_button", null, null);
        searchIcon = findViewById(searchIconId);

        // 이미지에 클릭 리스너를 설정합니다.
        searchIcon.setOnClickListener(v -> {

            Toast.makeText(context.getApplicationContext(), "클릭했따", Toast.LENGTH_SHORT).show();
            // 이미지를 클릭했을 때 수행할 작업을 여기에 작성합니다.
            // 예를 들어, 클릭 이벤트를 처리하거나 원하는 작업을 수행할 수 있습니다.
        });
    }
}
