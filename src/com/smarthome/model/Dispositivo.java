package com.smarthome.model;

import com.smarthome.services.SmartHomeUtils;

/**
 * Modelo base para el sistema SmartHome.
 *
 * Reglas clave:
 * - Todo dispositivo parte DESCONECTADO y APAGADO.
 * - Si está DESCONECTADO, no se puede encender/apagar (devuelve false).
 * - Si se desconecta, SIEMPRE queda apagado.
 * - Las validaciones de id/nombre/tipo se hacen en constructor y setter.
 * - El método protected exigirConectado() lanza la excepción con el mensaje exacto del enunciado.
 */
public abstract class Dispositivo implements Conectable, Encendible {

    /** Inmutable: identificador único del dispositivo. */
    private final String id;

    /** Mutable: el nombre puede cambiar (setter con validación). */
    private String nombre;

    /** Inmutable: tipo de dispositivo (BOMBILLA / CAMARA). */
    private final TipoDispositivo tipo;

    private EstadoConexion estadoConexion;
    private boolean encendido;

    protected Dispositivo(String id, String nombre, TipoDispositivo tipo) {
        // Validaciones exigidas: id y nombre no null/vacío; tipo no null.
        this.id = SmartHomeUtils.validarTextoNoVacio(id, "id");
        this.nombre = SmartHomeUtils.validarTextoNoVacio(nombre, "nombre");
        this.setNombre(nombre);
        this.tipo = SmartHomeUtils.validarNoNull(tipo, "tipo");

        // Estado inicial exigido por el examen.
        this.estadoConexion = EstadoConexion.DESCONECTADO;
        this.encendido = false;
    }

    public String getId() {
        return this.id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        // Setter con validación (regla general).
        this.nombre = SmartHomeUtils.validarTextoNoVacio(nombre, "nombre");
    }

    public TipoDispositivo getTipo() {
        return this.tipo;
    }

    @Override
    public EstadoConexion getEstadoConexion() {
        return this.estadoConexion;
    }

    protected void setEstadoConexion(EstadoConexion estadoConexion) {
        this.estadoConexion = SmartHomeUtils.validarNoNull(estadoConexion, "estadoConexion");
    }

    @Override
    public boolean isEncendido() {
        return this.encendido;
    }

    protected void setEncendido(boolean encendido) {
        this.encendido = encendido;
    }

    /**
     * Método de apoyo exigido por el enunciado.
     *
     * Se usa desde lógica de negocio (p. ej. ajustarNivel) para forzar:
     * - Excepción si el dispositivo está DESCONECTADO.
     * - Mensaje exacto del enunciado: Conectable.MENSAJE_DESCONECTADO.
     */
    protected void exigirConectado() {
        if (!estaConectado()) {
            throw new IllegalArgumentException(Conectable.MENSAJE_DESCONECTADO);
        }
    }

    @Override
    public boolean conectar() {
        // Importante: true SOLO si cambia el estado.
        if (estaConectado()) {
            return false;
        }
        setEstadoConexion(EstadoConexion.CONECTADO);
        return true;
    }

    @Override
    public boolean desconectar() {
        // Importante: true SOLO si cambia el estado.
        if (!estaConectado()) {
            return false;
        }
        // Regla obligatoria: al desconectar, queda apagado.
        setEstadoConexion(EstadoConexion.DESCONECTADO);
        setEncendido(false);
        return true;
    }

    @Override
    public boolean encender() {
        // Regla: si está desconectado, "no se puede" -> false y NO cambia el estado.
        if (!estaConectado()) {
            return false;
        }
        // true SOLO si cambia el estado (apagado -> encendido).
        if (isEncendido()) {
            return false;
        }
        setEncendido(true);
        return true;
    }

    @Override
    public boolean apagar() {
        // Regla: si está desconectado, "no se puede" -> false y NO cambia el estado.
        if (!estaConectado()) {
            return false;
        }
        // true SOLO si cambia el estado (encendido -> apagado).
        if (!isEncendido()) {
            return false;
        }
        setEncendido(false);
        return true;
    }

    @Override
    public String toString() {
        // Representación informativa exigida (id, nombre, tipo, conexión y encendido).
        return "Dispositivo [id=" + id
                + ", nombre=" + nombre
                + ", tipo=" + tipo
                + ", estadoConexion=" + estadoConexion
                + ", encendido=" + encendido
                + "]";
    }
}
