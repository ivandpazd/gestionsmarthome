package com.smarthome.services;

/**
 * Utilidades estáticas para validación.
 *
 * Motivo (diseño):
 * - Evitamos repetir validaciones en todas las clases.
 * - Centralizamos mensajes de error claros y consistentes.
 */
public final class SmartHomeUtils {

    private SmartHomeUtils() {
        // Clase de utilidades: no debe instanciarse.
        throw new AssertionError("No instanciable");
    }

    /**
     * Valida que un texto no sea null ni vacío (tras hacer trim).
     * @return el texto normalizado (trim) para evitar ids/nombres con espacios "fantasma".
     */
    public static String validarTextoNoVacio(String valor, String nombreCampo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(nombreCampo + " no puede ser null ni vacío");
        }
        return valor.trim();
    }

    /**
     * Valida que un valor no sea null.
     */
    public static <T> T validarNoNull(T valor, String nombreCampo) {
        if (valor == null) {
            throw new IllegalArgumentException(nombreCampo + " no puede ser null");
        }
        return valor;
    }

    /**
     * Valida que un entero esté dentro de un rango inclusivo [min..max].
     */
    public static void validarRangoInclusive(int valor, int min, int max, String nombreCampo) {
        if (valor < min || valor > max) {
            throw new IllegalArgumentException(
                    nombreCampo + " fuera de rango [" + min + ".." + max + "]: " + valor
            );
        }
    }
}
