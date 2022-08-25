package com.example.demo4.service.Impl;

import com.example.demo4.contant.RequestStatus;
import com.example.demo4.converter.RequestConverter;
import com.example.demo4.domain.Request;
import com.example.demo4.domain.User;
import com.example.demo4.exception.CustomException;
import com.example.demo4.repository.RequestRepository;
import com.example.demo4.repository.UserRepository;
import com.example.demo4.request.requests.CreateRequestDelayType;
import com.example.demo4.request.requests.CreateRequestLeaveType;
import com.example.demo4.response.ObjectResponse;
import com.example.demo4.response.request.ListRequestResponse;
import com.example.demo4.response.request.RequestByEmailResponse;
import com.example.demo4.response.request.RequestResponse;
import com.example.demo4.service.JwtService;
import com.example.demo4.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RequestServiceImpl implements RequestService {


    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RequestRepository requestRepository;


    @Override
    public RequestResponse insertLateEarlyRequest(String token, CreateRequestDelayType createRequestDelayType) {
        String userId = jwtService.parseTokenToId(token);

        boolean checkEmailUser = userRepository.existsUserByEmail(createRequestDelayType.getRequiredEmail());

        if (!checkEmailUser) {
            throw new CustomException(ObjectResponse.STATUS_CODE_NOT_FOUND, ObjectResponse.MESSAGE_USER_NOT_FOUND);
        }
        Request requestInsertDelay = RequestConverter.fromDelayResponse(userId, createRequestDelayType);
        Request insert = requestRepository.insert(requestInsertDelay);
        RequestResponse response = RequestConverter.toResponse(insert);
        return response;
    }

    @Override
    public RequestResponse insertLeaveRequest(String token, CreateRequestLeaveType requestLeaveType) {
        String userId = jwtService.parseTokenToId(token);

        boolean checkEmailUser = userRepository.existsUserByEmail(requestLeaveType.getRequiredEmail());
        if (!checkEmailUser) {
            throw new CustomException(ObjectResponse.STATUS_CODE_NOT_FOUND, ObjectResponse.MESSAGE_USER_NOT_FOUND);
        }

        Request requestInsertDayOff = RequestConverter.fromLeaveResponse(userId, requestLeaveType);
        Request insert = requestRepository.insert(requestInsertDayOff);
        RequestResponse response = RequestConverter.toResponse(insert);
        return response;


    }

    @Override
    public List<ListRequestResponse> findListByUserId(String token) {
        String userId = jwtService.parseTokenToId(token);
        List<ListRequestResponse> responses = (List<ListRequestResponse>) requestRepository.findRequestsByUserIdOrderByCreateRequestTimeDesc(userId)
                .stream()
                .map(request -> {
                    User user = userRepository.findUsersById(request.getUserId());
                    return ListRequestResponse.builder()
                            .requestTitle(request.getRequestTitle())
                            .content(request.getContent())
                            .requestStatus(request.getRequestStatus())
                            .timeRequest(RequestConverter.getTimeNeed(request))
                            .userName(user.getUsername())
                            .build();
                })
                .collect(Collectors.toList());
        return responses;
    }
    @Override
    public List<RequestByEmailResponse> findListRequestByReceiverEmail(String token) {
        String id = jwtService.parseTokenToId(token);
        Optional<User> optionalUser= userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new CustomException(ObjectResponse.STATUS_CODE_NOT_FOUND,ObjectResponse.MESSAGE_USER_NOT_FOUND +id);
        }
        User user = optionalUser.get();
        List<RequestByEmailResponse> collect = requestRepository.findRequestsByReceiverEmailAndRequestStatus(user.getEmail(),RequestStatus.PENDING)
                .stream()
                .map(request -> {
                    User requestCreate = userRepository.findUsersById(request.getUserId());
                    return RequestByEmailResponse.builder()
                            .id(request.getId())
                            .requestStatus(request.getRequestStatus())
                            .requestTitle(request.getRequestTitle())
                            .timeRequest(request.getDayRequest())
                            .content(request.getContent())
                            .userName(requestCreate.getUsername())
                            .timeTotal(RequestConverter.getTimeNeed(request))
                            .build();
                })
                .collect(Collectors.toList());
        return collect;
    }

    @Override
    public RequestResponse approveRequest(String requestId, RequestStatus requestStatus, String token) {
       String email = jwtService.parseTokenToEmail(token);
        Optional<Request> optionalRequest = requestRepository.findRequestsByIdAndReceiverEmail(requestId, email);
        if (optionalRequest.isEmpty()){
           throw new CustomException(ObjectResponse.STATUS_CODE_NOT_FOUND,ObjectResponse.MESSAGE_USER_NOT_FOUND+requestId);

       }
       Request request = optionalRequest.get();
       request.setRequestStatus(requestStatus);
       Request save = requestRepository.save(request);
      RequestResponse response = RequestConverter.toResponse(save);
       return response;

    }
}