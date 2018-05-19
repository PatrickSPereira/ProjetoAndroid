package projetofirebase.projetofirebase.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import projetofirebase.projetofirebase.Adapter.ProdutosAdapter;
import projetofirebase.projetofirebase.DAO.ConfiguracaoFirebase;
import projetofirebase.projetofirebase.Model.Produtos;
import projetofirebase.projetofirebase.R;

public class ProdutosActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<Produtos> adapter;
    private ArrayList<Produtos> produtos;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListenerProdutos;
    private Button btnVoltarTelaPrincipal;
    private AlertDialog alerta;
    private Produtos produtosExcluir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);

        produtos = new ArrayList<>();
        listView = findViewById(R.id.listViewProdutos);
        adapter = new ProdutosAdapter(this, produtos);

        listView.setAdapter(adapter);

        firebase = ConfiguracaoFirebase.getFirebase().child("addprodutos");

        valueEventListenerProdutos = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                produtos.clear();

                for(DataSnapshot dados : dataSnapshot.getChildren()){
                    Produtos produtosNovo = dados.getValue(Produtos.class);
                    produtos.add(produtosNovo);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        btnVoltarTelaPrincipal = findViewById(R.id.btnVoltarTelaInicial2);
        btnVoltarTelaPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voltarTelaInicial();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                produtosExcluir = adapter.getItem(position);

                //cria o gerador do alerteDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(ProdutosActivity.this);

                //Define o titulo
                builder.setTitle("Confirma exclusão?");

                //define uma mensagem
                builder.setMessage("Você deseja excluir " + produtosExcluir.getNome().toString() + " ?");

                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        firebase = ConfiguracaoFirebase.getFirebase().child("addprodutos");
                        firebase.child(produtosExcluir.getNome().toString()).removeValue();

                        Toast.makeText(ProdutosActivity.this, "Exclusão efetuada!", Toast.LENGTH_LONG).show();
                    }
                });

                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ProdutosActivity.this, "Exclusão Cancelada!", Toast.LENGTH_LONG).show();
                    }
                });

                //criar o alertDialog
                alerta = builder.create();

                alerta.show();
            }
        });
    }

    private void voltarTelaInicial(){
        Intent intent = new Intent(ProdutosActivity.this, PrincipalActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListenerProdutos);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebase.addValueEventListener(valueEventListenerProdutos);
    }
}
