package com.unitins.quadro.quadrodehorarios.views.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.unitins.quadro.quadrodehorarios.R;
import com.unitins.quadro.quadrodehorarios.controllers.AlocacaoSalaC;
import com.unitins.quadro.quadrodehorarios.controllers.OfertaC;
import com.unitins.quadro.quadrodehorarios.views.fragments.FragmentConsultas;
import com.unitins.quadro.quadrodehorarios.views.fragments.FragmentHome;
import com.unitins.quadro.quadrodehorarios.views.fragments.FragmentLocalizar;
import com.unitins.quadro.quadrodehorarios.views.fragments.FragmentNews;
import com.unitins.quadro.quadrodehorarios.views.fragments.FragmentSair;
import com.unitins.quadro.quadrodehorarios.views.fragments.FragmentSobre;

import com.unitins.quadro.quadrodehorarios.services.Preferencias;


import java.util.ArrayList;
import java.util.List;

public class Principal extends AppCompatActivity {

    //referencias visuais
    private TabLayout vrTablayout;
    private ViewPager vrViewpager;

    ViewPagerAdapter vpAdapter;

    private int[] vrTabicons = {
            R.drawable.home,
            R.drawable.news,
            R.drawable.consulta,
            R.drawable.localizar,
            R.drawable.sobre,
            R.drawable.sair
    };

    private AlocacaoSalaC findAlocacao;
    private OfertaC findOferta;

    private int resultItent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        findAlocacao = new AlocacaoSalaC(getApplicationContext());
        findOferta = new OfertaC(getApplicationContext());

        System.out.println(findOferta.listar());

        //referencias visuais adicionadas ao controlador da activity
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name)+" - UNITINS");

        vrViewpager = (ViewPager) findViewById(R.id.principal_viewpager);

        //referencia do banco
        FragmentConsultas.validaBanco(findAlocacao,findOferta);

        //adicionando conteudo a viewpager
        setupViewPager(vrViewpager);

        vrTablayout = (TabLayout) findViewById(R.id.principal_tabs);
        vrTablayout.setupWithViewPager(vrViewpager);

        //adicionando icones
        setupTabIcons();

    }

    //metodo que adicionará os icones as abas
    private void setupTabIcons() {
        vrTablayout.getTabAt(0).setIcon(vrTabicons[0]);
        vrTablayout.getTabAt(1).setIcon(vrTabicons[1]);
        vrTablayout.getTabAt(2).setIcon(vrTabicons[2]);
        vrTablayout.getTabAt(3).setIcon(vrTabicons[3]);
        vrTablayout.getTabAt(4).setIcon(vrTabicons[4]);
        vrTablayout.getTabAt(5).setIcon(vrTabicons[5]);
    }

    private void setupViewPager(ViewPager viewPager) {
        vpAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        vpAdapter.addFrag(new FragmentHome(), "Início");
        vpAdapter.addFrag(new FragmentNews(), "News");
        vpAdapter.addFrag(new FragmentConsultas(), "Consultas");
        vpAdapter.addFrag(new FragmentLocalizar(), "Localizar");
        vpAdapter.addFrag(new FragmentSobre(), "Sobre");
        vpAdapter.addFrag(new FragmentSair(), "Sair");
        vrViewpager.setOffscreenPageLimit(4);
        viewPager.setAdapter(vpAdapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            //return mFragmentTitleList.get(position);
            return null;
        }
    }

    @Override
    public void onActivityResult(int codigo, int resultado, Intent dados) {
        try {
            if(resultado == Activity.RESULT_OK){
                String mensagem;
                Boolean voltar = dados.getExtras().getBoolean("voltar");
                String tela = dados.getExtras().getString("tela");

                //verifica se esta voltando pelo backbutton
                if(tela.contains("PesquisarHorarios")){
                    if(voltar){
                        mensagem = "Lista inalterada.";
                    }
                    else{
                        Boolean pesquisa = dados.getExtras().getBoolean("pesquisa");
                        int curso = dados.getExtras().getInt("curso");
                        int semestre = dados.getExtras().getInt("semestre");
                        int periodo = dados.getExtras().getInt("periodo");
                        int sala = dados.getExtras().getInt("sala");
                        int turno = dados.getExtras().getInt("turno");
                        int dia = dados.getExtras().getInt("dia");

                        //verifica se passou os parametros padrão
                        if(curso == 0 && semestre == 0 && periodo == -1 && sala == 0 && turno == 0 && dia == 0){
                            mensagem = "Lista inalterada: Parametros vazios passados.";
                        }
                        else{
                            //altera as preferencias
                            if(curso != 0){
                                Preferencias.setInt(this,"curso",curso);
                                Preferencias.setBoolean(this, "salvo",true);
                            }

                            if(semestre != 0){
                                Preferencias.setInt(this,"semestre",semestre);
                                Preferencias.setBoolean(this, "salvo",true);
                            }
                            mensagem = "Lista alterada de acordo com os filtros selecionados.";
                            //recarrega o conteudo da fragment
                            FragmentConsultas fragment = (FragmentConsultas) vpAdapter.getItem(2);
                            fragment.carregarListaExterno(pesquisa,curso,semestre,periodo,sala,turno,dia);
                        }
                    }
                    Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
                }
                if(tela.contains("NoticiaDetalhes")){
                    //codigo aqui
                }
                if(tela.contains("HorarioDetalhes")){
                    //codigo aqui
                }

            }
        } catch (Exception e) {
            Toast.makeText(this.getApplicationContext(), "Atenção: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

}