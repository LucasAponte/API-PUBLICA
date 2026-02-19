package ar.utn.ba.ddsi.apipublica.monitoring.healthindicators;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

@Slf4j
public abstract class AbstractDependencyHealthIndicator implements HealthIndicator {

    private boolean forceDown = false;

    @Override
    public Health health() {
        if (forceDown) {
            log.warn("Estado DOWN forzado por un ADMIN para '{}'", dependencyName());
            return markDown();
        }
        try {
            if (estaDisponible()) {
                return markUp();
            }

            log.warn("Dependency '{}' reported DOWN: {}", dependencyName(), downMessage());
            return markDown();

        } catch (Exception ex) {
            log.error("Error comprobando dependencia '{}': {}", dependencyName(), ex.getMessage(), ex);
            return Health.down(ex)
                    .withDetail(dependencyName(), downMessage())
                    .build();
        }
    }

    protected abstract String dependencyName();
    protected abstract String downMessage();

    public abstract boolean estaDisponible();

    public Health markDown() {
        return Health.down()
                .withDetail(dependencyName(), downMessage())
                .build();
    }

    public Health markUp() {
        return Health.up()
                .withDetail(dependencyName(), "OK")
                .build();
    }
    public void forceDown() {
        log.warn("Forzando estado DOWN para '{}'", dependencyName());
        this.forceDown= true;
        // En este diseño, el estado forzado se maneja a través de un flag que se verifica en el método health()
        // Esto permite que el estado forzado persista hasta que se recupere manualmente o se reinicie el servicio
    }
    public void recover(){
        log.info("Recuperando estado para '{}'", dependencyName());
        this.forceDown= false;
        // En este diseño, la recuperación se maneja a través de un método que podría resetear el flag de forzado
        // y permitir que el health check normal vuelva a determinar el estado real de la dependencia
    }
}
