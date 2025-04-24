package com.talentreef.interviewquestions.takehome.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Table
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder(toBuilder=true)
@ToString
public class Widget {
  @Valid

  @NotNull
  @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
  private String name;

  @NotNull
  @Size(min = 5, max = 1000, message = "Description must be between 3 and 100 characters")
  private String description;

  @DecimalMax(value = "20000.00", message = "Price must not exceed 20000")
  @DecimalMin(value = "1.00", message = "Price must not be less than 1")
  @Digits(integer = 5, fraction = 2, message = "Price must have up to 2 decimal places")
  private double price;

}
