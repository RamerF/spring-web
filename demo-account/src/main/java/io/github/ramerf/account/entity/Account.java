package io.github.ramerf.account.entity;

import io.github.ramerf.wind.core.entity.enums.InterEnum;
import io.github.ramerf.wind.core.entity.pojo.AbstractEntityPoJo;
import javax.persistence.Entity;
import lombok.*;

/**
 * @author Tang Xiaofeng
 * @since 2020/5/17
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity(name = "account")
public class Account extends AbstractEntityPoJo {
  private String name;
  private Gender gender;

  public enum Gender implements InterEnum {
    /**
     * 性别.
     */
    MALE(0, "男"), FEMALE(1, "女"), NONE(2, "未知");
    private final Integer value;
    private final String desc;

    Gender(final Integer value, final String desc) {
      this.value = value;
      this.desc = desc;
    }

    @Override
    public Integer value() {
      return value;
    }

    @Override
    public String desc() {
      return desc;
    }
  }
}
