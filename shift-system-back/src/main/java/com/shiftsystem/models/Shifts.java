package com.shiftsystem.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "shifts")
public class Shifts {

    @Id
    private String id;
    @NotEmpty
    private String categoryId;
    @Min(1)
    private Long consecutive;
    @NotEmpty
    private String status;
    @NotEmpty
    private Date createdAt;
    @NotEmpty
    private Date attendedAt;
    @NotEmpty
    private Date finishedAt;
    private String adviserId;


}
