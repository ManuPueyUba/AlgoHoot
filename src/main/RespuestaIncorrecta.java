package main;

public class RespuestaIncorrecta implements Respuesta{
    private TipoDePenalidad tipoDePenalidad;
    private String enunciado;

    public RespuestaIncorrecta(String respuesta, TipoDePenalidad tipoDePenalidad){
        this.tipoDePenalidad = tipoDePenalidad;
        this.enunciado = respuesta;
    }

    @Override
    public int actualizarPuntaje(int puntaje) {
        return tipoDePenalidad.actualizarPuntaje(puntaje);
    }

    @Override
    public Boolean EsCorrecta(){
        return false;
    }

    @Override
    public int TieneOrdenCorrecto(Respuesta unaRespuesta){
        return 0;
    }

    @Override
    public String getEnunciado(){
        return enunciado;
    }

    @Override
    public String getOrdenParcial(){
        return "0"; //el cero indica que NO hay orden parcial.
    }

}
