
package com.platzi;

import java.io.IOException;
import javax.swing.JOptionPane;

public class Inicio {

    public static void main(String[] args) throws IOException {
        
        int opcionMenu = -1;
        //opciones para ejecutar en la aplicacion
        String[] botones = {
            "1.Ver gatos",
            "2.Ver favoritos",
            "3.Salir"
        };
        
        do{
            
            String opcion = (String)JOptionPane
                    .showInputDialog(null, "Gatos", "Menu Principal", JOptionPane.INFORMATION_MESSAGE, null, botones, botones[0]);
            //validacion de opcion que selecciono el usuario
            for(int i = 0 ; i < botones.length ; i++){
                if(opcion.equals(botones[i])){
                    opcionMenu = i;
                }
            }
            
            switch (opcionMenu) {
                case 0:
                    GatosService.verGatos();
                    break;
                case 1:
                    Gatos gatos = new Gatos();
                    GatosService.verFavoritos(gatos.getApiKey());
                default: 
                    break;
            }
            
        }while(opcionMenu != 1);

    }
}
