package pe.edu.cibertec.pedidoservicegrupo09.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.cibertec.pedidoservicegrupo09.dto.ConfiguracionDTO;

@RefreshScope
@RestController
@RequestMapping("/api/v1/pedidos")
public class ConfiguracionController {

    @Value("${ecom.impuesto-igv}")
    private Double impuestoIgv;

    @Value("${ecom.monto-minimo-envio-gratis}")
    private Double montoMinimoEnvioGratis;

    @Value("${ecom.mensaje-bienvenida}")
    private String mensajeBienvenida;

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    //localhost:8070/api/v1/pedidos/configuracion
    @GetMapping("/configuracion")
    public ResponseEntity<ConfiguracionDTO> getConfiguracion() {
        return ResponseEntity.ok(new ConfiguracionDTO(impuestoIgv,
                montoMinimoEnvioGratis, mensajeBienvenida, datasourceUrl));
    }
}
