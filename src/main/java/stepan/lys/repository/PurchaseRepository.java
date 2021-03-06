package stepan.lys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import stepan.lys.model.Purchase;

import java.time.LocalDate;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    @Transactional
    @Modifying
    @Query(value = "delete from Purchase p where p.date = ?1")
    void deleteAllByDateEquals(LocalDate date);
}
