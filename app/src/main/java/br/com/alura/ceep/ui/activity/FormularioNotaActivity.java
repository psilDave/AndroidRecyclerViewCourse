package br.com.alura.ceep.ui.activity;

import static br.com.alura.ceep.ui.activity.ConstanteActivities.CHAVE_NOTA;
import static br.com.alura.ceep.ui.activity.ConstanteActivities.CHAVE_POSICAO;
import static br.com.alura.ceep.ui.activity.ConstanteActivities.POSICAO_INVALIDA;
import static br.com.alura.ceep.ui.activity.ConstanteActivities.TITULO_ALTERA_NOTA;
import static br.com.alura.ceep.ui.activity.ConstanteActivities.TITULO_INSERE_NOTA;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import br.com.alura.ceep.R;
import br.com.alura.ceep.model.Nota;

public class FormularioNotaActivity extends AppCompatActivity {



    private int posicaoRecebida = POSICAO_INVALIDA;
    private TextView titulo;
    private TextView descricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_nota);
        inicializaCampos();
        setTitle(TITULO_INSERE_NOTA);
        Intent dadosRecebidos = getIntent();
        if (dadosRecebidos.hasExtra(CHAVE_NOTA)) {
            setTitle(TITULO_ALTERA_NOTA);
            Nota notaRecebida = (Nota) dadosRecebidos.getSerializableExtra(CHAVE_NOTA);
            posicaoRecebida = dadosRecebidos.getIntExtra(CHAVE_POSICAO, POSICAO_INVALIDA);
            mostra(notaRecebida);
        }

    }

    private void mostra(Nota notaRecebida) {
        titulo.setText(notaRecebida.getTitulo());
        descricao.setText(notaRecebida.getDescricao());
    }

    private void inicializaCampos() {
        titulo = findViewById(R.id.formulario_nota_titulo);
        descricao = findViewById(R.id.formulario_nota_descricao);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario_nota_salva, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (oBotaoSalvarFoiClicado(item)) {
            Nota novaNota = criaNota();
            retornaNota(novaNota, posicaoRecebida);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void retornaNota(Nota novaNota, int posicaoRecebida) {
        Intent resultadoInsercao = new Intent();
        resultadoInsercao.putExtra(CHAVE_NOTA, novaNota);
        resultadoInsercao.putExtra(CHAVE_POSICAO, posicaoRecebida);
        setResult(Activity.RESULT_OK, resultadoInsercao);
    }

    @NonNull
    private Nota criaNota() {
        return new Nota(titulo.getText().toString(), descricao.getText().toString());
    }

    private boolean oBotaoSalvarFoiClicado(@NonNull MenuItem item) {
        return item.getItemId() == R.id.menu_formulario_nota_ic_salva;
    }
}