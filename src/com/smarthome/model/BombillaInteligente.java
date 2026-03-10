package com.smarthome.model;

/**
 * Dispositivo concreto: Bombilla inteligente.
 *
 * - Implementa Conectable + Encendible (heredado de Dispositivo) y Graduable.
 * - Nivel representa el brillo actual.
 */
public class BombillaInteligente extends Dispositivo implements Graduable {

	//Comentario Daniel Navarro Pulido :)
	
    /** Brillo máximo exigido por el enunciado. */
    public static final int BRILLO_MAXIMO = 100;

    /** Brillo actual (0..100). */
    private int nivel;

    public BombillaInteligente(String id, String nombre) {
        // Tipo fijo: BOMBILLA. El estado inicial (DESCONECTADO/APAGADO) se fija en la superclase.
        super(id, nombre, TipoDispositivo.BOMBILLA);
        this.nivel = Graduable.NIVEL_MINIMO;
    }

    @Override
    public void ajustarNivel(int nivel) {
        // Regla: si está desconectado -> excepción usando método protected del modelo.
        // Esto garantiza el mensaje exacto Conectable.MENSAJE_DESCONECTADO.
        this.exigirConectado();

        // Regla: se permite ajustar aunque esté APAGADA, siempre que esté CONECTADA.
        // Por eso NO comprobamos isEncendido() aquí.
        if (!nivelEnRango(nivel)) {
            throw new IllegalArgumentException("Nivel fuera de rango: " + nivel);
        }
        this.nivel = nivel;
    }

    @Override
    public int getNivel() {
        return this.nivel;
    }

    @Override
    public int getNivelMaximo() {
        return BRILLO_MAXIMO;
    }
}
