/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.platzi;

import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author carlos
 */
public class GatosService {

    public static void verGatos() throws IOException {
        //Traer datos de la Api
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.thecatapi.com/v1/images/search").get().build();
        Response response = client.newCall(request).execute();

        String json = response.body().string();
        //cortamos los corchetes
        json = json.substring(1, json.length());
        json = json.substring(0, json.length() - 1);

        Gson gson = new Gson();
        Gatos gatos = gson.fromJson(json, Gatos.class);

        //redimensionamos la imagen
        Image imagen = null;
        try {
            URL url = new URL(gatos.getUrl());
            imagen = ImageIO.read(url);

            ImageIcon fondoGato = new ImageIcon(imagen);
            if (fondoGato.getIconWidth() > 800) {
                //redimensionamos
                Image fondo = fondoGato.getImage();
                Image imagenMoficada = fondo.getScaledInstance(800, 600, java.awt.Image.SCALE_SMOOTH);
                fondoGato = new ImageIcon(imagenMoficada);
            }

            String menu = "Opciones\n" + "1.Ver otra imagen\n" + "2.Favorito\n" + "3.Volver al menu\n";
            String[] botones = {"Ver otra imagen", "Favorito", "Volver"};
            String idGato = gatos.getId();
            String opcion = (String) JOptionPane.showInputDialog(null, menu, idGato, JOptionPane.INFORMATION_MESSAGE, fondoGato, botones, botones[0]);
            int seleccion = -1;
            for (int i = 0; i < botones.length; i++) {
                if (opcion.equals(botones[i])) {
                    seleccion = 1;
                }
            }
            switch (seleccion) {
                case 0:
                    verGatos();
                    break;
                case 1:
                    gatoFavorito(gatos);
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void gatoFavorito(Gatos gato) {

        try {
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\n    \"image_id\":\"" + gato.getId() + "\"\n}");
            Request request = new Request.Builder()
                    .url("https://api.thecatapi.com/v1/favourites")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("x-api-key", gato.getApiKey())
                    .build();
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void verFavoritos(String apiKey) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://api.thecatapi.com/v1/favourites")
                .get()
                .addHeader("x-api-key", apiKey)
                .build();
        Response response = client.newCall(request).execute();
        String json = response.body().string();

        Gson gson = new Gson();
        GatosFavoritos[] gatosArray = gson.fromJson(json, GatosFavoritos[].class);
        if (gatosArray.length > 0) {
            int min = 1;
            int max = gatosArray.length;
            int numeroAleatorio = (int) (Math.random() * ((max - min) - 1)) + min;
            int indice = numeroAleatorio - 1;

            GatosFavoritos gatoFav = gatosArray[indice];

            //redimensionamos la imagen
            Image imagen = null;
            try {
                URL url = new URL(gatoFav.image.getUrl());
                imagen = ImageIO.read(url);

                ImageIcon fondoGato = new ImageIcon(imagen);
                if (fondoGato.getIconWidth() > 800) {
                    //redimensionamos
                    Image fondo = fondoGato.getImage();
                    Image imagenMoficada = fondo.getScaledInstance(800, 600, java.awt.Image.SCALE_SMOOTH);
                    fondoGato = new ImageIcon(imagenMoficada);
                }

                String menu = "Opciones\n" + "1.Ver otra imagen\n" + "2.Eliminar Favorito\n" + "3.Volver al menu\n";
                String[] botones = {"Ver otra imagen", "Eliminar favorito", "Volver"};
                String idGato = gatoFav.getId();
                String opcion = (String) JOptionPane.showInputDialog(null, menu, idGato, JOptionPane.INFORMATION_MESSAGE, fondoGato, botones, botones[0]);
                int seleccion = -1;
                for (int i = 0; i < botones.length; i++) {
                    if (opcion.equals(botones[i])) {
                        seleccion = 1;
                    }
                }
                switch (seleccion) {
                    case 0:
                        verFavoritos(apiKey);
                        break;
                    case 1:
                        borrarFavorito(gatoFav);
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private static void borrarFavorito(GatosFavoritos gatoFav) {
        try {
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "");
            Request request = new Request.Builder()
                    .url("https://api.thecatapi.com/v1/favourites/"+gatoFav.getId()+"")
                    .method("DELETE", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("x-api-key", gatoFav.getApiKey())
                    .build();
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
