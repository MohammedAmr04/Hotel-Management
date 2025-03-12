package com.hotel.hotel.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hotel.hotel.Model.Invoice;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByUserId(Long userId);
    List<Invoice> findByBookingId(Long bookingId);
    List<Invoice> findByPaymentStatus(String status);
    List<Invoice> findByPaymentMethod(String method);
    List<Invoice> findByInvoiceDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<Invoice> findByTotalAmountBetween(Double minAmount, Double maxAmount);
}

