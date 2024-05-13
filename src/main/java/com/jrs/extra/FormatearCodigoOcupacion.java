package com.jrs.extra;

public class FormatearCodigoOcupacion {

    /**
     * The formatearCodigoTipo function takes a String as an argument and returns a String.
     * The function is used to format the text of the code type in order to make it more readable for users.
     *
     *
     * @param texto Determine which text to return
     *
     * @return A string
     *
     */
    public static String formatearCodigoTipo( String texto ) {
        return switch (texto) {
            case "ocupacion" -> "Grado de ocupación por plazas (**)";
            case "ocupacionFin" -> "Grado de ocupación por plazas en fin de semana (**)";
            case "establecimientos" -> "Número de establecimientos abiertos estimados (*)";
            case "plazas" -> "Número de plazas estimadas (*)";
            case "personal" -> "Personal empleado (*)";
            default -> texto;
        };
    }

    /**
     * The formatearCodigoMetadata function takes a String as input and returns the same string with all spaces, asterisks, parentheses and capital letters removed.
     *
     *
     * @param texto Store the text that is being processed
     *
     * @return A string without spaces, asterisks, parentheses and lowercase
     *
     */
    public static String formatearCodigoMetadata( String texto ) {
        texto = texto.replaceAll( " " , "" );
        texto = texto.replace( "*" , "" );
        texto = texto.replace( "(" , "" );
        texto = texto.replace( ")" , "" );
        return texto.toLowerCase( );
    }
}
