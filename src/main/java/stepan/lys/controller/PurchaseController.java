package stepan.lys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stepan.lys.model.Purchase;
import stepan.lys.service.PurchaseService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping
    public ResponseEntity<Map<LocalDate, List<String>>> create(@RequestBody Purchase purchase) {
        return new ResponseEntity<>(
                purchaseService.add(purchase),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<Map<LocalDate, List<String>>> all() {
        return new ResponseEntity<>(
                purchaseService.getAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("/report")
    public ResponseEntity<String> report(@RequestParam(defaultValue = "2019") String year,
                                         @RequestParam(defaultValue = "UAH") String currency) {
        return new ResponseEntity<>(
                purchaseService.getReport(year, currency.toUpperCase()),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{date}")
    public ResponseEntity<Map<LocalDate, List<String>>> clear(@PathVariable String date) {
        return new ResponseEntity<>(
                purchaseService.deleteByDate(LocalDate.parse(date)),
                HttpStatus.OK
        );
    }
}
