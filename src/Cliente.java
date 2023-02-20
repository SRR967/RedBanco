public class Cliente {
    private String numeroCuenta;
    private String nombre;
    private String cedula;
    private int saldo;

    public Cliente(String numeroCuenta, String nombre, String cedula, int saldo) {
        this.numeroCuenta = numeroCuenta;
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

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}