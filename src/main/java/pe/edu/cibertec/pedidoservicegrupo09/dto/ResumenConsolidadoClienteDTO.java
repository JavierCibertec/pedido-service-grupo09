package pe.edu.cibertec.pedidoservicegrupo09.dto;

import java.util.List;

public record ResumenConsolidadoClienteDTO(
        Long clienteId,
        String nombreCliente,
        Integer totalPedidosRealizados,
        Double montoTotalAcumulado,
        List<PedidoResponseDTO> historialPedidos,
        List<PagoDTO> historialPagos,
        String estadoCuenta
) {
}
