package com.ludo.mspatient.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "patient", uniqueConstraints =
@UniqueConstraint(name = "UniqueFirstNameAndLastNameAndBirthDate", columnNames = {"first_name", "last_name", "birthdate"})
)
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank(message = "FirstName should not be blank")
    @NotNull
    @Column(name="first_name")
    private String firstName;

    @NotBlank(message = "LastName should not be blank")
    @NotNull
    @Column(name="last_name")
    private String lastName;

    @NotNull(message = "birthdate should not be blank")
    @Column(name="birthdate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;

    @NotNull(message = "Sex should not be blank")
    @Column(name="sex", length = 1)
    private String sex;

    @Column(name="address")
    private String address;

    @Column(name="phone")
    private String phone;

}
