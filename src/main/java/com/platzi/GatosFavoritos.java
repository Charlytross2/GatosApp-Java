/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.platzi;

/**
 *
 * @author carlos
 */
public class GatosFavoritos {
    
    String id;
    String image_id;
    String apiKey = "b95009e6-f74d-4a82-8987-883b82a02e29";
    ImageGato image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImagenId() {
        return image_id;
    }

    public void setImagenId(String imagenId) {
        this.image_id = imagenId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public ImageGato getImagen() {
        return image;
    }

    public void setImagen(ImageGato imagen) {
        this.image = imagen;
    }
    
}
