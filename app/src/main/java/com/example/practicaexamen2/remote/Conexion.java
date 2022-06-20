package com.example.practicaexamen2.remote;



import com.example.practicaexamen2.utils.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Conexion {

    @GET("public/v2/users/{user_id}/todos")
    public Call<List<Usuario>> getUsuario(@Path("user_id") String user_id);

    @POST("public/v2/users/{user_id}/todos")
    Call<Usuario> postUsuario(@Path("user_id") String user_id, @Body Usuario body);
}
