package com.shiftsystem.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "advisers")
public class Adviser {

    @Id
    private String Id;
    @NotEmpty
    private String firstname;
    @NotEmpty
    private String lastname;

}
