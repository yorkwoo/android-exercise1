package exercise1.yorkwu.com.exercise1;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Ex5 extends AppCompatActivity {

    private EditText et_login;
    private EditText    et_passwd;
    private Button btn_ok;
    private TextView tv_message;
    private static final String TAG = "YORK_DEBUG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_spinner);

        tv_message = (TextView)Ex5.this.findViewById(R.id.tv_LoginResult);
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
                    intent.setClass(Ex5.this, LoginCheck.class);
                    // setClassName can be used to call another process's activity
                    //intent.setClassName(callApp,
                    //        "yorkwusoft.com.logincheck.ykLoginCheck");
                    intent.putExtras(b);
                    /* request code is am arbitrary code which used once. */
                    Ex5.this.startActivityForResult(intent, 12345);
                } catch (ActivityNotFoundException anfe){
                    String msg = "Please install "+callApp+" first!";
                    Toast.makeText(Ex5.this, msg, Toast.LENGTH_SHORT).show();
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
        spinner_init();

    }

    private Spinner sp1, sp2;
    private ArrayAdapter<String> aadapter;
    private ArrayList<String> alist;

    void spinner_init()
    {
        sp1 = (Spinner)findViewById(R.id.sp1);
        sp2 = (Spinner)findViewById(R.id.sp2);
        alist = new ArrayList<String>();
        alist.add("Please choose one of the following...");
        alist.add("Movie");
        alist.add("Food");
        alist.add("Programming");

        aadapter = new ArrayAdapter<String>(Ex5.this, android.R.layout.simple_spinner_item, alist);
        sp1.setAdapter(aadapter);
        sp1.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setup_sp2(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp2.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0) {
                    String text = alist.get(position);
                    tv_message.setText(text);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                tv_message.setText("Select one?");
            }
        });
    }

    void setup_sp2(int sp1_sel)
    {
        alist = new ArrayList<String>();
        if(sp1_sel > 0) {
            alist.add("Please choose one");
        }
        switch(sp1_sel)
        {
            case 1:
                alist.add("Titanic");
                alist.add("Iron man");
                alist.add("Harry Potter");
                break;
            case 2:
                alist.add("Burger");
                alist.add("Noodle");
                alist.add("Vegetable");
                break;
            case 3:
                alist.add("Java");
                alist.add("Python");
                alist.add("PHP");
                break;
        }
        aadapter = new ArrayAdapter<String>(Ex5.this, android.R.layout.simple_spinner_item, alist);
        sp2.setAdapter(aadapter);
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
            //Ex5.this.startActivity(intent);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, e.toString());
        }
        Ex5.this.finish();

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
