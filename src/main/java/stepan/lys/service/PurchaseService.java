package stepan.lys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stepan.lys.model.Purchase;
import stepan.lys.repository.PurchaseRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class PurchaseService {

    private final PurchaseRepository repository;

    @Autowired
    public PurchaseService(PurchaseRepository repository) {
        this.repository = repository;
    }

    public List<Purchase> add(Purchase purchase) {
        if (purchase.getDate() == null) {
            purchase.setDate(LocalDate.now());
        }
        repository.save(purchase);

        return getAll();
    }

    public List<Purchase> getAll() {
        return repository.findAll();
    }

    public List<Purchase> deleteByDate(LocalDate date) {
        repository.deleteAllByDateEquals(date);

        return getAll();
    }

}
