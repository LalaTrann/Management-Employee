package com.example.demo4.converter;

import com.example.demo4.contant.RequestStatus;
import com.example.demo4.contant.RequestType;

import com.example.demo4.domain.Request;
import com.example.demo4.request.requests.CreateRequestDelayType;
import com.example.demo4.request.requests.CreateRequestLeaveType;
import com.example.demo4.response.request.ListRequestResponse;
import com.example.demo4.response.request.RequestResponse;
import com.example.demo4.utils.DateConvert;


public class RequestConverter {
    public static Request fromDelayResponse(String userId , CreateRequestDelayType createRequestDelayType){
        return Request.builder()
                .requestStatus(RequestStatus.PENDING)
                .requestTitle(createRequestDelayType.getRequestDelayType().getTitle())
                .receiverEmail(createRequestDelayType.getRequiredEmail())
                .createRequestTime(String.valueOf(System.currentTimeMillis()))
                .content(createRequestDelayType.getContent())
                .dayRequest(createRequestDelayType.getDayRequest() + "-" + createRequestDelayType.getShift().getTitle())
                .timeDelay(createRequestDelayType.getTimeLateOrEarly())
                .requestType(RequestType.DELAY)
                .userId(userId)
                .build();
    }

    public static Request fromLeaveResponse(String userId, CreateRequestLeaveType requestLeaveType){
        return Request.builder()
                .requestStatus(RequestStatus.PENDING)
                .requestTitle(requestLeaveType.getRequestLeaveType().getTitle())
                .receiverEmail(requestLeaveType.getRequiredEmail())
                .createRequestTime(String.valueOf(System.currentTimeMillis()))
                .content(requestLeaveType.getContent())
                .dayRequest(requestLeaveType.getDayStart() + "-"+ requestLeaveType.getDayEnd())
                .totalDay(DateConvert.calculateDayOff(requestLeaveType))
                .requestType(RequestType.OFF)
                .userId(userId)
                .build();
    }


    public static RequestResponse toResponse(Request request){
        return RequestResponse.builder()
                .requestStatus(request.getRequestStatus())
                .requestTitle(request.getRequestTitle())
                .timeTotal(RequestConverter.getTimeNeed(request))
                .timeRequest(request.getCreateRequestTime())
                .build();
    }
    public static String getTimeNeed(Request request) {
        if (request.getRequestType().equals(RequestType.OFF)) {
            return request.getTotalDay() + "ngày";
        }
        return request.getTimeDelay();
    }
}
