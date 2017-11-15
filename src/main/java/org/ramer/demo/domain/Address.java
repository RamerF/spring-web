package org.ramer.demo.domain;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address{
    private Integer id;
    private String location;

    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;

        Address address = (Address) o;

        if (id != null ? !id.equals(address.id) : address.id != null)
            return false;
        return location != null ? location.equals(address.location) : address.location == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Address{" + "id=" + id + ", location='" + location + '\'' + '}';
    }
}
