package site.fishstyletrade.fishapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText editText_user;
    EditText editText_password;
    Button btnLogIn;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editText_user = findViewById(R.id.loginScreen_user);
        editText_password = findViewById(R.id.loginScreen_password);
        btnLogIn = findViewById(R.id.loginScreen_button);

        intent = new Intent(this, MainActivity.class);
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    public void login() {
        if (editText_user.getText().toString().equals("admin") && editText_password.getText().toString().equals("admin0202")) {
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
//            editText_user.setError("");
//            editText_password.setError("");
        }
    }

}