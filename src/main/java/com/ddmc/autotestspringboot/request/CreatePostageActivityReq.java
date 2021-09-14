package com.ddmc.autotestspringboot.request;

import com.ddmc.autotestspringboot.request.activityExtend.PostageExtend;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostageActivityReq extends CreateActivityReq{

    private PostageExtend extend;
}
