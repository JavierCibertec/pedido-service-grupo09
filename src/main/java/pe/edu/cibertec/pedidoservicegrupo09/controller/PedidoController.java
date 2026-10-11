package pe.edu.cibertec.pedidoservicegrupo09.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.cibertec.pedidoservicegrupo09.dto.PedidoResponseDTO;
import pe.edu.cibertec.pedidoservicegrupo09.dto.ResumenConsolidadoClienteDTO;
import pe.edu.cibertec.pedidoservicegrupo09.service.PedidoService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    //localhost:8070/api/v1/pedidos/{idPedido}
    @GetMapping("/{idPedido}")
    public ResponseEntity<PedidoResponseDTO> getPedido(@PathVariable Long idPedido) {
        return ResponseEntity.ok(pedidoService.obtenerPedido(idPedido));
    }

    //localhost:8070/api/v1/pedidos/verificar-y-completar/{idPedido}
    @GetMapping("/verificar-y-completar/{idPedido}")
    public ResponseEntity<PedidoResponseDTO> verificarYCompletar(@PathVariable Long idPedido) {
        return ResponseEntity.ok(pedidoService.verificarYCompletar(idPedido));
    }

    //localhost:8070/api/v1/pedidos/consolidado-cliente/{clienteId}
    @GetMapping("/consolidado-cliente/{clienteId}")
    public ResponseEntity<ResumenConsolidadoClienteDTO> consolidadoCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(pedidoService.consolidadoCliente(clienteId));
    }
}
