package com.example.mi_album;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    private String galeria = "";
    private Integer cantidadFotos = 0;
    private String ar;
    private ArrayList<String> archivos;
    private Bitmap bitmap;

    private static int TAKE_PICTURE = 1;
    private static int SELECT_PICTURE = 2;
    private ImageView imagen;

    private Integer i = 0;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        String[] archivo;
        File dir = getExternalFilesDir(null);

        archivo = dir.list();

        archivos = new ArrayList<>(Arrays.asList(archivo));

        cantidadFotos = archivos.size();

        imagen = findViewById(R.id.imgVista);

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("CONTINUA", i);
        outState.putString("gal", galeria);


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        galeria = savedInstanceState.getString("gal");
        if (!"".equals(galeria)) {


            InputStream is;
            try {


                Uri selectedImage = Uri.parse(galeria);
                is = getContentResolver().openInputStream(selectedImage);
                BufferedInputStream bis = new BufferedInputStream(is);
                bitmap = BitmapFactory.decodeStream(bis);

                imagen.setImageBitmap(bitmap);


            } catch (FileNotFoundException e) {
            }

        } else {

            i = savedInstanceState.getInt("CONTINUA");
            mostrarimagen(i);
        }


    }


    public void acerdade(View v) {
        Intent i = new Intent(this, acercaDe.class);
        startActivity(i);
    }


    public void adelante(View v) {

        i++;

        if (i >= cantidadFotos) i = 0;

        mostrarimagen(i);
    }

    public void verfoto(View v) {
        intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent, SELECT_PICTURE);

    }

    public void tomarFoto(View v) {


        Calendar calendar = Calendar.getInstance();
        Integer year = calendar.get(Calendar.YEAR);
        Integer mes = calendar.get(Calendar.MONTH) + 1;
        Integer dia = calendar.get(Calendar.DAY_OF_MONTH);
        Integer hora = calendar.get(Calendar.HOUR_OF_DAY);
        Integer min = calendar.get(Calendar.MINUTE);
        Integer seg = calendar.get(Calendar.SECOND);


        String h = year.toString() + mes.toString() + dia.toString() + hora.toString() + min.toString() + seg.toString();
        ar = h + ".jpg";


        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // name = Environment.getExternalStorageDirectory() + "/"+ ar ;
        File foto = new File(getExternalFilesDir(null), ar);
        Uri output = Uri.fromFile(foto);
        // Uri output = Uri.fromFile(new File(name));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, output);

        startActivityForResult(intent, TAKE_PICTURE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TAKE_PICTURE) {


            if (resultCode == RESULT_OK) {
                galeria = "";
                bitmap = BitmapFactory.decodeFile(getExternalFilesDir(null) + "/" + ar);
                imagen.setImageBitmap(bitmap);
                archivos.add(ar);
                cantidadFotos = archivos.size();
            }

        } else if (requestCode == SELECT_PICTURE) {
            Uri selectedImage = data.getData();

            InputStream is;
            try {


                if (selectedImage == null) throw new AssertionError();
                galeria = selectedImage.toString();
                is = getContentResolver().openInputStream(selectedImage);
                BufferedInputStream bis = new BufferedInputStream(is);
                bitmap = BitmapFactory.decodeStream(bis);

                imagen.setImageBitmap(bitmap);


            } catch (FileNotFoundException e) {
            }
        }
    }

    public void atras(View v) {
        i--;
        if (i < 0) i = cantidadFotos - 1;
        mostrarimagen(i);
    }

    void mostrarimagen(Integer indice) {
        bitmap = BitmapFactory.decodeFile(getExternalFilesDir(null) + "/" + archivos.get(indice));
        imagen.setImageBitmap(bitmap);
        galeria = "";
    }

}
