package firebase.androidinfnet.info.appcadastrofirebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import firebase.androidinfnet.info.appcadastrofirebase.adapter.UserRecyclerAdapter;
import firebase.androidinfnet.info.appcadastrofirebase.adapter.UserViewHolder;

public class VisualizarUsuariosActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    private UserRecyclerAdapter adapter;
    private DatabaseReference firebase;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_usuarios);

        // Displaying toolbar icon
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Usuários Registrados");
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        // AdBanner --Anuncios
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        // referencia ao nodo
        firebase = FirebaseDatabase.getInstance().getReference().child("users");


        adapter = new UserRecyclerAdapter(User.class, android.R.layout.two_line_list_item, UserViewHolder.class, firebase);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        adapter.cleanup();
    }
}
