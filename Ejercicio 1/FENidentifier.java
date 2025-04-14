/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.fenidentifier;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
/**
 *
 * @author Sebastian
 */




public class FENidentifier {
    
    //PART CHECKER
     public static void checker (String C){ 
     String[] parts= C.split(" ");
     int iparts=0;
     if (parts.length == 6) {
         System.out.println("Partes de FEN validas (6)");
         PosChecker(parts[0]);
         Turncheck(parts[1]);
         Enroquecheck(parts[2]);
         CasillaCheck(parts[3]);
         MovMedCheck(parts[4]);
         MovimientosCheck(parts[5]);
     
     }
     else{System.out.println("ERROR: El FEN introducido no contiene 8 partes validas");}
         System.out.println(parts[0]);
         
         
   /* for (String part: parts){
        iparts++;
        System.out.println(part);
        if(iparts==1){
         String[] pospart= part.split("/");
            if (pospart.length == 8) {
                System.out.println("Posicion de FEN validas (7)");
                   iparts++;}
                else{System.out.println("ERROR: El FEN no contiene posiciones validas");
            return;}}
     
        
    }*/
   }
     //Posicion
     public static void PosChecker (String C){ 
     String[] parts= C.split("/");
     int iparts=0;
     if (parts.length == 8) {
         System.out.println("Posicion de FEN validas (8)");}
     else{System.out.println("ERROR: El FEN no contiene 8 posiciones validas");}
  
    for (String part: parts){
        iparts++;
        System.out.println(part);
        boolean valido = part.matches("[rnbqkp1-8RNBQKP]+"); // true
         if (valido){
             System.out.println("FEN VALIDO");
             
         }
         else{
             System.out.println("Parte de FEN invalida");
         break;}
    }
   }
        
     public static void Turncheck (String C){ 
        boolean valido = C.matches("[wb]"); // true
         if (valido){
             System.out.println(C);
             System.out.println("FEN VALIDO");
             
         }
         else{
             System.out.println("Turno de FEN invalida");
         return;}}
     
      public static void Enroquecheck (String C){ 
        boolean valido = C.matches("[QKqk]+"); // true
         if (valido){
             System.out.println(C);
             System.out.println("FEN VALIDO");
             
         }
         else{
             System.out.println("Enroque de FEN invalido");
         return;}}
      
      public static void CasillaCheck (String C){ 
        boolean valido = C.matches("[a-h][1-8-]"); // true
         if (valido){
             System.out.println(C);
             System.out.println("FEN VALIDO");
             
         }
         else{
             System.out.println("Casilla de FEN invalido");
         return;}}
      
       public static void MovMedCheck (String C){
          System.out.println(C);
        if (!C.matches("\\d+")) { // Solo dígitos
            System.err.println("Error: El conteo de medio movimientos debe ser un número entero.");
            return;
        }

        try {
            int halfmoveCount = Integer.parseInt(C);
            if (halfmoveCount < 0) {
                System.err.println("Error: El conteo de medio movimientos no puede ser negativo.");
                return;
            }
        } catch (NumberFormatException e) {
            System.err.println("Error: El conteo de medio movimientos no es un número entero válido.");
            return; // Aunque la regex ya debería haber evitado esto, lo dejamos por seguridad
        }
           System.out.println("FEN VALIDO");
        return;
    }       
       
    public static void MovimientosCheck (String C){
        System.out.println(C);
        if (!C.matches("\\d+")) { // Solo dígitos
            System.err.println("Error: El número de movimiento completo debe ser un número entero.");
            return;
        }

        try {
            int fullmoveCount = Integer.parseInt(C);
            if (fullmoveCount <= 0) {  // Generalmente el conteo de movimientos empieza en 1.
                System.err.println("Error: El número de movimiento completo debe ser mayor que cero.");
                return;
            }
        } catch (NumberFormatException e) {
            System.err.println("Error: El número de movimiento completo no es un número entero válido.");
            return; // Aunque la regex ya debería haber evitado esto, lo dejamos por seguridad
        }
        System.out.println("FEN VALIDO");
        return;
    }
       
       
       
    
/**
 * "\\d+"
Datos de colocación de piezas: Se describe cada rango, comenzando con el rango 8 y terminando con el rango 1, con una "/" entre cada uno; Dentro de cada rango, el contenido de las casillas se describe en orden desde la columna A hasta la columna H.
* Cada pieza se identifica con una sola letra tomada de los nombres estándar en inglés en notación algebraica (peón = "P", caballo = "N", alfil = "B", torre = "R", reina = "Q" y rey = "K").
* Las piezas blancas se designan con letras mayúsculas ("PNBRQK"), mientras que las piezas negras usan letras minúsculas ("pnbrqk").
* Un conjunto de una o más casillas vacías consecutivas dentro de un rango se denota con un dígito del "1" al "8", correspondiente al número de casillas.
Color activo: "w" significa que el blanco debe moverse; "b" significa que las negras deben moverse.
* 
Disponibilidad de enroque: Si ninguno de los bandos tiene la capacidad de enrocar, este campo utiliza el carácter "-".
* De lo contrario, este campo contiene una o más letras:
* "K" si las blancas pueden enrocar en el flanco de rey, 
* "Q" si las blancas pueden enrocar en el flanco de dama,
* "k" si las negras pueden enrocar en el flanco de dama.
* Una situación que impida temporalmente el enroque no impide el uso de esta notación.
* 
Casilla objetivo al paso: Se trata de una casilla por la que acaba de pasar un peón mientras se mueve dos casillas;
* Se da en notación algebraica. Si no hay un cuadrado de destino al paso, este campo utiliza el carácter "-".
* Esto se registra independientemente de si hay un peón en posición de capturar al paso.
* [6] Desde entonces, una versión actualizada de la especificación ha hecho que el cuadrado objetivo se registre solo si es posible una captura legal al paso,
* pero la versión antigua del estándar es la más utilizada. [7][8]
* 
Reloj de medio movimiento: El número de medios movimientos desde la última captura o avance de peón,
* utilizado para la regla de cincuenta movimientos
* 
Número de jugadas completas: El número de jugadas completas. Comienza en 1 y se incrementa después de la jugada de las negras.
*/




    public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    String C = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq b8 0 0";
  checker(C);
        
        
        
} 
     
}

