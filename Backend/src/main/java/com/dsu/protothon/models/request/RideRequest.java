package com.dsu.protothon.model.request;

import com.codewarrior.travenjo.model.RideServiceProviderContext;
import com.codewarrior.travenjo.model.RideType;
import com.codewarrior.travenjo.model.SplitInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RideRequest {
    private int riderId;
    private String from;
    private String to;

    public RideServiceProviderContext toRideServiceProviderContext(SplitInfo splitInfo) {
        RideType rideType = null;
        SplitInfo newSplitInfo = null;
        if(splitInfo != null) {
            newSplitInfo = SplitInfo
                    .builder()
                    .splitGroupId(splitInfo.getSplitGroupId())
                    .srNo(splitInfo.getSrNo() + 1)
                    .pickUp(this.from)
                    .drop(this.to).build();
            rideType = RideType.SPLIT;
        }
        return RideServiceProviderContext.builder()
                .from(this.from)
                .to(this.to)
                .riderId(this.riderId)
                .splitInfo(newSplitInfo)
                .rideType(rideType)
                .build();
    }
}
