package stepan.lys.service.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static stepan.lys.constants.FixerIoConst.FIXERIO_URL;

@Component
public class FixerIoClient {

    private RestTemplate restTemplate;
    private Map rates;
    private Set currency;

    public FixerIoClient() {
        restTemplate = new RestTemplate();
    }

    public Map getRates() {
        try {
            rates = (Map) Objects.requireNonNull(
                    restTemplate.getForObject(new URI(FIXERIO_URL), Map.class)
            ).get("rates");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return rates;
    }

    public Set getCurrency() {
        currency = getRates().keySet();

        return currency;
    }
}
