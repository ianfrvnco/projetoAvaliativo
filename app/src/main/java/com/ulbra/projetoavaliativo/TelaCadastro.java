package com.ulbra.projetoavaliativo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TelaCadastro extends AppCompatActivity {

    EditText edtNome, edtEmail, edtSenha;
    Button btnCadastrar;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cadastro);

        db = new DBHelper(this);
        edtNome = findViewById(R.id.edtNome);
        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        btnCadastrar = findViewById(R.id.btnCadastrar);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = edtNome.getText().toString();
                String login = edtEmail.getText().toString();
                String senha = edtSenha.getText().toString();

                if (nome.isEmpty() || login.isEmpty() || senha.isEmpty()) {
                    Toast.makeText(TelaCadastro.this, "DADOS EM BRANCO", Toast.LENGTH_SHORT).show();
                } else {
                    long res = db.criarUtilizador(login, senha);
                    if (res > 0) {
                        Toast.makeText(TelaCadastro.this, "Registro OK", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(TelaCadastro.this, TelaLogin.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(TelaCadastro.this, "Erro ao registrar usuário!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}