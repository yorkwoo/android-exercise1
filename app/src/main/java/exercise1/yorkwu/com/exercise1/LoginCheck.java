package exercise1.yorkwu.com.exercise1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

// 這是一個專門檢查密碼的行程，所以不需要開啟畫面
public class LoginCheck extends AppCompatActivity {

    final String stdLogin = "yorkwu";
    final String stdPasswd = "1234";

    private Bundle retBundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.login_check);
        // get bundle
        Bundle b = LoginCheck.this.getIntent().getExtras();
        if(b != null && b.containsKey("KEY_ID") && b.containsKey("KEY_PWD")){
            if(b.getString("KEY_ID").equalsIgnoreCase(stdLogin) &&
            b.getString("KEY_PWD").equalsIgnoreCase(stdPasswd)){
                retBundle.putBoolean("KY_RESULT", true);
            } else {
                retBundle.putBoolean("KY_RESULT", false);
            }
        } else {
            retBundle.putBoolean("KY_RESULT", false);
        }

        Intent retIntent = new Intent();
        retIntent.putExtras(retBundle);
        LoginCheck.this.setResult(999, retIntent);
        LoginCheck.this.finish();
    }
}
