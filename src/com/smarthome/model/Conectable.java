package com.smarthome.model;

/**
 * Capacidad de conexión.
 *
 * Reglas del examen:
 * - conectar()/desconectar() devuelven true SOLO si cambian el estado.
 * - getEstadoConexion() expone el estado real de conexión.
 * - estaConectado() (default) evita repetir comparaciones con enums en el resto del código.
 *
 * Nota: la regla "al desconectar queda apagado" se garantiza en Dispositivo.desconectar().
 */
public interface Conectable {

    /** Mensaje exacto exigido por el enunciado para la excepción de "dispositivo desconectado". */
    String MENSAJE_DESCONECTADO = "Dispositivo desconectado";

    /**
     * Conecta el dispositivo.
     * @return true si cambia a CONECTADO; false si ya estaba conectado.
     */
    boolean conectar();

    /**
     * Desconecta el dispositivo.
     * @return true si cambia a DESCONECTADO; false si ya estaba desconectado.
     */
    boolean desconectar();

    /**
     * Estado actual de conexión (CONECTADO / DESCONECTADO).
     */
    EstadoConexion getEstadoConexion();

    /**
     * Helper para consultar si el estado actual es CONECTADO.
     * (Se usa mucho en Escena y en reglas de "no se puede".)
     */
    default boolean estaConectado() {
        return getEstadoConexion() == EstadoConexion.CONECTADO;
    }
}
