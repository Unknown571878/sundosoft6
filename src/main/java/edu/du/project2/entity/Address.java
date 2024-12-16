package edu.du.project2.entity;

import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Data
@Getter
@Setter
@Embeddable
@NoArgsConstructor
public class Address {

    @NotBlank(message = "주소를 입력해주세요")
    private String zipcode;
    private String address;
    private String detailAddr;


    @Builder
    public Address(String zipcode, String address, String detailAddr) {
        this.zipcode = zipcode;
        this.address = address;
        this.detailAddr = detailAddr;
    }

}
