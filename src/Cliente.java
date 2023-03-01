public class Cliente {

    private String nombre;
    private String cedula;
    private int saldo;

    public Cliente(String nombre, String cedula, int saldo) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.saldo = saldo;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}