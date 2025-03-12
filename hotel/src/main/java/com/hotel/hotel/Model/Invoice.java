package com.hotel.hotel.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "invoices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Booking ID is required")
    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @NotNull(message = "User ID is required")
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull(message = "Invoice date is required")
    @Column(name = "invoice_date")
    private LocalDateTime invoiceDate;

    @NotNull(message = "Total amount is required")
    @DecimalMin(value = "0.0", message = "Total amount must be positive")
    @Column(name = "total_amount")
    private Double totalAmount;

    @NotNull(message = "Payment status is required")
    @Pattern(regexp = "^(PENDING|PAID|CANCELLED|REFUNDED)$", 
            message = "Payment status must be either PENDING, PAID, CANCELLED, or REFUNDED")
    @Column(name = "payment_status")
    private String paymentStatus;

    @Pattern(regexp = "^(CASH|CREDIT_CARD|DEBIT_CARD|BANK_TRANSFER)$", 
            message = "Payment method must be either CASH, CREDIT_CARD, DEBIT_CARD, or BANK_TRANSFER")
    @Column(name = "payment_method")
    private String paymentMethod;

    @Size(max = 500, message = "Notes cannot exceed 500 characters")
    @Column(name = "notes")
    private String notes;

    @Column(name = "tax_amount")
    private Double taxAmount;

    @Column(name = "discount_amount")
    private Double discountAmount;

    @Column(name = "payment_date")
    private LocalDateTime paymentDate;

    @PrePersist
    protected void onCreate() {
        if (invoiceDate == null) {
            invoiceDate = LocalDateTime.now();
        }
        if (paymentStatus == null) {
            paymentStatus = "PENDING";
        }
        if (taxAmount == null) {
            taxAmount = 0.0;
        }
        if (discountAmount == null) {
            discountAmount = 0.0;
        }
    }
}
