package pe.edu.cibertec.pedidoservicegrupo09.dto;

public record ConfiguracionDTO(
        Double impuestoIgv,
        Double montoMinimoEnvioGratis,
        String mensajeBienvenida,
        String datasourceUrl
) {
}
