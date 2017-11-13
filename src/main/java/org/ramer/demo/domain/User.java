package org.ramer.demo.domain;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User{
    private Integer id;
    private String username;
    private String password;
}
