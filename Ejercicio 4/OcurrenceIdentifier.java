/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.ocurrenceidentifier;

/**
 *
 * @author Sebastian
 */
 import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OcurrenceIdentifier {

    public static void contarOcurrencias(String cadena, String palabra) {
     
        Pattern patron = Pattern.compile("\\b" + palabra + "\\b", Pattern.CASE_INSENSITIVE); //CASE_INSENSITIVE para ignorar mayúsculas/minúsculas

   
        Matcher matcher = patron.matcher(cadena);

      
        int contador = 0;
        while (matcher.find()) {
            contador++;
            System.out.println("Ocurrencia " + contador + ": " + palabra + " encontrada en la posicion " + matcher.start());
        }

       
        System.out.println("La palabra '" + palabra + "' aparece " + contador + " veces en la cadena.");
    }

    public static void main(String[] args) {
        String cadena = "Dado una cadena C, valide si C se encuentra en notación FEN (Forsyth-Edwards Notation), \n" +
"Forsyth–Edwards Notation. FEN es un sistema estándar para describir posiciones específicas en \n" +
"partidas de ajedrez, permitiendo reiniciar el juego desde una posición dada. Desarrollado \n" +
"inicialmente por David Forsyth y ampliado por Steven J. Edwards, FEN se utiliza en la Notación \n" +
"de Juego Portátil para definir posiciones iniciales distintas a la estándar (Wikipedia, 2025).";
        String palabra = "FEN";  // Palabra a buscar (sin distinguir mayúsculas/minúsculas)

        contarOcurrencias(cadena, palabra);
    }
}
