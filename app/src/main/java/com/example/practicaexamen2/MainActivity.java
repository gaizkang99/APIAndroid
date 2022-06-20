package com.example.practicaexamen2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.practicaexamen2.remote.Conexion;
import com.example.practicaexamen2.utils.Usuario;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private final String api = "c9938eec8191e9bc4060a002826fe64a881daa30ce41fad639ead880b269dcfb";
    private EditText nombre;
    private EditText id;
    private TextView usuario;
    private Button get;
    private Button post;
    private Conexion conexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombre = findViewById(R.id.nombre_persona);
        id = findViewById(R.id.id_persona);
        usuario = findViewById(R.id.datos);
        get = findViewById(R.id.get_button);
        post = findViewById(R.id.post_button);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {

            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {

                Request newRequest  = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + api)
                        .build();
                return chain.proceed(newRequest);

            }

        }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("https://gorest.co.in/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        conexion = retrofit.create(Conexion.class);

        post.setOnClickListener(v -> inscribirUsuario());
        get.setOnClickListener(v -> consultarUsuario());
    }

    public void inscribirUsuario(){
        String name = nombre.getText().toString();
        String id_user = id.getText().toString();

        Call<Usuario> user = conexion.postUsuario(id_user, new Usuario( name, "2022-03-09T00:00:00.000+05:30", "pending"));

        user.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    Log.i("msg", "Success");
                }
                Log.i("msg", String.format("%d",response.code()));
            }
            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {

                Log.i("msg", "Fallo en el metodo Post");
            }
        });
    }

    public void consultarUsuario(){
        String id_user = id.getText().toString();
        Call<List<Usuario>> users = conexion.getUsuario(id_user);
        users.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if (!response.isSuccessful()) {
                    usuario.setText("Response code: " + call.request().url().toString());
                    return ;
                }else {
                    List<Usuario> posts = response.body();
                    String text = "";
                    for (Usuario post : posts) {
                        text += "id: " + post.getUser_id() + "\n";
                        text += "title: " + post.getTitle() + "\n";
                        text += "Date: " + post.getDue_on() + "\n";
                    }
                    usuario.setText(text);
                }
            }
            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                usuario.setText(t.getMessage());
            }
        });
    }
}

