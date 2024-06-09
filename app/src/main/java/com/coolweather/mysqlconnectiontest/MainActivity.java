package com.coolweather.mysqlconnectiontest;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import androidx.appcompat.app.AppCompatActivity; // 修改此处
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.coolweather.mysqlconnectiontest.R;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private TextView textView;
    private static final int TEST_USER_SELECT = 1;
    int i = 0;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String user;
            switch (msg.what) {
                case TEST_USER_SELECT:
                    Test test = (Test) msg.obj;
                    user = test.getName();
                    System.out.println("***********");
                    System.out.println("***********");
                    System.out.println("user:" + user);
                    textView.setText(user);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.bt_send);
        textView = (TextView) findViewById(R.id.tv_response);
    }

    @Override
    protected void onStart() {
        super.onStart();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 执行查询操作
                // 通过点击 button 自增长查询对应 id 的 name
                if (i <= 3) { // 因为数据库我就添加了三个数据条数，所以进行判断使其可以循环查询
                    i++;
                } else {
                    i = 1;
                }
                // 连接数据库进行操作需要在主线程操作
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Connection conn = null;
                        conn = (Connection) DBOpenHelper.getConn();
                        String sql = "select name from test_one where id='" + i + "'";
                        Statement st;
                        try {
                            st = (Statement) conn.createStatement();
                            ResultSet rs = st.executeQuery(sql);
                            while (rs.next()) {
                                // 因为查出来的数据是结果集的形式，所以我们新建一个 javabean 存储
                                Test test = new Test();
                                //test.setUser(rs.getString(1));
                                test.setName(rs.getString(1));//* 修改：
                                Message msg = new Message();
                                msg.what = TEST_USER_SELECT;
                                msg.obj = test;
                                handler.sendMessage(msg);
                            }
                            st.close();
                            conn.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }
}
