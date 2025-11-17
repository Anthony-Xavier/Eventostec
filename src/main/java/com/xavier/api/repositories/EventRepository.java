package com.xavier.api.repositories;

import com.xavier.api.domain.event.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {

    @Query("SELECT e FROM Event e LEFT JOIN FETCH e.address a  WHERE e.data >= :data")
    Page<Event> findUpcomingEvents(@Param("data") Date data, Pageable pageable);


    @Query("SELECT e FROM Event e " +
            "JOIN e.address a " +
            "WHERE (:city = '' OR a.city LIKE %:city%) " +
            "AND (:uf = '' OR a.uf LIKE %:uf%) " +
            "AND e.data BETWEEN :startDate AND :endDate")
    Page<Event> findFilteredEvents(
            @Param("city") String city,
            @Param("uf") String uf,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate,
            Pageable pageable
    );


}
