package exercise1.yorkwu.com.exercise1;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class LoginOk extends Activity {

    private TextView tv_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LoginOk.this.setContentView(R.layout.login_ok);
        tv_msg = (TextView)findViewById(R.id.tv_Message);
        Bundle b = LoginOk.this.getIntent().getExtras();
        // must check
        if(b != null && b.containsKey("LoginID")){
            String msg = getResources().getString(R.string.tv_msg_loginOk);
            tv_msg.setText(msg+b.getString("LoginID"));
        }
    }
}
