package com.smarthome.model;

/**
 * Capacidad de encendido/apagado.
 *
 * Regla del examen:
 * - encender()/apagar() devuelven true SOLO si cambian el estado.
 * - Si el dispositivo está desconectado, NO se puede encender/apagar: devuelve false y no cambia el estado.
 */
public interface Encendible {

    String TEXTO_ENCENDIDO = "ENCENDIDO";
    String TEXTO_APAGADO = "APAGADO";

    /**
     * Enciende el dispositivo (si está conectado).
     * @return true si pasa de apagado a encendido; false si no cambia.
     */
    boolean encender();

    /**
     * Apaga el dispositivo (si está conectado).
     * @return true si pasa de encendido a apagado; false si no cambia.
     */
    boolean apagar();

    /** @return true si el dispositivo está encendido, false si está apagado. */
    boolean isEncendido();

    /**
     * Helper para imprimir el estado de encendido de forma consistente.
     * (Evita "mágicos" como "Encendido/Apagado" y asegura el texto exacto del enunciado.)
     */
    default String textoEstadoEncendido() {
        return isEncendido() ? TEXTO_ENCENDIDO : TEXTO_APAGADO;
    }
}
