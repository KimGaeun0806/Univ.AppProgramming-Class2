package org.techtown.ap4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // et_syrup 인스턴스 들고와서 텍스트 변경 관련 이벤트 핸들러 부착
        EditText et_syrup = findViewById(R.id.et_syrup);
        et_syrup.addTextChangedListener(new TextWatcher() { // TextWatcher 자동완성하면 밑에 내용 뜸
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) { // EditText의 text attribute값이 한글자라도 변경된다면 호출
                Calculate(); // 글자 바뀔 때마다 calories 재계산
            }
        });
    }

    int calories;

    void Calculate() {
        CheckBox cb_banana = findViewById(R.id.cb_banana);
        CheckBox cb_pineapple = findViewById(R.id.cb_pineapple);
        CheckBox cb_chocochip = findViewById(R.id.cb_chocochip);

        RadioButton rb_vanilla = findViewById(R.id.rb_vanilla);
        RadioButton rb_choco = findViewById(R.id.rb_choco);
        RadioButton rb_yogurt = findViewById(R.id.rb_yogurt);

        EditText et_syrup = findViewById(R.id.et_syrup);
        TextView tv_calories = findViewById(R.id.tv_calories);
        ProgressBar pb_calories = findViewById(R.id.pb_calories);


        // 칼로리
        calories = 0;

        // 베이스
        if (rb_vanilla.isChecked()) { // rb_vanilla에 체크된 상태라면 괄호 안 코드 실행
            calories = 1000;
        }
        if (rb_choco.isChecked()) {
            calories = 2000;
        }
        if (rb_yogurt.isChecked()) {
            calories = 800;
        }

        // 토핑. 세 개 다 true일 수도 있고, 세 개 다 false일 수도 있음
        if (cb_banana.isChecked()) { // calories 누적시키도록
            calories += 200;
        }
        if (cb_pineapple.isChecked()) {
            calories += 100;
        }
        if (cb_chocochip.isChecked()) {
            calories += 400;
        }

        // 시럽
        int syrup = Integer.parseInt(et_syrup.getText().toString()); // EditText에 적혀있는 글자들에서 적합한 숫자를 던져줌

        if (syrup < 0) {
            syrup = 0;
        }
        if (syrup > (5000-2700)/5) {
            syrup = (5000-2700)/5;
        }
        calories += syrup * 5; // syrup 1당 5칼로리 추가

        // 최종 칼로리 화면에 표시
        tv_calories.setText("" + calories); // calories가 int값이기 때문에, string + int를 함 -> string 형식 값만 남음
        pb_calories.setProgress(calories); // progress attribute값을 새로 계산한 calories값으로 세팅. 숫자니까 int값 그대로 넣어도 됨
    }

    public void onClick(View v) { // 여섯 개 모두 onClick에 onClick을 지정해놨기 때문에 클릭하면 이게 실행됨
        Calculate(); // 클릭하면 Calculate 메소드 호출
    }

    public void bt_order_onClick(View v) {
        if (calories >= 2000) {
            Toast.makeText(this, "권장 칼로리를 초과합니다.", Toast.LENGTH_LONG).show(); // context, 무슨 텍스트를 보여줄건지, 얼마나 길게 보여줄건지
        }
        else {
            Toast.makeText(this, "권장 칼로리 이내입니다.", Toast.LENGTH_LONG).show();
        }
    }
}