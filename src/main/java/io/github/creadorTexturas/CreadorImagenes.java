package io.github.creadorTexturas;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class CreadorImagenes {

    public CreadorImagenes(){
    }

    /**
     * @param imagenes las imagenes que quieres unir
     * @param lugarDeGuardado en que carpeta quieres guardar (ej: C:\\Users\\Tomas\\Downloads\\nombreDeTuImagen.png)
     * Recuerda que (lugarDeGuardado) tambien tienes que especificar su nombre de la nueva imagen.
     * El (ArrayList) debe estar entre el rango de 1 a 100 (0 < x < 100)
     */
    public static void unirImagenes(ArrayList<BufferedImage> imagenes, File lugarDeGuardado){
        Long cantidadImagenes = imagenes.stream().count();
        //Math.toIntExact solo transforma un Long a un Int (si no es muy grande)
        Dimension tamañoImagenFinal = calcularTamaño(Math.toIntExact(cantidadImagenes));

        int widthTotal = (int) tamañoImagenFinal.getWidth();
        int heightTotal = (int) tamañoImagenFinal.getWidth();

        guardarImagen(widthTotal,heightTotal, imagenes, lugarDeGuardado);
    }

    /**
     * @param cantidadImagenes son todas las imagenes que quieres unir
     * @return retorna un (Dimension) el cual va a ser el tamaño de la imagen final
     *
     */
    private static Dimension calcularTamaño(int cantidadImagenes){
        System.out.println("cantidad de imagenes a fusionar: " + cantidadImagenes);
        if (cantidadImagenes <= 0 || cantidadImagenes>100){
            throw new IllegalArgumentException("tu ArrayList esta vacio o tiene mas de 100 imagenes. cantidadImagenes: " + cantidadImagenes);
        }
        double widthTotal;
        if (cantidadImagenes < 10){
            widthTotal = cantidadImagenes * 20;
        } else {
            widthTotal = 200;
        }
        double heightTotal = Math.ceil((double) cantidadImagenes / 10);
        return new Dimension( (int) widthTotal, (int) heightTotal);
    }

    /**
     * Este metodo se asegura de que guardes correctamente y que las "texturas" esten en una sola imagen que tiene un maximo de 200x200 [px]
     * De esta manera dependiendo de la cantidad de Imaggenes se guardara en una especie de rectangulo
     */
    private static void guardarImagen(double widthTotal, double heightTotal, ArrayList<BufferedImage> imagenes, File lugarDeGuardado){
        BufferedImage imagenCompleta = new BufferedImage((int) widthTotal,(int) heightTotal,BufferedImage.TYPE_INT_ARGB);
        Graphics graficos = imagenCompleta.getGraphics();

        //Se recorre el ArrayList "imagenes" con un stream
        //Se crea una nueva fila cuando el Width de "imagenCompleta" llega a 200 (10 texturas)
        //Es necesario la Clase "Atomic" para usar variables que puedan cambiar dentro de un stream
        AtomicInteger x = new AtomicInteger(-20); //Nose porque me da error cuando esta en "0" XD pero supongo que es por el comportamiento de x.addAndGet (Error de logica)
        AtomicInteger y = new AtomicInteger(0);

        imagenes.stream().forEach(bufferedImage -> {
            int xActual = x.addAndGet(20); //"x.addAndGet" suma "20" despues de pasar esta linea (ej: si es la primera vez que se ejecuta esta linea, xActual sera "0" y despues de esto a "x" (clase atomic) se le sumara 20)
            if (xActual < 200){
                graficos.drawImage(bufferedImage,xActual,y.get(),null);
            } else {
                //SE COLOCA LA PROXIMA FILA POR DEBAJO DE LA ANTERIOR
                xActual = 0;
                y.addAndGet(20);
                x.set(0);

                //SE COLOCA LA TEXTURA EN LA PRIMERA PARTE DE LA FILA
                //Es necesario seguir "dibujando" (drawImage) porque si no perderiamos una textura
                graficos.drawImage(bufferedImage,xActual,y.get(),null);
            }
        });
        graficos.dispose();
        try {
            ImageIO.write(imagenCompleta, "png", lugarDeGuardado);
        } catch (IOException e) {
            throw new RuntimeException("Parece ser que el lugar de guardado (" + lugarDeGuardado + ") esta teniendo un error: " + e);
        }
    }

}
