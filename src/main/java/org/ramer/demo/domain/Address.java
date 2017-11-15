package org.ramer.demo.domain;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = { "user" })
@ToString(exclude = { "user" })
public class Address{
    private Integer id;
    private String location;

    private User user;
}
