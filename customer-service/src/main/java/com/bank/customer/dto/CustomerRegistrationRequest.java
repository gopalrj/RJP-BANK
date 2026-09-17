package com.bank.customer.dto;

import javax.validation.constraints.*;
import java.time.LocalDate;

public class CustomerRegistrationRequest {

    @NotBlank(message = "fullName is required")
    private String fullName;

    @NotBlank(message = "email is required")
    @Email(message = "email must be valid")
    private String email;

    @NotBlank(message = "phone is required")
    @Pattern(regexp = "\\d{10}", message = "phone must be a 10 digit number")
    private String phone;

    @NotNull(message = "dateOfBirth is required")
    @Past(message = "dateOfBirth must be in the past")
    private LocalDate dateOfBirth;

    private String address;

    @NotBlank(message = "panNumber is required")
    @Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-Z]{1}", message = "panNumber must follow the ABCDE1234F format")
    private String panNumber;

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPanNumber() { return panNumber; }
    public void setPanNumber(String panNumber) { this.panNumber = panNumber; }
}
