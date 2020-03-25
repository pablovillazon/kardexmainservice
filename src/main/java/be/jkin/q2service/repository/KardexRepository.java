package be.jkin.q2service.repository;

import be.jkin.q2service.model.Kardex;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KardexRepository  extends JpaRepository<Kardex, Long> {
}
