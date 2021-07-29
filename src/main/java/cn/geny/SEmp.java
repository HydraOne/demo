package cn.geny;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * S_EMP
 *
 * @author
 */
@Data
@AllArgsConstructor
public class SEmp implements Serializable {
    private Integer id;

    private String lastName;

    private String firstName;

    private String userid;

    private Date startDate;

    private String comments;

    private Integer managerId;
    private String title;

    private Integer deptId;

    private BigDecimal salary;

    private BigDecimal commissionPct;

    private static final long serialVersionUID = 1L;
}