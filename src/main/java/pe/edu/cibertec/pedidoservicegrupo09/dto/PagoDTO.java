package pe.edu.cibertec.pedidoservicegrupo09.dto;

import java.time.LocalDateTime;

public record PagoDTO(
        Long idPago,
        Long idPedido,
        String metodoPago,
        Double montoPagado,
        LocalDateTime fechaPago,
        String codigoAutorizacion,
        String estadoPago
) {
}
