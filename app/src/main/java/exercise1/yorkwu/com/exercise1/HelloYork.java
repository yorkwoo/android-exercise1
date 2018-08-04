package exercise1.yorkwu.com.exercise1;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

// When ! appears, move cursor on the word and press Alt+Enter

public class HelloYork extends AppCompatActivity {

    private EditText    et_login;
    private EditText    et_passwd;
    private Button      btn_ok;
    private TextView    tv_message;
    private static final String TAG = "YORK_DEBUG";

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
                String callApp = "yorkwusoft.com.logincheck";
                // 這邊改用遠端 activity 幫我們驗證
                try {
                    Bundle b = new Bundle();
                    b.putString("KEY_ID", et_login.getText().toString());
                    b.putString("KEY_PWD", et_passwd.getText().toString());
                    Intent intent = new Intent();
                    //intent.setClass(HelloYork.this, LoginCheck.class);
                    // setClassName can be used to call another process's activity
                    intent.setClassName(callApp,
                            "yorkwusoft.com.logincheck.ykLoginCheck");
                    intent.putExtras(b);
                    /* request code is am arbitrary code which used once. */
                    HelloYork.this.startActivityForResult(intent, 12345);
                } catch (ActivityNotFoundException anfe){
                    String msg = "Please install "+callApp+" first!";
                    Toast.makeText(HelloYork.this, msg, Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    e.printStackTrace();
                    Log.e(TAG, e.toString());
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

    @Override
    protected void onActivityResult(int reqCode, int retCode, Intent data)
    {
        switch(reqCode)
        {
            case 12345:
                switch(retCode)
                {
                    case 999:
                        if(data != null && data.getExtras().containsKey("KY_RESULT")){
                            if(data.getExtras().getBoolean("KY_RESULT")) {
                                showLoginOkWindow();
                            } else {
                                tv_message.setText(getResources().getString(R.string.tv_msg_loginfail));
                                tv_message.setTextColor(getResources().getColor(R.color.colorMessage));
                            }
                        }
                        break;
                }
                break;
        }
    }

}
