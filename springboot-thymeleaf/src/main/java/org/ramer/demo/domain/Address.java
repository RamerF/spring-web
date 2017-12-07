package org.ramer.demo.domain;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = { "user" })
@ToString(exclude = { "user" })
@Entity
public class Address{
    private @Id @GeneratedValue Integer id;
    private String location;
    @ManyToOne
    @JoinColumn(name = "user")
    private User user;
}
