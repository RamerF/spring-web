package org.ramer.demo.domain;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"addresses", "roles", "locations"})
@ToString(exclude = {"addresses", "roles", "locations"})
@Entity
public class User {
  private @Id
  @GeneratedValue
  Long id;
  private String username;
  private String password;
  @CreationTimestamp
  private Date createTime = new Date();
  @OneToMany(mappedBy = "user")
  private List<Address> addresses;
  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
  private List<Roles> roles;
  @OneToMany(mappedBy = "user")
  private List<Location> locations;

  public User(Long id, String username, String password, Date createTime) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.createTime = createTime;
  }
}
