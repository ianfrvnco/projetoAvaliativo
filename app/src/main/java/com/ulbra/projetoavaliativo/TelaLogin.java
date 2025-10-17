package com.ulbra.projetoavaliativo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class TelaLogin extends AppCompatActivity {

    TextView txtCadastro;
    Button btnEntrar;
    EditText edtEmail, edtSenha;
    DBHelper db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.tela_login);

        db = new DBHelper(this);
        txtCadastro = findViewById(R.id.txtCadastro);
        btnEntrar = findViewById(R.id.btnEntrar);
        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);

        txtCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaLogin.this, TelaCadastro.class);
                startActivity(intent);
            }
        });

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login = edtEmail.getText().toString();
                String senha = edtSenha.getText().toString();
              //  Toast.makeText(TelaLogin.this, "Email:"+login +" SEnha"+senha, Toast.LENGTH_SHORT).show();
               if (login.isEmpty() || senha.isEmpty()) {
                    Toast.makeText(TelaLogin.this, "Usuário ou Senha não inserido, tente novamente", Toast.LENGTH_SHORT).show();
                } else {
                    String res = db.validarLogin(login, senha);

                    if (res.equals("OK")) {
                        Toast.makeText(TelaLogin.this, "Login OK!!", Toast.LENGTH_LONG).show();
                       abrirMenu();
                    } else {
                        Toast.makeText(TelaLogin.this, "Login ou Senha errado(s)!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
    public void abrirMenu(){
        Intent intent = new Intent(TelaLogin.this, TelaMenu.class);
        startActivity(intent);
    }
}
