package com.ddmc.autotestspringboot.request.activityExtend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostageExtend implements Serializable {

    private Long basePrice;

}
