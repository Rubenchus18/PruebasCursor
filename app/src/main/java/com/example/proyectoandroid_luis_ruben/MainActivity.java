package com.example.proyectoandroid_luis_ruben;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //VARIABLES
    EditText nombreUsuario, contrasenaUsuario;
    Button inicioSesion, registro;
    String nombre, contraseña;
    //ARRAYLIST USUARIOS
    ArrayList<Usuario>listaUsuarios=new ArrayList<Usuario>();
    //USUARIOS POR DEFECTO
    Usuario usuario1=new Usuario("Yolanda", "1234");
    Usuario usuario2=new Usuario("Ruben", "abcd");
    Usuario usuario3=new Usuario("Luis", "Luis");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //ANIDACION DE USUARIOS A LA LISTA DE USUARIOS
        listaUsuarios.add(usuario1);
        listaUsuarios.add(usuario2);
        listaUsuarios.add(usuario3);
        //ATRIBUTOS
        nombreUsuario=findViewById(R.id.edittextusuario);
        contrasenaUsuario=findViewById(R.id.editTextTextContraseña);
        inicioSesion=findViewById(R.id.buttonInciarSesion);
        registro=findViewById(R.id.buttonRegistarse);
        //CLICK LISTENERS DE LOS BOTONES
        //INICIO DE SESION
        inicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerValores();
                comprobarNulos(view);
            }//onClickInicioSesion
        });
        //REGISTRO
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerValores();
                crearUsuario(view);
            }//onClickInicioSesion

        });;;

    }//onCreate

    public void comprobarNulos(View view){

        if(nombre.isEmpty() || contraseña.isEmpty()){
            if(nombre.isEmpty()){
                Toast.makeText(getApplicationContext(), "CAMPO DE USUARIO OBLIGATORIO", Toast.LENGTH_LONG).show();
            }else if(contraseña.isEmpty()){
                Toast.makeText(getApplicationContext(), "CAMPO DE CONTRASEÑA OBLIGATORIO", Toast.LENGTH_LONG).show();
            }//else if
        }else if(nombre.isEmpty() && contraseña.isEmpty()){
            Toast.makeText(getApplicationContext(), "INTRODUZCA UN INICIO DE SESION", Toast.LENGTH_LONG).show();
        }else{
            registrarUsuario(view);
        }//else
    }//comprobarNulos
    public void crearUsuario(View view){
        Usuario usuario=new Usuario();
        boolean existe=false;

        if(nombre.isEmpty() || contraseña.isEmpty()){
            if(nombre.isEmpty()){
                Toast.makeText(getApplicationContext(), "CAMPO DE USUARIO OBLIGATORIO", Toast.LENGTH_LONG).show();
            }else if(contraseña.isEmpty()){
                Toast.makeText(getApplicationContext(), "CAMPO DE CONTRASEÑA OBLIGATORIO", Toast.LENGTH_LONG).show();
            }//else if
        }
        for(int i=0; i<listaUsuarios.size(); i++) {
            if (nombre.equals(listaUsuarios.get(i).getNombre()) && contraseña.equals(listaUsuarios.get(i).getContraseña())) {
                existe = true;

            }
        }
        if(existe){
                    Toast.makeText(getApplicationContext(), "Este usuario o contraseña ya existe", Toast.LENGTH_LONG).show();
        }else if(existe==false){
                    //CREAMOS EL USUARIO SI PASO TODO EL RECONOMIENTO PREVIO
                    String nombre=String.valueOf(nombreUsuario.getText());
                    String contraseña=String.valueOf(contrasenaUsuario.getText());
                    usuario=new Usuario(nombre, contraseña);
                    listaUsuarios.add(usuario);
                    nombreUsuario.setText("");
                    contrasenaUsuario.setText("");
                    //SE LO DECIMOS AL USUARIO TAMBIEN
                    Toast.makeText(getApplicationContext(), "USUARIO CREADO CORRECTAMENTE", Toast.LENGTH_LONG).show();
                }//else



    }//crearUsuario
    public void registrarUsuario(View view){

        for(int i=0; i<listaUsuarios.size(); i++) {
            if (!listaUsuarios.get(i).getNombre().equals(nombre) || !listaUsuarios.get(i).getContraseña().equals(contraseña)) {
                if (listaUsuarios.get(i).getNombre().equals(nombre)) {
                    Toast.makeText(getApplicationContext(), "NOMBRE DE USUARIO INCORRECTO", Toast.LENGTH_LONG).show();
                } else if (listaUsuarios.get(i).getContraseña().equals(contraseña)) {
                    Toast.makeText(getApplicationContext(), "CONTRASEÑA INCORRECTA", Toast.LENGTH_LONG).show();
                } else if (!listaUsuarios.get(i).getNombre().equals(nombre) && !listaUsuarios.get(i).getContraseña().equals(contraseña)) {
                    Toast.makeText(getApplicationContext(), "INICIO DE SESION INCORRECTO", Toast.LENGTH_LONG).show();
                }//else if
            }else {
                siguienteActividad(view);
            }//else
        }//for

    }//registrarUsuario

    public void obtenerValores(){
        nombre=nombreUsuario.getText().toString().trim();
        contraseña=contrasenaUsuario.getText().toString().trim();
        //Sin este metodo la app da error, puesto que al intetar recuperar los valores antes de los metodos on click por ende antes de inicializarlos puede provocar nulos
    }//obtener valores

    public void siguienteActividad(View view){
        Intent siguienteActividad = new Intent(this, Informacion.class);
        siguienteActividad.putExtra("usuario", nombre);  // Pasar el nombre de usuario
        siguienteActividad.putExtra("contraseña", contraseña); // Pasar la contraseña
        guardarDatosUsuario(nombre, contraseña);
        startActivity(siguienteActividad);
    }//siguiente actividad
    public void guardarDatosUsuario(String usuario, String contraseña) {
        SharedPreferences sharedPreferences = getSharedPreferences("DatosUsuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("usuario", usuario);
        editor.putString("contraseña", contraseña);
        editor.apply();
    }
}//MainActivity
