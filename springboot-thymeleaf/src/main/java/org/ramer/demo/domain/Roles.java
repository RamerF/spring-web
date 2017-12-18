package org.ramer.demo.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Created by RAMER on 5/22/2017.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = { "privileges", "user" })
@ToString(exclude = { "privileges", "user" })
public class Roles{
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "role_privileges", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id"))
    private List<Privilege> privileges;

    public Roles(String name) {
        this.name = name;
    }

}
