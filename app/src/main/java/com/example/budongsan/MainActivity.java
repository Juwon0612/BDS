package com.example.budongsan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Join {
    EditText edtId,edtPw;
    Button btnJoin,btnLogin;
    int idFlag=0;
    int pwFlag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtId=(EditText)findViewById(R.id.edtId);
        edtPw=(EditText)findViewById(R.id.edtPw);
        btnJoin=(Button)findViewById(R.id.btnJoin);
        btnLogin=(Button)findViewById(R.id.btnLogin);
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Join.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                sqlDB=myHelper.getReadableDatabase();
                Cursor cursor;
                cursor=sqlDB.rawQuery("SELECT * FROM JoinInfo;",null);
                String edt1=null;
                String pass1=null;
                String edt2=null;
                String pass2=null;
//데이터베이스에 ID PW가 있는지 알아봄
                while(cursor.moveToNext()){
                    edt2=cursor.getString(0);//첫번째 레코드 아이디
                    pass2=cursor.getString(1);//첫번째 레코드 비번
                    edt1=edtId.getText().toString();
                    pass1=edtPw.getText().toString();
                    if(edt2.equals(edt1)){//아이디 일치함
                        idFlag=1;
                        if(pass2.equals(pass1)){//비밀번호가 일치함
                            pwFlag=1;
                            Toast.makeText(getApplicationContext(),"정상회원입니다.",Toast.LENGTH_LONG).show();
//허가된 페이지 (메뉴화면)으로 이동
                            Intent intent=new Intent(getApplicationContext(),MainActivity0.class);
                            startActivity(intent);

                        }
                        else{//아이디는 일치하는데 비밀번호가 틀립니다.
                            Toast.makeText(getApplicationContext(),"id는 일치하는데,비밀번호가 틀립니다.",
                                    Toast.LENGTH_LONG).show();

                        }
                    }
//else{} 아이디가 일치하지 않음

                }//while
//모든 레코드에서 ID 패스워드 일치하는 ID가 없음 패스워드 다 틀림
                if(idFlag==0 && pwFlag==0) {
                    Toast.makeText(getApplicationContext(), "회원이 아닙니다.회원가입해주세요",
                            Toast.LENGTH_LONG).show();

                }
            }
        });
    }

}