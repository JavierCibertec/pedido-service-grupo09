package pe.edu.cibertec.pedidoservicegrupo09.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

// Clase (no record) porque estadoPedido cambia a COMPLETADO
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoResponseDTO {
    private Long idPedido;
    private String codigoSeguimiento;
    private LocalDateTime fechaEmision;
    private Long clienteId;
    private String estadoPedido;
    private List<DetallePedidoDTO> listaDetalles;
    private Double montoTotalCalculado;
}
