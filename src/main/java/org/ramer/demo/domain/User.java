package org.ramer.demo.domain;

import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = { "addresses" })
@ToString(exclude = { "addresses" })
public class User{
    private Integer id;
    private String username;
    private String password;
    private Date createTime;

    private List<Address> addresses;

    public User(Integer id, String username, String password, Date createTime) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.createTime = createTime;
    }
}
