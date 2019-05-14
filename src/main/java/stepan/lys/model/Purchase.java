package stepan.lys.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @Column(updatable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotNull(message = "You must enter price. For example: 15, 12.45, etc.")
    private Double price;
    @NotNull(message = "You must be enter currency. For example: USD, UAH, EUR, etc.")
    private String currency;
    @NotNull(message = "You must be enter purchase. For example: bag, hat, my new purchase, etc.")
    private String name;

    public Purchase(LocalDate date, Double price, String currency, String name) {
        this.date = date;
        this.price = price;
        this.currency = currency;
        this.name = name;
    }
}
