package com.EscolhaSustentavel.pi.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.EscolhaSustentavel.pi.R;
import com.EscolhaSustentavel.pi.model.Produto;
import com.EscolhaSustentavel.pi.sql.DatabaseHelper;

import java.io.IOException;
import java.io.InputStream;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import static com.EscolhaSustentavel.pi.R.id.nestedScrollView;

public class EscolherComparacao extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String nameProd, descProd, compProd, timeProd, impactoProd, urlProd;
    private Double lat, lon;
    private int idProd;

    private TextView tvDesc, tvComp, tvTime, tvImpact, camp_desc2, camp_impact2, camp_comp2,camp_time2;
    private ImageView ivProd, im2;
    Spinner ListCateg;
    ArrayList Categorias;
    List<String> categorias;
    String nome;
    String ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_escolher_comparacao);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //recuperando os dados de composicao
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String txt = bundle.getString("txt");
        TextView txtResultado = (TextView) findViewById(R.id.camp_comp);
        txtResultado.setText(txt);

        //recuperando os dados de descrição
        String desc = bundle.getString("desc");
        TextView descr = (TextView) findViewById(R.id.camp_desc);
        descr.setText(desc);

        //recuperando os dados de impacto

        String impact = bundle.getString("impact");
        TextView impc = (TextView) findViewById(R.id.camp_impact);
        impc.setText(impact);

        //recuperando os dados de tempo de composicao


        String time = bundle.getString("time");
        TextView tempo = (TextView) findViewById(R.id.camp_time);
        tempo.setText(time);

        //recuperando o nome do produto
        String nomep = bundle.getString("prod_nome");
        nome=nomep;

        //recuperando a categoria do produto

        String categp = bundle.getString("produ_categ");




        //recuperando a url da imagem

        String foto = bundle.getString("prod_url");
        urlProd=foto;

        initView();
        setData();
        //setDataimg2();


        ListCateg.setOnItemSelectedListener(this);
        DatabaseHelper categ=new DatabaseHelper(this);
        categorias = new ArrayList<String>();

        categorias=categ.ListarCategorias(categp, nomep);



        ArrayAdapter <String> dataAdapter = new ArrayAdapter <String>(this, R.layout.spinner_item, categorias);

        // dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ListCateg.setAdapter(dataAdapter);
        ListCateg.setOnItemSelectedListener(this);


    }




    private void initView() {

        tvComp = (TextView) findViewById(R.id.camp_comp);
        tvTime = (TextView) findViewById(R.id.camp_time);
        tvImpact = (TextView) findViewById(R.id.camp_impact);
        ivProd = (ImageView) findViewById(R.id.im);
        tvDesc = (TextView) findViewById(R.id.camp_desc);
        ListCateg=(Spinner) findViewById(R.id.ListCateg);

        camp_desc2 = (TextView) findViewById(R.id.camp_desc2);
        camp_impact2 = (TextView) findViewById(R.id.camp_impact2);
        camp_comp2 = (TextView) findViewById(R.id.camp_comp2);
        camp_time2 = (TextView) findViewById(R.id.camp_time2);
        im2= (ImageView) findViewById(R.id.im2);
    }


    //capturando e setando a imagem pela url
    private void setData() {
        try {
            String nameFile = urlProd.substring(urlProd.lastIndexOf('/'));
            InputStream ims = getAssets().open(nameFile.substring(1));
            Drawable d = Drawable.createFromStream(ims, null);
            ivProd.setImageDrawable(d);
        } catch (IOException ignored) {
        }
    }

    //método que irá setar a imagem do segundo produto
    private void setDataimg2() {
        try {
            String nameFile = ft.substring(ft.lastIndexOf('/'));
            InputStream ims = getAssets().open(nameFile.substring(1));
            Drawable d = Drawable.createFromStream(ims, null);
            im2.setImageDrawable(d);
        } catch (IOException ignored) {
        }
    }



    //método que irá exibir as informações do produto após ele ser selecionado no spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {




        List<String> Produtos=new ArrayList<>();
        DatabaseHelper bd=new DatabaseHelper(this);
        String item = parent.getItemAtPosition(position).toString();
        String opcao = (String) ListCateg.getSelectedItem();
        Produtos=bd.ListarProduto(opcao, nome);
        camp_impact2.setText(Produtos.get(1));
        camp_time2.setText(Produtos.get(2));
        camp_comp2.setText(Produtos.get(3));
        camp_desc2.setText(Produtos.get(4));
        ft=Produtos.get(6);

        setDataimg2();



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
