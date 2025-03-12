package com.hotel.hotel.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.hotel.hotel.Model.Invoice;
import com.hotel.hotel.Repository.InvoiceRepository;

@Service
public class InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public Optional<Invoice> getInvoiceById(Long id) {
        return invoiceRepository.findById(id);
    }

    public Invoice createInvoice(Invoice invoice) {
        validateInvoice(invoice);
        invoice.setInvoiceDate(LocalDateTime.now());
        if (invoice.getPaymentStatus() == null) {
            invoice.setPaymentStatus("PENDING");
        }
        calculateTotalAmount(invoice);
        return invoiceRepository.save(invoice);
    }

    public Invoice updateInvoice(Long id, Invoice invoiceDetails) {
        Optional<Invoice> invoiceOpt = invoiceRepository.findById(id);
        if (invoiceOpt.isPresent()) {
            validateInvoice(invoiceDetails);
            Invoice existingInvoice = invoiceOpt.get();
            
            // Update basic information
            if (invoiceDetails.getBooking() != null) {
                existingInvoice.setBooking(invoiceDetails.getBooking());
            }
            if (invoiceDetails.getUser() != null) {
                existingInvoice.setUser(invoiceDetails.getUser());
            }
            if (invoiceDetails.getTotalAmount() != null) {
                existingInvoice.setTotalAmount(invoiceDetails.getTotalAmount());
            }
            if (invoiceDetails.getPaymentStatus() != null) {
                existingInvoice.setPaymentStatus(invoiceDetails.getPaymentStatus());
                if (invoiceDetails.getPaymentStatus().equals("PAID")) {
                    existingInvoice.setPaymentDate(LocalDateTime.now());
                }
            }
            if (invoiceDetails.getPaymentMethod() != null) {
                existingInvoice.setPaymentMethod(invoiceDetails.getPaymentMethod());
            }
            if (invoiceDetails.getNotes() != null) {
                existingInvoice.setNotes(invoiceDetails.getNotes());
            }
            if (invoiceDetails.getTaxAmount() != null) {
                existingInvoice.setTaxAmount(invoiceDetails.getTaxAmount());
            }
            if (invoiceDetails.getDiscountAmount() != null) {
                existingInvoice.setDiscountAmount(invoiceDetails.getDiscountAmount());
            }
            
            calculateTotalAmount(existingInvoice);
            return invoiceRepository.save(existingInvoice);
        }
        return null;
    }

    public void deleteInvoice(Long id) {
        if (!invoiceRepository.existsById(id)) {
            throw new IllegalArgumentException("Invoice not found with id: " + id);
        }
        invoiceRepository.deleteById(id);
    }

    public List<Invoice> getInvoicesByUserId(Long userId) {
        return invoiceRepository.findByUserId(userId);
    }

    public List<Invoice> getInvoicesByBookingId(Long bookingId) {
        return invoiceRepository.findByBookingId(bookingId);
    }

    public List<Invoice> getInvoicesByStatus(String status) {
        validatePaymentStatus(status);
        return invoiceRepository.findByPaymentStatus(status.toUpperCase());
    }

    public List<Invoice> getInvoicesByPaymentMethod(String method) {
        validatePaymentMethod(method);
        return invoiceRepository.findByPaymentMethod(method.toUpperCase());
    }

    public List<Invoice> getInvoicesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
        return invoiceRepository.findByInvoiceDateBetween(startDate, endDate);
    }

    public List<Invoice> getInvoicesByAmountRange(Double minAmount, Double maxAmount) {
        if (minAmount > maxAmount) {
            throw new IllegalArgumentException("Minimum amount must be less than maximum amount");
        }
        return invoiceRepository.findByTotalAmountBetween(minAmount, maxAmount);
    }

    private void validateInvoice(Invoice invoice) {
        if (invoice.getBooking() == null) {
            throw new IllegalArgumentException("Booking is required");
        }
        if (invoice.getUser() == null) {
            throw new IllegalArgumentException("User is required");
        }
        if (invoice.getTotalAmount() != null && invoice.getTotalAmount() < 0) {
            throw new IllegalArgumentException("Total amount must be positive");
        }
        if (invoice.getPaymentStatus() != null) {
            validatePaymentStatus(invoice.getPaymentStatus());
        }
        if (invoice.getPaymentMethod() != null) {
            validatePaymentMethod(invoice.getPaymentMethod());
        }
        if (invoice.getNotes() != null && invoice.getNotes().length() > 500) {
            throw new IllegalArgumentException("Notes cannot exceed 500 characters");
        }
        if (invoice.getTaxAmount() != null && invoice.getTaxAmount() < 0) {
            throw new IllegalArgumentException("Tax amount cannot be negative");
        }
        if (invoice.getDiscountAmount() != null && invoice.getDiscountAmount() < 0) {
            throw new IllegalArgumentException("Discount amount cannot be negative");
        }
    }

    private void validatePaymentStatus(String status) {
        if (status == null || !status.matches("^(PENDING|PAID|CANCELLED|REFUNDED)$")) {
            throw new IllegalArgumentException("Payment status must be either PENDING, PAID, CANCELLED, or REFUNDED");
        }
    }

    private void validatePaymentMethod(String method) {
        if (method == null || !method.matches("^(CASH|CREDIT_CARD|DEBIT_CARD|BANK_TRANSFER)$")) {
            throw new IllegalArgumentException("Payment method must be either CASH, CREDIT_CARD, DEBIT_CARD, or BANK_TRANSFER");
        }
    }

    private void calculateTotalAmount(Invoice invoice) {
        double baseAmount = invoice.getTotalAmount() != null ? invoice.getTotalAmount() : 0.0;
        double taxAmount = invoice.getTaxAmount() != null ? invoice.getTaxAmount() : 0.0;
        double discountAmount = invoice.getDiscountAmount() != null ? invoice.getDiscountAmount() : 0.0;
        
        invoice.setTotalAmount(baseAmount + taxAmount - discountAmount);
    }
}
