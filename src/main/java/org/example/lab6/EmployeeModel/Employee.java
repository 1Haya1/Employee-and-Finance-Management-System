package org.example.lab6.EmployeeModel;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;


import java.time.LocalDate;
@Data
@AllArgsConstructor
public class Employee {



    @NotNull(message = "id cant be null")
    @Size(min = 3,message = "length must be more than 2 characters")
    private String id;

    @NotNull(message = "cant be null")
    @Size(min = 5,message = "length must be more than 4 characters")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Must contain only characters")
    private String name;

    @Email(message = "must be a valid email format")
    private String email;


    @NotNull(message = "cant be null")
    @Pattern(regexp = "^05\\d{8}$", message = "Must start with '05' and consists of exactly 10 digits")
    private String phonenumber;


    @NotNull(message = "cant be null")
    @Size(min = 26,message = "must be more than 25")
    private Integer age;


    @NotNull(message = "cant be null")
    @Pattern(regexp = "^(supervisor|coordinator)$", message = "Must be either 'supervisor' or 'coordinator' only")
    private String position;


    @NotNull
    private boolean onleave=false;

     @NotNull(message = "cant be null")
     @PastOrPresent(message = "should be in the past or present")
     @Min(1900)
     private LocalDate hiredate;

    @NotNull(message = "cant be null")
    @Positive(message = "must be a positive number")
    private Integer anuualleave;













}
