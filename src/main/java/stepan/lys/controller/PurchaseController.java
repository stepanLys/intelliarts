package stepan.lys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stepan.lys.model.Purchase;
import stepan.lys.service.PurchaseService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping
    public ResponseEntity<List<Purchase>> create(@RequestBody Purchase purchase) {
        return new ResponseEntity<>(
                purchaseService.add(purchase),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<Purchase>> all() {
        return new ResponseEntity<>(
                purchaseService.getAll(),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{date}")
    public ResponseEntity<List<Purchase>> clear(@PathVariable String date) {
        return new ResponseEntity<>(
                purchaseService.deleteByDate(LocalDate.parse(date)),
                HttpStatus.OK
        );
    }
}