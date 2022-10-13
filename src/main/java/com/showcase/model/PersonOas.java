package com.showcase.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonOas {

    private String id;
    private String createdAt;
    private String userName;
    private String bankAccount;
    private String status;
}
