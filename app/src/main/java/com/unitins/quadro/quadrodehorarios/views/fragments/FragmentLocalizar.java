package com.unitins.quadro.quadrodehorarios.views.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.unitins.quadro.quadrodehorarios.R;
import com.unitins.quadro.quadrodehorarios.controllers.UnidadeC;
import com.unitins.quadro.quadrodehorarios.models.Unidade;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentLocalizar extends Fragment implements OnMapReadyCallback, View.OnClickListener {

    //componente visual
    private Spinner comboUnidade = null;
    private SupportMapFragment mapaFrag = null;
    private Button rota = null;
    private ImageButton mais = null;
    private ImageButton menos = null;

    //adapters dos spinners
    private ArrayAdapter<String> unidadeAdapter;

    //objetos unidade
    private UnidadeC cUnidade = null;
    private ArrayList<Unidade> listaUnidade = null;
    private ArrayList<String> nomesUnidade = null;
    private ArrayList<Unidade> auxUnidades = null;
    private int posUnidade;

    //mapa
    private GoogleMap mapa = null;
    private LocationManager locationManager;
    private float zoom;

    private boolean mostrou = false;
    private boolean permissao = false;

    public FragmentLocalizar() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_localizar, container, false);
        //inicializa o mapa
        MapsInitializer.initialize(this.getActivity());
        //Location manager para pegar a localização
        locationManager = (LocationManager) this.getContext().getSystemService(this.getContext().LOCATION_SERVICE);

        //cria o controler
        cUnidade = new UnidadeC(this.getActivity());
        //cria a lista
        listaUnidade = new ArrayList<>();
        nomesUnidade = new ArrayList<>();

        comboUnidade = (Spinner) result.findViewById(R.id.mapa_select);
        rota = (Button) result.findViewById(R.id.mapa_btnrota);
        mais = (ImageButton) result.findViewById(R.id.mapa_plus);
        menos = (ImageButton) result.findViewById(R.id.mapa_minus);

        //carrega as unidades e o adapter
        carregarUnidades();
        getAdapUnidade();

        //como está numa fragment, enntão é necessário outro processo
        FragmentManager fm = getActivity().getSupportFragmentManager();/// getChildFragmentManager();
        mapaFrag = (SupportMapFragment) fm.findFragmentById(R.id.mapa_fragment);
        if (mapaFrag == null) {
            //cria uma nova instancia, se não ele não irá pegar a referencia no getMapSync
            mapaFrag = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.mapa_fragment, mapaFrag).commit();
        }
        mapaFrag.getMapAsync(this);

        //zoom minimo permitido
        zoom = 2.0f;
        //seta o clicklistener
        rota.setOnClickListener(this);
        mais.setOnClickListener(this);
        menos.setOnClickListener(this);
        //desabilita por padrão
        rota.setEnabled(false);
        mais.setEnabled(false);
        mais.setImageResource(R.drawable.zoomind);
        menos.setEnabled(false);
        menos.setImageResource(R.drawable.zoomoutd);

        //listeners
        comboUnidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView cursoD, View v, int posicao, long id) {
                posUnidade = posicao;
                if (posicao != 0){
                    rota.setTextColor(getResources().getColor(R.color.letrabranca));
                    rota.setEnabled(true);
                    mais.setImageResource(R.drawable.zoomin);
                    menos.setImageResource(R.drawable.zoomout);
                }
                else{
                    rota.setTextColor(getResources().getColor(R.color.letracinza));
                    rota.setEnabled(false);
                    mais.setImageResource(R.drawable.zoomind);
                    menos.setImageResource(R.drawable.zoomoutd);
                }
                colocaPino(posUnidade);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return result;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            mostrou = true;
            getDeviceLocation();
            if (mostrou && auxUnidades.isEmpty()) {
                listaUnidade = new ArrayList<>();
                nomesUnidade = new ArrayList<>();
                //carrega as unidades e o adapter
                carregarUnidades();
                getAdapUnidade();
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;
        mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mapa.setMyLocationEnabled(false);
    }

    @Override
    public void onClick(View v) {
        //evento de click do rotas
        switch (v.getId()) {
            case R.id.mapa_btnrota:
                mostrarRota();
                break;
            case R.id.mapa_plus:
                aumentaZoom();
                break;
            case R.id.mapa_minus:
                diminuiZoom();
                break;
        }
    }

    //coloca o pino no mapa
    public void colocaPino(int p) {
        LatLng uni = null;
        if (mapa != null)
            mapa.clear();
        if (p == 0 && mapa != null) {
            //se for zerado ele reseta o mapa
            if (mapa != null) {
                uni = new LatLng(listaUnidade.get(p).getLatitude(), listaUnidade.get(p).getLongitude());
                mapa.addMarker(new MarkerOptions().position(uni).title(listaUnidade.get(p).getNome()));
                mapa.moveCamera(CameraUpdateFactory.newLatLng(uni));
                zoom = 2.0f;
                mapa.animateCamera(CameraUpdateFactory.zoomTo(zoom));
                mapa.clear();
                rota.setEnabled(false);
                mais.setEnabled(false);
                menos.setEnabled(false);
            }
        } else {
            //se não ele coloca a posição e dá o zoom
            if (mapa != null) {
                uni = new LatLng(listaUnidade.get(p).getLatitude(), listaUnidade.get(p).getLongitude());
                //faz um marcador e pega a referencia ao adicionar para já mostrar o nome no pino
                Marker marcador = mapa.addMarker(new MarkerOptions().position(uni).title(listaUnidade.get(p).getNome()));
                mapa.moveCamera(CameraUpdateFactory.newLatLng(uni));
                zoom = 12.0f;
                mapa.animateCamera(CameraUpdateFactory.zoomTo(12.0f));
                marcador.showInfoWindow();
                rota.setEnabled(true);
                mais.setEnabled(true);
                menos.setEnabled(true);
            }
        }
    }

    //metodo que carrega as unidades do banco
    private void carregarUnidades() {
        Unidade unidade = new Unidade();
        unidade.setId(0);
        unidade.setNome("Clique aqui para selecionar o Câmpus.");
        listaUnidade.add(unidade);
        nomesUnidade.add(unidade.getNome());
        auxUnidades = cUnidade.listar();
        //só carrega se nao vier vazio
        if (!auxUnidades.isEmpty()) {
            //faz o foreach
            for (Unidade u : auxUnidades) {
                listaUnidade.add(u);
                nomesUnidade.add(u.getNome());
            }
        }
    }

    //metodos que criam os adapters
    private void getAdapUnidade() {
        //Cria um ArrayAdapter usando um padrão de layout da classe R do android, passando o ArrayList nomes
        unidadeAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, nomesUnidade);
        unidadeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        comboUnidade.setAdapter(unidadeAdapter);
        unidadeAdapter.notifyDataSetChanged();
        posUnidade = 0;
    }

    private void aumentaZoom() {
        if (mapa != null)
            mapa.animateCamera(CameraUpdateFactory.zoomIn());
    }

    private void diminuiZoom() {
        if (mapa != null)
            mapa.animateCamera(CameraUpdateFactory.zoomOut());

    }

    //metodo que chamará a API do google para chamar
    @SuppressLint("MissingPermission")
    private void mostrarRota() {
        if (mapa != null && !listaUnidade.isEmpty()) {
            if (isLocationEnabled() || permissao == true) {
                //coloca o destino numa string
                String destino = listaUnidade.get(posUnidade).getLatitude() + ", " + listaUnidade.get(posUnidade).getLongitude();
                //chama a api do google no modo de rotas
                Uri.Builder directionsBuilder = new Uri.Builder()
                        .scheme("https")
                        .authority("www.google.com")
                        .appendPath("maps")
                        .appendPath("dir")
                        .appendPath("")
                        .appendQueryParameter("api", "1")
                        .appendQueryParameter("destination", destino);
                //inicia a API
                startActivity(new Intent(Intent.ACTION_VIEW, directionsBuilder.build()));

            } else {
                showAlert();
            }
        }
    }

    //verifica o localizador ativado
    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    //verifica as permissãos
    private void getDeviceLocation() {
        if (ContextCompat.checkSelfPermission(this.getActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            permissao = true;
        } else {
            ActivityCompat.requestPermissions(this.getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
            permissao = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,@NonNull String permissions[],@NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permissao = true;
                }
            }
        }
    }

    //alerta caso esteja desabilitado
    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this.getActivity());
        dialog.setTitle("Habilitar Localizador")
                .setMessage("Suas Configurações de Localização estão definidas desabilitada.\n" +
                        "Ative o Localizador para usar este recurso do aplicativo.")
                .setPositiveButton("Configurações", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }

}

