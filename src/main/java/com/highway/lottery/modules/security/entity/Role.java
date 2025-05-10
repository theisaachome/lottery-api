package com.highway.lottery.modules.security.entity;
import com.highway.lottery.common.dto.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@Setter
@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
public class Role  extends BaseEntity {


    private String roleName;
    private String description;


}
