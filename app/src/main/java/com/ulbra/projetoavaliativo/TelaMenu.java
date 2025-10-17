package com.ulbra.projetoavaliativo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class TelaMenu extends AppCompatActivity {

    EditText edtNome, edtSalario, edtFilhos;
    RadioGroup rgOpcoes;
    RadioButton rbtnMasculino, rbtnFeminino;
    Button btnCalcular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.tela_menu);
        edtNome = findViewById(R.id.edtNome);
        edtSalario = findViewById(R.id.edtSalario);
        edtFilhos = findViewById(R.id.edtFilhos);
        rgOpcoes = findViewById(R.id.rgOpcoes);
        rbtnMasculino = findViewById(R.id.rbtnMasculino);
        rbtnFeminino = findViewById(R.id.rbtnFeminino);
        btnCalcular = findViewById(R.id.btnCalcular);

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = edtNome.getText().toString().trim();
                String salario = edtSalario.getText().toString().trim();
                String filhos = edtFilhos.getText().toString().trim();

                if (nome.isEmpty() || salario.isEmpty() || filhos.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
                    return;
                }

                double salarioBruto;
                int numeroFilhos;

                try {
                    salarioBruto = Double.parseDouble(salario);
                    numeroFilhos = Integer.parseInt(filhos);
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Salário e número de filhos devem ser numéricos.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (salarioBruto <= 0 || salarioBruto > 1_000_000) {
                    Toast.makeText(getApplicationContext(), "Salário deve ser entre 0 e 1.000.000.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (numeroFilhos < 0) {
                    Toast.makeText(getApplicationContext(), "Número de filhos não pode ser negativo.", Toast.LENGTH_SHORT).show();
                    return;
                }

                int selectedId = rgOpcoes.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    Toast.makeText(getApplicationContext(), "Selecione o sexo.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String tratamento = (selectedId == rbtnMasculino.getId()) ? "Sr." : "Sra.";

                double taxaINSS;
                if (salarioBruto <= 1212.00) {
                    taxaINSS = 0.075;
                } else if (salarioBruto <= 2427.35) {
                    taxaINSS = 0.09;
                } else if (salarioBruto <= 3641.03) {
                    taxaINSS = 0.12;
                } else {
                    taxaINSS = 0.14;
                }

                double descontoINSS = salarioBruto * taxaINSS;

                double taxaIR;
                if (salarioBruto <= 1903.98) {
                    taxaIR = 0.0;
                } else if (salarioBruto <= 2826.65) {
                    taxaIR = 0.075;
                } else if (salarioBruto <= 3751.05) {
                    taxaIR = 0.15;
                } else {
                    taxaIR = 0.225;
                }
                double descontoIR = salarioBruto * taxaIR;

                double salarioFamilia = (salarioBruto <= 1212.00) ? 56.47 * numeroFilhos : 0.0;

                double salarioLiquido = salarioBruto - descontoINSS - descontoIR + salarioFamilia;

                StringBuilder resultado = new StringBuilder();
                resultado.append(tratamento).append(" ").append(nome).append("\n\n");
                resultado.append("Salário Bruto: R$ ").append(String.format("%.2f", salarioBruto)).append("\n");
                resultado.append("Desconto INSS: R$ ").append(String.format("%.2f", descontoINSS)).append("\n");
                resultado.append("Desconto IR: R$ ").append(String.format("%.2f", descontoIR)).append("\n");
                if (salarioFamilia > 0) {
                    resultado.append("Salário-Família: R$ ").append(String.format("%.2f", salarioFamilia)).append("\n");
                }
                resultado.append("\nSalário Líquido: R$ ").append(String.format("%.2f", salarioLiquido));

                // Exibir em um AlertDialog ou TextView
                new AlertDialog.Builder(TelaMenu.this)
                        .setTitle("Resultado do Cálculo")
                        .setMessage(resultado.toString())
                        .setPositiveButton("OK", null)
                        .show();
            }
        });

,

    }
}
