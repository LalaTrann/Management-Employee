package com.example.demo4.utils;

import com.example.demo4.request.requests.CreateRequestLeaveType;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DateConvert {


    public static Double calculateDayOff(CreateRequestLeaveType createRequestLeaveType){

        LocalDate dateStart = LocalDate.parse(createRequestLeaveType.getDayStart());
        LocalDate dateFinnish =LocalDate.parse(createRequestLeaveType.getDayEnd());

        Double result = Double.valueOf(ChronoUnit.DAYS.between(dateStart, dateFinnish));

        if (createRequestLeaveType.getShiftStart().equals(createRequestLeaveType.getShiftEnd())){

            return result + 0.5;

        }
        return result + 1;
    }

}
