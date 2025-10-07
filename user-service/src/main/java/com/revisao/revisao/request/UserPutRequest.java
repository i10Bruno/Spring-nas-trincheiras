package com.revisao.revisao.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPutRequest {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

}
