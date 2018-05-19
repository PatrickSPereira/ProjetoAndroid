package projetofirebase.projetofirebase.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import projetofirebase.projetofirebase.Model.Produtos;
import projetofirebase.projetofirebase.R;

public class ProdutosAdapter extends ArrayAdapter<Produtos> {

    private ArrayList<Produtos> produto;
    private Context context;

    public ProdutosAdapter(Context c, ArrayList<Produtos> objects) {
        super(c, 0, objects);
        this.context = c;
        this.produto = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view = null;

        if (produto != null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.lista_produtos, parent, false);

            TextView txtViewNome = view.findViewById(R.id.txtViewNome);
            TextView txtViewValor = view.findViewById(R.id.txtViewValor);

            Produtos produtos2 = produto.get(position);
            txtViewNome.setText(produtos2.getNome());
            txtViewValor.setText(produtos2.getValor().toString());
        }
        return view;
    }


}
