package org.ramer.demo.domain;

import lombok.*;
import net.bytebuddy.asm.Advice;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = { "addresses", "roles" })
@ToString(exclude = { "addresses", "roles" })
@Entity
public class User{
    private @Id @GeneratedValue Integer id;
    private String username;
    private String password;
    private Date createTime = new Date();
    @OneToMany(mappedBy = "user")
    private List<Address> addresses;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Roles> roles;

    public User(Integer id, String username, String password, Date createTime) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.createTime = createTime;
    }
}
