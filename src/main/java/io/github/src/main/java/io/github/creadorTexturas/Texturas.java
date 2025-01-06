package io.github.creadorTexturas;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Texturas {

    /**
     ESTA CLASE SIRVE PARA INGRESAR IMAGENES (TEXTURAS DE LOS BLOQUES) Y FUSIONARLAS.
     NO HAY INTERFAZ GRAFICA, UNICAMENTE ES CODIGO.
     */

    public static void main(String[] args) throws RuntimeException, IOException {

        ArrayList<BufferedImage> imagenes = new ArrayList<>(); //ArrayList que guardara las imagenes que importemos

        // (carpeta) tendra las imagenes que importemos de una (CARPETA), importante que sea UNA CARPETA
        File carpeta = new File("C:\\Users\\Tomas\\OneDrive\\Desktop\\texturasJuego (NO MOVER)");

        if (!carpeta.isDirectory() || !carpeta.exists()){
          throw new IllegalArgumentException("Hay un error con la ruta al archivo: " + carpeta.getName());
        }

        /*
        Por alguna razon me daba error esto:
         ArrayList<File> archivoLista = (ArrayList<File>) Arrays.asList((Objects.requireNonNull(carpeta.listFiles())));
         */

        imagenes.add(ImageIO.read(new File("C:\\Users\\Tomas\\OneDrive\\Desktop\\texturasJuego (NO MOVER)\\Indicadores\\IndicadorSeleccionar.png")) );
        imagenes.add(ImageIO.read(new File("C:\\Users\\Tomas\\OneDrive\\Desktop\\texturasJuego (NO MOVER)\\Indicadores\\IndicadorAccion.png")));

        //Conversiones de lista a stream
        Arrays.stream((Objects.requireNonNull(carpeta.listFiles()))).
                filter((archivo) -> archivo.getName().endsWith(".png") //SOLO ACEPTA ARCHIVOS PNG
        ).forEach(arhcivo -> {
            try {
                    imagenes.add( ImageIO.read(arhcivo)); //Archivos PNG's se añaden a imagenes
            } catch (IOException e) {
                    throw new RuntimeException(e);
            }
        });

        //LA GUARDA EN UN LUGAR A TU ELECCION
        File lugarDeGuardado = new File("C:\\Users\\Tomas\\Downloads\\imagenTesteo1.png");
        CreadorImagenes.unirImagenes(imagenes,lugarDeGuardado);
        System.out.println("Guardado exitoso");
    }
}