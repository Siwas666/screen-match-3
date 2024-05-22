package com.alura.screenmatch.excepcion;

public class ErrorConvercionDuracionExcepcion extends RuntimeException {
    private String mensaje;

    public ErrorConvercionDuracionExcepcion(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String getMessage() {
        return this.mensaje;
    }
}
