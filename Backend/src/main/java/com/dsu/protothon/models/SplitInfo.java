package com.dsu.protothon.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SplitInfo {
    private String splitGroupId;
    private String pickUp;
    private String drop;
    private Integer srNo;
}
