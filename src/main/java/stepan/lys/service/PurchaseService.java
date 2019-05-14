package stepan.lys.service;

import stepan.lys.model.Purchase;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface PurchaseService {
    Object add(Purchase purchase);

    Map<LocalDate, List<String>> getAll();

    Map<LocalDate, List<String>> deleteByDate(LocalDate date);

    String getReport(String year, String currency);

}
