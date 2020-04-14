package com.example.attendance;

        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;

        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.AuthResult;
        import com.google.firebase.auth.FirebaseAuth;

public class teacherlogin extends AppCompatActivity{
    EditText useradmin,passwordadmin;
    Button submitadmin;
    String  useradmin1,passwordadmin1;
    FirebaseAuth mAuth;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacherlogin);
        useradmin=findViewById(R.id.teacheruser);
        passwordadmin=findViewById(R.id.teacherpass);
        submitadmin=findViewById(R.id.button2);
        mAuth = FirebaseAuth.getInstance();
        submitadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                useradmin1=useradmin.getText().toString();
                passwordadmin1=passwordadmin.getText().toString();
                mAuth.signInWithEmailAndPassword(useradmin1,passwordadmin1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(teacherlogin.this, "Sign In successfull!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(teacherlogin.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
