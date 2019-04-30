package stepan.lys.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false, nullable = false)
    private LocalDate date;

    private Double price;
    private String currency;
    private String name;

    public Purchase(LocalDate date, Double price, String currency, String name) {
        this.date = date;
        this.price = price;
        this.currency = currency;
        this.name = name;
    }
}
