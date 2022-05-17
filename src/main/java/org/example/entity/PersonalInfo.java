package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class PersonalInfo implements Serializable {

    private static final long serialVersionUID = -7609192259415081667L;
    private String first_name;
    private String last_name;

    //    @Convert(converter = BirthdayConverter.class)
    private Birthday birthDate;
}