package org.ramer.demo.domain;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Topic{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    private String title;
    private String album;
    @Column(length = 300)
    private String url;
    @CreationTimestamp
    private Date createTime;
}
