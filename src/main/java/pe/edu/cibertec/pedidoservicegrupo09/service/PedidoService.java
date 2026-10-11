package pe.edu.cibertec.pedidoservicegrupo09.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pe.edu.cibertec.pedidoservicegrupo09.dto.DetallePedidoDTO;
import pe.edu.cibertec.pedidoservicegrupo09.dto.PagoDTO;
import pe.edu.cibertec.pedidoservicegrupo09.dto.PedidoResponseDTO;
import pe.edu.cibertec.pedidoservicegrupo09.dto.ResumenConsolidadoClienteDTO;
import pe.edu.cibertec.pedidoservicegrupo09.remoteservice.PagoClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Service
public class PedidoService {

    private final PagoClient pagoClient;

    // Datos en memoria (sin BD)
    private final Map<Long, String> clientes = Map.of(
            1L, "Juan Perez",
            2L, "Maria Lopez");

    private final Map<Long, PedidoResponseDTO> pedidos = new ConcurrentHashMap<>(Map.of(
            1L, new PedidoResponseDTO(1L, "TRK-G09-0001",
                    LocalDateTime.of(2026, 6, 1, 10, 30), 1L, "PENDIENTE",
                    List.of(detalle(1L, "Laptop", 1, 2500.00),
                            detalle(2L, "Mouse", 2, 50.00)), null),
            2L, new PedidoResponseDTO(2L, "TRK-G09-0002",
                    LocalDateTime.of(2026, 6, 2, 15, 0), 1L, "PENDIENTE",
                    List.of(detalle(3L, "Monitor", 1, 800.00)), null),
            3L, new PedidoResponseDTO(3L, "TRK-G09-0003",
                    LocalDateTime.of(2026, 6, 3, 9, 45), 2L, "PENDIENTE",
                    List.of(detalle(4L, "Teclado", 3, 120.00)), null)));

    public PedidoResponseDTO obtenerPedido(Long idPedido) {
        PedidoResponseDTO pedido = pedidos.get(idPedido);
        if (pedido == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Pedido " + idPedido + " no encontrado");
        }
        // Calculo dinamico: suma de subtotales
        pedido.setMontoTotalCalculado(calcularMontoTotal(pedido.getListaDetalles()));
        return pedido;
    }

    public PedidoResponseDTO verificarYCompletar(Long idPedido) {
        PedidoResponseDTO pedido = obtenerPedido(idPedido);
        PagoDTO pago = pagoClient.getPagoPorPedido(idPedido);

        if (pago != null
                && "APROBADO".equals(pago.estadoPago())
                && montosIguales(pago.montoPagado(), pedido.getMontoTotalCalculado())) {
            pedido.setEstadoPedido("COMPLETADO");
        }
        return pedido;
    }

    public ResumenConsolidadoClienteDTO consolidadoCliente(Long clienteId) {
        String nombreCliente = clientes.get(clienteId);
        if (nombreCliente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Cliente " + clienteId + " no encontrado");
        }

        List<PedidoResponseDTO> historialPedidos = pedidos.values().stream()
                .filter(p -> p.getClienteId().equals(clienteId))
                .map(p -> obtenerPedido(p.getIdPedido()))
                .toList();

        List<PagoDTO> historialPagos = pagoClient.getPagosPorCliente(clienteId);

        double montoTotalAcumulado = historialPedidos.stream()
                .mapToDouble(PedidoResponseDTO::getMontoTotalCalculado)
                .sum();
        double totalPagadoAprobado = historialPagos.stream()
                .filter(p -> "APROBADO".equals(p.estadoPago()))
                .mapToDouble(PagoDTO::montoPagado)
                .sum();

        // AL_DIA: los pagos aprobados cubren el total de sus pedidos
        String estadoCuenta = totalPagadoAprobado >= montoTotalAcumulado - 0.01
                ? "AL_DIA" : "CON_DEUDAS";

        return new ResumenConsolidadoClienteDTO(clienteId, nombreCliente,
                historialPedidos.size(), montoTotalAcumulado,
                historialPedidos, historialPagos, estadoCuenta);
    }

    private static DetallePedidoDTO detalle(Long idProducto, String nombre,
                                            Integer cantidad, Double precioUnitario) {
        return new DetallePedidoDTO(idProducto, nombre, cantidad,
                precioUnitario, cantidad * precioUnitario);
    }

    private Double calcularMontoTotal(List<DetallePedidoDTO> detalles) {
        return detalles.stream()
                .mapToDouble(DetallePedidoDTO::subtotal)
                .sum();
    }

    private boolean montosIguales(Double a, Double b) {
        return a != null && b != null && Math.abs(a - b) < 0.01;
    }
}
