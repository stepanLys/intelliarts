package stepan.lys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stepan.lys.model.Purchase;
import stepan.lys.repository.PurchaseRepository;
import stepan.lys.service.utils.FixerIoClient;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.aspectj.runtime.internal.Conversions.doubleValue;

@Service
public class PurchaseServiceImpl implements stepan.lys.service.PurchaseService {

    private final PurchaseRepository repository;
    private final FixerIoClient fixerIoClient;

    @Autowired
    public PurchaseServiceImpl(PurchaseRepository repository, FixerIoClient fixerIoClient) {
        this.repository = repository;
        this.fixerIoClient = fixerIoClient;
    }

    @Override
    public Object add(Purchase purchase) {
        if (purchase.getDate() == null) {
            purchase.setDate(LocalDate.now());
        }
        if (!fixerIoClient.getCurrency().contains(purchase.getCurrency())) {
            return "Incorrect currency";
        }

        repository.save(purchase);

        return getAll();
    }

    @Override
    public Map<LocalDate, List<String>> getAll() {
        List<Purchase> all = repository.findAll();
        Map<LocalDate, List<String>> byDate = new LinkedHashMap<>();

        all.forEach(i -> {
            if (!byDate.containsKey(i.getDate())) {
                byDate.put(i.getDate(), new ArrayList<>(
                        Collections.singletonList(i.getName() + " "
                                + i.getPrice().toString() + " "
                                + i.getCurrency())));
            } else {
                byDate.get(i.getDate())
                        .add(i.getName() + " "
                                + i.getPrice().toString() + " "
                                + i.getCurrency());
            }
        });

        return byDate.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    @Override
    public Map<LocalDate, List<String>> deleteByDate(LocalDate date) {
        repository.deleteAllByDateEquals(date);

        return getAll();
    }

    @Override
    public String getReport(String year, String currency) {
        double report;
        Map rates = fixerIoClient.getRates();

        List<Purchase> all = repository.findAll();

        report = all.stream()
                .filter(i -> i.getDate().toString().contains(year))
                .mapToDouble(i -> i.getPrice()
                        * doubleValue(rates.get(currency))
                        / doubleValue(rates.get(i.getCurrency()))).sum();

        return Math.round(report * 100.0) / 100.0 + " " + currency;
    }

}
