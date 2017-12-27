package cn.edu.gdmec.android.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.edu.gdmec.android.R;
import cn.edu.gdmec.android.utils.MD5Utils;

public class LoginActivity extends AppCompatActivity {

    private TextView tv_main_title;
    private TextView tv_back;
    private TextView tv_register;
    private TextView textView;
    private Button btn_login;
    private TextView tv_find_psw;
    private EditText et_user_name;
    private EditText et_psw;
    private String userName;
    private String psw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
    }

    /**
     * 找出登录页面的控件
     */
    private void init() {
        tv_main_title = (TextView) findViewById(R.id.tv_main_title);
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_register = (TextView) findViewById(R.id.tv_register);
        textView = (TextView) findViewById(R.id.tv_find_psw);
        btn_login = (Button) findViewById(R.id.btn_login);
        tv_find_psw = ((TextView) findViewById(R.id.tv_find_psw));
        et_user_name = (EditText) findViewById(R.id.et_user_name);
        et_psw = (EditText) findViewById(R.id.et_psw);
        tv_main_title.setText("登录");
        //返回按钮的点击时间，即关闭当前页面
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.finish();
            }
        });
        //立即注册按钮的点击事件
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //通过Intent类实现页面跳转，第一次参数可以是当前页面，第二个参数为要跳转的页面.class
                Intent intent =new Intent(LoginActivity.this,RegisterActivity.class);
                //startAcitivity开始跳转，而startActivityForResult可以返回一个结果
                startActivityForResult(intent,1);
            }
        });
        //找回密码按钮的点击事件
        tv_find_psw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到找回密码页面（暂时不作处理）
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入文本的数据，其中对密码进行md5加密以便做判断
                userName = et_user_name.getText().toString().trim();
                psw = et_psw.getText().toString().trim();
                //调用MD5Utils的md5方法将用户输入的密码加密
                String md5Psw =  MD5Utils.md5(psw);
                //封装一个方法用于读取sharedPreferences中用户保存的数据，以便作校对
                String spPsw = readPsw(userName);
                //获取之后，实现校验逻辑，即对用户输入内容做判断并给出提示
                if (TextUtils.isEmpty(userName)){
                    Toast.makeText(LoginActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                }else if (TextUtils.isEmpty(psw)){
                    Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }else if (md5Psw.equals(spPsw)){
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    //登录成功之后需要在sharedPreferences中保存登录状态并实现页面跳转，在这里封装一个保存的方法
                    saveLoginStatus(true,userName);//设置第一个参数为布尔值来确定登录状态
                    Intent data =new Intent();
                    data.putExtra("isLogin",true);//使用isLogin，用true表示登陆成功
                    setResult(RESULT_OK,data);//通过setResult方法告诉前一个页面当前已经OK，第二个参数为传递信息的Intent
                    LoginActivity.this.finish();
                    //跳转到主页
                    return;
                }else if (!TextUtils.isEmpty(userName) && md5Psw.equals(spPsw)){
                Toast.makeText(LoginActivity.this, "用户名和密码不一致", Toast.LENGTH_SHORT).show();
                return;
                }else{
                    Toast.makeText(LoginActivity.this, "此用户名不存在", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }

    private void saveLoginStatus(boolean status, String userName) {
        SharedPreferences sp = getSharedPreferences("loginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();//获取编辑器
        editor.putBoolean("isLogin",status);//保存布尔值类型的登录状态
        editor.putString("LoginUserName",userName);//保存登录时的用户名
        editor.commit();//提交修改

    }


    private String readPsw(String userName) {
        //获取haredPreferences类读取其中存储的数据，第一个参数为文件名称，第二个参数为设置类型（私有）
        SharedPreferences sp = getSharedPreferences("loginInfo",MODE_PRIVATE);
        return sp.getString(userName,"");//通过getString方法，由用户名索引到在sharedPreferences中存储的密码
    }

    //上面的 startActivityForResult返回的结果到此处，即注册成功的数据返回
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       if (data!=null){
           //从注册页面获取传递过来的数据
           String userName = data.getStringExtra("userName");//通过getStringExtra方法传入用户名
           if (!TextUtils.isEmpty(userName)){
               et_user_name.setText(userName);//拿到用户名之后将其显示在页面上
               et_user_name.setSelection(userName.length());//设置光标在用户名之后显示出来
           }
       }
    }
}
