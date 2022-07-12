package pe.com.nttdata.gateway.banking.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Component
public class ControlGatewayFilterFactory implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(ControlGatewayFilterFactory.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("[PRE-FILTER]");

        String idTransaction = UUID.randomUUID().toString();
        log.info("[idTransaction]: " + idTransaction);
        exchange.getRequest().mutate().headers(h -> h.add("idTransaction", idTransaction));
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String timestamp = df.format(new Date());
        exchange.getRequest().mutate().headers(h -> h.add("timestamp", timestamp));
        String clientId;

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            log.info("[POST-FILTER]");
            Optional.ofNullable(exchange.getRequest().getHeaders().getFirst("idTransaction"))
                    .ifPresent(value -> {
                        exchange.getResponse().getHeaders().add("idTransaction", idTransaction);
            });
            Optional.ofNullable(exchange.getRequest().getHeaders().getFirst("timestamp"))
                    .ifPresent(value -> {
                        exchange.getResponse().getHeaders().add("timestamp", timestamp);
            });
        }));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
