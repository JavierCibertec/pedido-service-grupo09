package pe.edu.cibertec.pedidoservicegrupo09.dto;

public record DetallePedidoDTO(
        Long idProducto,
        String nombreProducto,
        Integer cantidad,
        Double precioUnitario,
        Double subtotal
) {
}
