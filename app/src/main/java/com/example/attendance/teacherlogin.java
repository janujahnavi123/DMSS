package com.example.attendance;

        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        import androidx.appcompat.app.AppCompatActivity;

public class teacherlogin extends AppCompatActivity implements View.OnClickListener {
    EditText useradmin,passwordadmin;
    Button submitadmin;
    String  useradmin1,passwordadmin1;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacherlogin);
        useradmin=findViewById(R.id.adminuser);
        passwordadmin=findViewById(R.id.adminpass);
        submitadmin=findViewById(R.id.button2);
        
        submitadmin.setOnClickListener(this);
    }
    public void onClick(View view)
    {
        if(view==submitadmin)
        {   useradmin1=useradmin.getText().toString();
            passwordadmin1=passwordadmin.getText().toString();
            if(useradmin1.equals("admin")&&passwordadmin1.equals("password"))
            {
                Intent myintent=new Intent(view.getContext(),teacherlogin.class);
                startActivity(myintent);
            }
            else
            {
                Toast.makeText(view.getContext(),useradmin1+"/n"+passwordadmin1,Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
