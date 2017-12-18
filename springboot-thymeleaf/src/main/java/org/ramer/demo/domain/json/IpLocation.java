package org.ramer.demo.domain.json;

import lombok.Data;
import org.ramer.demo.domain.Location;

@Data
public class IpLocation{
    private int code;
    private Location data;
}
