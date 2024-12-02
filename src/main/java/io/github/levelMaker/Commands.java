package io.github.levelMaker;

public enum Commands {

    //ARGUMENTO
    place,
    remove,

    //FORMA
    rectangle,
    stair;


    public static boolean contains(String comandoIngresado){
        boolean isCorrecto = false;
        for (Commands comando : Commands.values()){
            if (comando.toString().equals(comandoIngresado)){
                isCorrecto = true;
            }
        }
        return isCorrecto;
    }
}
