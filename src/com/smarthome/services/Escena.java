package com.smarthome.services;

import com.smarthome.model.Dispositivo;
import com.smarthome.model.Graduable;
import com.smarthome.model.TipoDispositivo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Una escena agrupa varios dispositivos y aplica una secuencia coordinada de acciones.
 *
 * Reglas del examen:
 * - No se admite null al añadir dispositivos.
 * - No se admiten duplicados por id (comparación exacta de String).
 * - ejecutar() debe recorrer con Iterator y aplicar: conectar -> encender -> ajustar nivel (bombillas) -> imprimir.
 */
public class Escena {

    /** Constante exigida para la escena "Cine". */
    public static final int BRILLO_CINE = 20;

    private final String nombre;
    private final List<Dispositivo> dispositivos;

    public Escena(String nombre) {
        // Validación de texto según reglas generales (IllegalArgumentException con mensaje claro).
        this.nombre = SmartHomeUtils.validarTextoNoVacio(nombre, "nombre");
        this.dispositivos = new ArrayList<>();
    }

    public String getNombre() {
        return this.nombre;
    }

    public List<Dispositivo> getDispositivos() {
        return this.dispositivos;
    }

    /**
     * Alta de dispositivos en la escena.
     *
     * @return true si se añade; false si ya existía un dispositivo con el mismo id.
     */
    public boolean addDispositivo(Dispositivo dispositivo) {
        SmartHomeUtils.validarNoNull(dispositivo, "dispositivo");

        // Duplicados por id: se compara el texto exacto del id (no ignoramos mayúsculas/minúsculas).
        for (Dispositivo d : dispositivos) {
            if (d.getId().equals(dispositivo.getId())) {
                return false;
            }
        }
        return this.dispositivos.add(dispositivo);
    }

    /**
     * Ejecuta la escena con el orden exigido:
     * 1) conectar si hace falta
     * 2) encender
     * 3) ajustar nivel (solo bombillas)
     * 4) imprimir acciones realizadas
     *
     * Importante: se usa Iterator porque el enunciado lo exige explícitamente.
     */
    public void ejecutar() {
        Iterator<Dispositivo> it = dispositivos.iterator();
        while (it.hasNext()) {
            Dispositivo dispositivo = it.next();

            // 1) Conectar solo si hace falta: así "conectar()" devuelve true únicamente cuando cambia el estado.
            if (!dispositivo.estaConectado()) {
                if (dispositivo.conectar()) {
                    System.out.println("Dispositivo " + dispositivo.getNombre() + " conectado");
                }
            }

            // 2) Encender. Si ya estaba encendido, encender() devuelve false (no hay cambio de estado).
            if (dispositivo.encender()) {
                if (dispositivo.getTipo() == TipoDispositivo.BOMBILLA) {
                    System.out.println("Bombilla " + dispositivo.getNombre() + " encendida");
                } else {
                    System.out.println("Cámara " + dispositivo.getNombre() + " encendida");
                }
            }

            // 3) Ajustar nivel solo en bombillas. Usamos la constante BRILLO_CINE para evitar números mágicos.
            if (dispositivo.getTipo() == TipoDispositivo.BOMBILLA && dispositivo instanceof Graduable) {
                ((Graduable) dispositivo).ajustarNivel(BRILLO_CINE);
                System.out.println("Brillo de " + dispositivo.getNombre() + " ajustado a " + BRILLO_CINE);
            }

            // 4) Impresión: ya se imprime de forma incremental en los pasos anteriores.
        }
    }
}
