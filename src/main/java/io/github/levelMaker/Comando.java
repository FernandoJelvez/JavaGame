package io.github.levelMaker;

import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class Comando {

    private static AbstractExceptionManager exceptionManager = new ExceptionManager();
    private static Point puntoInicial = new Point();
    public static String rellenar;

    public static Point[] start(Point puntoDeInicio){
        System.out.println("Comandos en clase 'Comando' ");
        System.out.println("(place/remove) (forma) (coordX,coordY)");
        System.out.println("Introducir comando: ");
        puntoInicialAUnidades(puntoDeInicio);
        Scanner leer = new Scanner(System.in);

        rellenar = "cancelar";
        Point[] puntos = new Point[0];
        while (true){
            String comando = leer.nextLine();
            if (comando.equals("cancelar")){
                System.out.println("Has cancelado");
                break;
            }
            try {
                puntos = leerComando(comando);
                break;
            } catch (Exception e){
                exceptionManager.catchException(e);
            }
        }
        return puntos;
    }

    private static Point[] leerComando(String comando) throws Exception{
        comando = comando.toLowerCase();
        String[] comandoDivido = comando.split(" ");
        verificar(comandoDivido);

        if (comandoDivido[0].equals(Commands.place.toString())){
            rellenar = Commands.place.toString();
        } else {
            rellenar = Commands.remove.toString();
        }
        Point puntoFinal = obtenerCoordenada(comandoDivido[2]);
        return unidadesAPuntos(Pintar.rectangle(puntoInicial, puntoFinal));
    }

    private static void verificar(String[] argumentos) throws Exception {
        long largo = Arrays.stream(argumentos).limit(2).filter(Commands::contains).count();
        // (argumentos.length != 3) comprueba si se han puesto los 3 argumentos
        // (largo != 2) comprueba si los 2 primeros argumentos son validos (place/remove) (stair)(rectangle)
        if (argumentos.length != 3 || largo != 2){
            throw new Exception("ha puesto algun dato mal");
        }
        Point puntoFinal = obtenerCoordenada(argumentos[2]);
        if (puntoFinal.getX() - puntoInicial.getX() == 0 && puntoFinal.getY() - puntoInicial.getY() == 0){
            throw new Exception("ha puesto algun dato mal");
        }
    }

    private static Point obtenerCoordenada(String argumento){
        int indice = argumento.indexOf(",");
        int x = Integer.parseInt(argumento.substring(0,indice));
        int y = Integer.parseInt(argumento.substring(indice + 1));
        return new Point(x,y);
    }


    private static void puntoInicialAUnidades(Point puntoDeInicio){
        puntoInicial.setLocation(puntoDeInicio.getX() / 20, puntoDeInicio.getY() / 20);
    }

    private static Point[] unidadesAPuntos(Point[] puntos){
        return Arrays.stream(puntos).peek( (punto) -> {
            double x = punto.getX() * 20;
            double y = punto.getY() * 20;
            punto.setLocation(x,y);
        }).toArray(Point[]::new);
    }

    public static String getRellenar() {
        return rellenar;
    }
}
