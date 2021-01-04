package com.edu.springbootcsv;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
    private Long id;
//    private String firstName;
//    private Integer age;
//    private String  lastName;
//    private String legalId;
    private String emailAddress;
    private String name;
    private String purchasedPackage;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StudentDto{");
        sb.append("id=").append(id);
        sb.append(", emailAddress='").append(emailAddress).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", purchasedPackage='").append(purchasedPackage).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
