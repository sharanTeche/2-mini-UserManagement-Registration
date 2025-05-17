package com.sun.service;


import com.sun.dto.QuoteApiResponceDto;
import com.sun.dto.ResetPwdForm;
import com.sun.dto.UserDto;

import java.util.Map;

public interface UserService {

    public boolean register(UserDto userDto);

    public Map<Integer, String> getAllCountry();

    public Map<Integer, String> getStateByState(Integer countryId);
    public Map<Integer, String> getCitiesByState(Integer stateId);

    public UserDto login(String email, String pwd);

    public boolean resetPwd(ResetPwdForm formObj);

    public QuoteApiResponceDto getQuote();

}
