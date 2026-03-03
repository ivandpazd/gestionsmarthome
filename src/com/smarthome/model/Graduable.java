package com.smarthome.model;

/**
 * Capacidad de ajuste de nivel (p. ej. brillo en bombillas).
 *
 * Reglas del examen:
 * - NIVEL_MINIMO es 0.
 * - nivelEnRango(nivel) (default) ayuda a validar sin repetir lógica.
 * - ajustarNivel(nivel) debe lanzar IllegalArgumentException si el nivel está fuera de rango.
 * - Se permite ajustar el nivel aunque el dispositivo esté apagado, SIEMPRE que esté conectado.
 */
public interface Graduable {

    int NIVEL_MINIMO = 0;

    /**
     * Ajusta el nivel actual.
     * @throws IllegalArgumentException si el nivel está fuera de rango o si el dispositivo está desconectado
     */
    void ajustarNivel(int nivel);

    /** Nivel actual. */
    int getNivel();

    /** Nivel máximo permitido (depende del dispositivo). */
    int getNivelMaximo();

    /**
     * Valida que el nivel esté entre NIVEL_MINIMO y el máximo del dispositivo (ambos inclusive).
     */
    default boolean nivelEnRango(int nivel) {
        return nivel >= NIVEL_MINIMO && nivel <= getNivelMaximo();
    }
}
