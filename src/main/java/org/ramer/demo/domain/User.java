package org.ramer.demo.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = { "addresses" })
@ToString(exclude = { "addresses" })
@Entity
public class User{
    private @Id @GeneratedValue Integer id;
    private String username;
    private String password;
    private Date createTime = new Date();
    @OneToMany(mappedBy = "user")
    private List<Address> addresses;

    public User(Integer id, String username, String password, Date createTime) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.createTime = createTime;
    }
}
