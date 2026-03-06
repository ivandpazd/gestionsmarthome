package com.smarthome.model;

import com.smarthome.services.SmartHomeUtils;

/**
 * Dispositivo concreto: Cámara de seguridad.
 *
 * - Implementa Conectable + Encendible (heredado de Dispositivo).
 * - Sensibilidad determina si se detecta movimiento.
 */
public class CamaraSeguridad extends Dispositivo {
 public static final int SENSIBILIDAD_MINIMA = 1;
    public static final int SENSIBILIDAD_MAXIMA = 10;
    public static final int UMBRAL_MOVIMIENTO = 7;
    private int sensibilidad;

    public CamaraSeguridad(String id, String nombre, int sensibilidad) {
        super(id, nombre, TipoDispositivo.CAMARA);
        this.setSensibilidad(sensibilidad);
    }

    public int getSensibilidad() {
        return this.sensibilidad;
    }

    /**
     * Setter con validación de rango inclusivo [1..10] (obligatorio).
     */
    public void setSensibilidad(int sensibilidad) {
        SmartHomeUtils.validarRangoInclusive(sensibilidad, SENSIBILIDAD_MINIMA, SENSIBILIDAD_MAXIMA, "sensibilidad");
        this.sensibilidad = sensibilidad;
    }

    /**
     * Detecta movimiento según el enunciado:
     * true SOLO si está conectada, encendida y sensibilidad >= UMBRAL_MOVIMIENTO.
     */
    public boolean detectarMovimiento() {
        return this.estaConectado() && this.isEncendido() && sensibilidad >= UMBRAL_MOVIMIENTO;
    }

    /**
     * Carol nuevoMetodo: aumenta la sensibilidad sin pasarse del máximo
     */
    public void aumentarSensibilidad() {
        if (this.sensibilidad < SENSIBILIDAD_MAXIMA) {
            this.sensibilidad++;
        }
    }
}