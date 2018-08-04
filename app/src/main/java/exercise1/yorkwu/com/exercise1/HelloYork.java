package exercise1.yorkwu.com.exercise1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;

// When ! appears, move cursor on the word and press Alt+Enter

public class HelloYork extends AppCompatActivity {

    private EditText    et_login;
    private EditText    et_passwd;
    private Button      btn_ok;
    private TextView    tv_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        tv_message = (TextView)HelloYork.this.findViewById(R.id.tv_LoginResult);
        et_login = (EditText)findViewById(R.id.ed_Login);
        et_passwd = (EditText)findViewById(R.id.ed_Passwd);
        btn_ok = (Button)findViewById(R.id.btn_ok);
        String defMsg = getResources().getString(R.string.tv_msg_defaultMsg);
        tv_message.setText(defMsg);
        tv_message.setTextColor(getResources().getColor(R.color.colorMessage));

        final String stdLogin = "yorkwu";
        final String stdPasswd = "1234";
        // Set button click event, so complex
        btn_ok.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                if(et_login.getText().toString().equalsIgnoreCase(stdLogin) &&
                        et_passwd.getText().toString().equalsIgnoreCase(stdPasswd)){
                    //tv_message.setText("Login Successful!");
                    //tv_message.setTextColor(getResources().getColor(R.color.colorMessage));
                    Log.i("YORK_DEBUG", "Login success!");
                    showLoginOkWindow();
                } else {
                    String msg = getResources().getString(R.string.tv_msg_loginfail);
                    tv_message.setText(msg);
                    tv_message.setTextColor(getResources().getColor(R.color.colorErrorMsg));
                }
            }
        });
        tv_message.setOnLongClickListener(new TextView.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v)
            {
                et_login.setText(stdLogin);
                et_passwd.setText(stdPasswd);
                return false;
            }
        });

    }

    void showLoginOkWindow()
    {
        String TAG="YORK_DEBUG";
        Log.i(TAG, "showLoginOkWindow() runs.");
        Intent intent = new Intent();
        intent.setClass(this, LoginOk.class);
        Bundle bb = new Bundle();
        bb.putString("LoginID", et_login.getText().toString());
        intent.putExtras(bb);
        // To prevent target activity busy to crash my program, using try
        try {
            //HelloYork.this.startActivity(intent);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, e.toString());
        }
        //HelloYork.this.finish();

    }
}
