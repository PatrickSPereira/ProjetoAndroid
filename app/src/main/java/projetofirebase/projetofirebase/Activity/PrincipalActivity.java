package projetofirebase.projetofirebase.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;

import projetofirebase.projetofirebase.DAO.ConfiguracaoFirebase;
import projetofirebase.projetofirebase.R;

public class PrincipalActivity extends AppCompatActivity implements SensorEventListener {

    private FirebaseAuth usuarioFirebse;
    private Button btnAddProduto;
    private Button btnVerProduto;
    private LinearLayout ln;
    private SensorManager sm;
    private Sensor sensor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        usuarioFirebse = ConfiguracaoFirebase.getFirebaseAutenticacao();

        btnAddProduto = findViewById(R.id.btnAddProduto);
        btnVerProduto = findViewById(R.id.btnVerProduto);

        btnAddProduto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                addProduto();
            }
        });

        btnVerProduto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                verProduto();
            }
        });

        ln = findViewById(R.id.activityPrincipal);
        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensor = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sm.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void deslogarUsuario(){
        usuarioFirebse.signOut();
        Intent intent = new Intent(PrincipalActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void addProduto(){

        Intent intent = new Intent(PrincipalActivity.this, CadastroProdutosActivity.class);
        startActivity(intent);
        finish();
    }

    private void verProduto(){

        Intent intent = new Intent(PrincipalActivity.this, ProdutosActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        String texto = String.valueOf(event.values[0]);
        float valor = Float.parseFloat(texto);

        if (valor <= 10){
            btnAddProduto.setBackgroundColor(Color.BLUE);
            btnVerProduto.setBackgroundColor(Color.BLUE);
        }else{
            btnAddProduto.setBackgroundColor(Color.WHITE);
            btnVerProduto.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
