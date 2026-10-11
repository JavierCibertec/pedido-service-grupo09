package pe.edu.cibertec.pedidoservicegrupo09.remoteservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pe.edu.cibertec.pedidoservicegrupo09.dto.PagoDTO;

import java.util.List;

@FeignClient(name = "pago-service-grupo09")
public interface PagoClient {

    //localhost:8072/api/v1/pagos/pedido/{idPedido}
    @GetMapping("/api/v1/pagos/pedido/{idPedido}")
    PagoDTO getPagoPorPedido(@PathVariable("idPedido") Long idPedido);

    //localhost:8072/api/v1/pagos/cliente/{clienteId}
    @GetMapping("/api/v1/pagos/cliente/{clienteId}")
    List<PagoDTO> getPagosPorCliente(@PathVariable("clienteId") Long clienteId);

}
