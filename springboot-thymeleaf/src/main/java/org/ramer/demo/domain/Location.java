package org.ramer.demo.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = { "user" })
@ToString(exclude = { "user" })
@Entity
public class Location{
    private @Id @GeneratedValue Integer id;
    // 中国
    private String country;
    // 西南
    private String area;
    // 四川省
    private String region;
    // 成都市
    private String city;
    @ManyToOne
    @JoinColumn(name = "user")
    private User user;
    @CreationTimestamp
    private Date createTime = new Date();

    public Location(String country, String area, String region, String city, User user) {
        this.country = country;
        this.area = area;
        this.region = region;
        this.city = city;
        this.user = user;
    }
}
