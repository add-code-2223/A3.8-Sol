package exceptions;



import java.math.BigDecimal;

/**
 *
 * @author maria
 */
public class SaldoInsuficienteException extends Exception {

    private static final long serialVersionUID = 1L;
	private BigDecimal saldoActual;
    private BigDecimal cantidad;

    public SaldoInsuficienteException(String string, BigDecimal saldoActual, BigDecimal cantidad) {
        super(String.format(string, saldoActual, cantidad));
        this.saldoActual = saldoActual;
        this.cantidad = cantidad;
    }

}