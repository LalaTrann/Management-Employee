package com.example.demo4.service;

import com.example.demo4.contant.RequestStatus;
import com.example.demo4.request.requests.CreateRequestDelayType;
import com.example.demo4.request.requests.CreateRequestLeaveType;
import com.example.demo4.response.request.ListRequestResponse;
import com.example.demo4.response.request.RequestByEmailResponse;
import com.example.demo4.response.request.RequestResponse;

import java.util.List;

public interface RequestService {
    RequestResponse insertLateEarlyRequest(String token, CreateRequestDelayType request);
    RequestResponse insertLeaveRequest(String token, CreateRequestLeaveType request);
    List<RequestByEmailResponse> findListRequestByReceiverEmail(String token);
    RequestResponse approveRequest(String requestId , RequestStatus requestStatus, String token);
    List<ListRequestResponse> findListByUserId(String token);
}
