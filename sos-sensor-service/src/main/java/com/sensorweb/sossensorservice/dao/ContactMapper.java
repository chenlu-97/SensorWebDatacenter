package com.sensorweb.sossensorservice.dao;

import com.sensorweb.sossensorservice.entity.Contact;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ContactMapper {
    int insertData(Contact contact);

    int deleteById(int id);
    int deleteByProcedureId(String procedureId);

    Contact selectByProcedureId(String procedureId);
    List<Contact> selectByIndividualName(String individualName);
    List<Contact> selectByPositionName(String positionName);
    List<Contact> selectByOrganizationName(String organizationName);
}
