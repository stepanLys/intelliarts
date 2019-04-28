package stepan.lys.service;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stepan.lys.model.Purchase;
import stepan.lys.repository.PurchaseRepository;

import java.time.LocalDate;
import java.util.*;

@Service
public class PurchaseService {

    private final PurchaseRepository repository;

    @Autowired
    public PurchaseService(PurchaseRepository repository) {
        this.repository = repository;
    }

    public Map<LocalDate, List<String>> add(Purchase purchase) {
        if (purchase.getDate() == null) {
            purchase.setDate(LocalDate.now());
        }
        repository.save(purchase);

        return getAll();
    }

    public Map<LocalDate, List<String>> getAll() {
        List<Purchase> all = repository.findAll();
        Map<LocalDate, List<String>> byDate = new HashMap<>();

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

        return byDate;
    }

    public Map<LocalDate, List<String>> deleteByDate(LocalDate date) {
        repository.deleteAllByDateEquals(date);

        return getAll();
    }

    public Double getReport(int year, String currency) {
        double report = 0;



        return report;
    }

}
