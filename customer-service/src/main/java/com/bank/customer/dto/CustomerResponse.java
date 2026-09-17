package com.bank.customer.dto;

import com.bank.customer.entity.CustomerStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

public class CustomerResponse {

    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private int age;
    private String address;
    private String panNumber;
    private LocalDateTime registrationDate;
    private CustomerStatus status;

    public CustomerResponse() {
    }

    public CustomerResponse(Long id, String fullName, String email, String phone, LocalDate dateOfBirth,
                             String address, String panNumber, LocalDateTime registrationDate, CustomerStatus status) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        // Java 8 java.time.Period used to derive age instead of manual date math
        this.age = dateOfBirth == null ? 0 : Period.between(dateOfBirth, LocalDate.now()).getYears();
        this.address = address;
        this.panNumber = panNumber;
        this.registrationDate = registrationDate;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPanNumber() { return panNumber; }
    public void setPanNumber(String panNumber) { this.panNumber = panNumber; }

    public LocalDateTime getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(LocalDateTime registrationDate) { this.registrationDate = registrationDate; }

    public CustomerStatus getStatus() { return status; }
    public void setStatus(CustomerStatus status) { this.status = status; }
}
