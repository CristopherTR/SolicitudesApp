package com.torres.cristopher.solicitudesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView solicitudesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        solicitudesList = findViewById(R.id.recyclerview);
        solicitudesList.setLayoutManager(new LinearLayoutManager(this));
        solicitudesList.setAdapter(new SolicitudesAdapter());
        initialize();

    }
    private void initialize() {
        ApiService service = ApiServiceGenerator.createService(ApiService.class);
        Call<List<Solicitud>> call = service.getSolicitudes();

        call.enqueue(new Call.Callback<List<Solicitud>>() {
            @Override
            public void onResponse(Call<List<Solicitud>> call, Response<List<Solicitud>> response) {
                try {
                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);
                    if (response.isSuccessful()) {
                        List<Solicitud> solicitudes = response.body();
                        Log.d(TAG, "productos: " + solicitudes);

                        SolicitudesAdapter adapter = (SolicitudesAdapter) solicitudesList.getAdapter();
                        adapter.setProductos(solicitudes);
                        adapter.notifyDataSetChanged();
                    }else{
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }
                }catch (Throwable t){
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                        }catch (Throwable x){}
                }
            }
            @Override
            public void onFailure(Call<List<Solicitud>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
