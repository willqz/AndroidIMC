package co.tiagoaguiar.codelab.myapplication;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import javax.xml.transform.Result;

public class ImcActivity extends AppCompatActivity {

    private EditText txtPeso;
    private EditText txtAltura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imc);

        txtPeso = findViewById(R.id.txtPeso);
        txtAltura = findViewById(R.id.txtAltura);
        
    }

    public void calcImc(View view) {
        if(!this.validate()){
            Toast.makeText(ImcActivity.this, R.string.imc_form_invalid, Toast.LENGTH_LONG).show();
            return;
        }

        int peso = Integer.parseInt(txtPeso.getText().toString());
        int altura = Integer.parseInt(txtAltura.getText().toString());

        double imc = calcularImc(peso, altura);
        Log.d("TESTE", "IMC: " + imc);

        int imcResponseId = imcResponse(imc);

        //Toast.makeText(ImcActivity.this,imcResponId, Toast.LENGTH_LONG).show();

        AlertDialog dialog = new AlertDialog.Builder(ImcActivity.this)
                .setTitle(getString(R.string.imcResponse, imc))
                .setMessage(imcResponseId)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();
        dialog.show();

        // ESCONDERO TECLADO
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(txtPeso.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(txtAltura.getWindowToken(), 0);
    }

    @StringRes
    private int imcResponse(double imc){
        if (imc < 15){
          return R.string.imc_severamente_acima;
        } else if (imc < 16) {
            return R.string.imc_muito_abaixo;
        } else if (imc < 18.5) {
            return R.string.imc_abaixo;
        } else if (imc < 25) {
            return R.string.imc_normal;
        } else if (imc < 30) {
            return R.string.imc_acima;
        } else if (imc < 35) {
            return R.string.imc_severamente_acima;
        } else if (imc < 40) {
            return R.string.imc_severamente_obeso;
        } else {
            return R.string.imc_extremamente_obeso;
        }
    }

    private double calcularImc(int peso, int altura){
        double alturaInMetros = (double)altura / 100;
        return peso / (alturaInMetros * alturaInMetros);
    }

    private boolean validate() {
        if (txtPeso.getText().toString().startsWith("0") || txtAltura.getText().toString().startsWith("0")
                || txtPeso.getText().toString().isEmpty() || txtAltura.getText().toString().isEmpty()){
            return false;
        }
        return true;
    }
}