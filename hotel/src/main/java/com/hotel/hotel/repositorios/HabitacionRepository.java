package main.java.com.hotel.hotel.repositorios;

import com.hotel.hotel.entidades.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitacionRepository extends JpaRepository<Habitacion, Integer> {
}
