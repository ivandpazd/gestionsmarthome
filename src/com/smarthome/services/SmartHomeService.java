package com.smarthome.services;

import com.smarthome.model.CamaraSeguridad;
import com.smarthome.model.Dispositivo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Servicio integrador del sistema SmartHome.
 *
 * Responsabilidades:
 * - Registrar dispositivos (sin null y sin ids duplicados).
 * - Buscar dispositivos por id.
 * - Registrar escenas (sin null y sin nombres duplicados).
 * - Ejecutar escena por nombre usando Iterator (obligatorio).
 * - Revisar cámaras y mostrar detección de movimiento.
 */
public class SmartHomeService {

    private final List<Dispositivo> dispositivos;
    private final List<Escena> escenas;

    public SmartHomeService() {
        this.dispositivos = new ArrayList<>();
        this.escenas = new ArrayList<>();
    }

    public List<Dispositivo> getDispositivos() {
        return this.dispositivos;
    }

    /**
     * Registra un dispositivo en el sistema.
     *
     * Reglas:
     * - No admite null.
     * - No admite ids duplicados (comparación exacta de String).
     * @return true si se registra; false si ya existía un dispositivo con ese id.
     */
    public boolean registrarDispositivo(Dispositivo dispositivo) {
        SmartHomeUtils.validarNoNull(dispositivo, "dispositivo");

        for (Dispositivo d : dispositivos) {
            if (d.getId().equals(dispositivo.getId())) {
                return false; // duplicado por id
            }
        }
        return this.dispositivos.add(dispositivo);
    }

    /**
     * Busca un dispositivo por id.
     * @return el dispositivo si existe; null si no se encuentra.
     */
    public Dispositivo buscarPorId(String id) {
        String idNormalizado = SmartHomeUtils.validarTextoNoVacio(id, "id");
        for (Dispositivo d : dispositivos) {
            if (d.getId().equals(idNormalizado)) {
                return d;
            }
        }
        return null;
    }

    /**
     * Registra una escena.
     * - No admite null.
     * - No admite nombres duplicados (comparación exacta).
     */
    public boolean registrarEscena(Escena escena) {
        SmartHomeUtils.validarNoNull(escena, "escena");

        for (Escena e : escenas) {
            if (e.getNombre().equals(escena.getNombre())) {
                return false; // nombre duplicado
            }
        }
        return this.escenas.add(escena);
    }

    /**
     * Ejecuta escena por nombre usando Iterator (obligatorio).
     * Devuelve true si existe, false si no.
     */
    public boolean ejecutarEscenaPorNombre(String nombreEscena) {
        String nombreNormalizado = SmartHomeUtils.validarTextoNoVacio(nombreEscena, "nombreEscena");

        Iterator<Escena> it = this.escenas.iterator();
        while (it.hasNext()) {
            Escena escena = it.next();
            if (escena.getNombre().equals(nombreNormalizado)) {
                // Si existe, se ejecuta.
                escena.ejecutar();
                return true;
            }
        }
        return false;
    }

    /**
     * Método de revisión: recorre dispositivos y por cada cámara imprime si detecta movimiento.
     * */
    public void revisionCamaras() {
        Iterator<Dispositivo> it = this.dispositivos.iterator();
        while (it.hasNext()) {
            Dispositivo d = it.next();
            if (d instanceof CamaraSeguridad) {
                CamaraSeguridad camara = (CamaraSeguridad) d;
                if (camara.detectarMovimiento()) {
                    System.out.println("Movimiento detectado: " + camara.getId() + " - " + camara.getNombre());
                }
            }
        }
    }
}
