package com.inschos.yunda.data.dao;

import com.inschos.yunda.data.mapper.StaffPersonMapper;
import com.inschos.yunda.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StaffPersonDao {
    @Autowired
    private StaffPersonMapper staffPersonMapper;

    public long addStaffPerson(StaffPerson staffPerson) {
        return staffPersonMapper.addStaffPerson(staffPerson);
    }

    public long findStaffPerson(StaffPerson staffPerson) {
        return staffPersonMapper.findStaffPerson(staffPerson);
    }
}