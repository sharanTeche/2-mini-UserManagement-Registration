package com.sun.service.impl;

import com.sun.dto.QuoteApiResponceDto;
import com.sun.dto.ResetPwdForm;
import com.sun.dto.UserDto;
import com.sun.email.EmailServcie;
import com.sun.entity.City;
import com.sun.entity.Country;
import com.sun.entity.State;
import com.sun.entity.User;
import com.sun.repo.CityRepository;
import com.sun.repo.CountryRepository;
import com.sun.repo.StateRepository;
import com.sun.repo.UserRepository;
import com.sun.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private EmailServcie emailService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    StateRepository stateRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    private ModelMapper modelMapper;

    public UserServiceImpl(EmailServcie emailService, UserRepository userRepository,
                           CountryRepository countryRepository,
                           StateRepository stateRepository, CityRepository cityRepository,
                           ModelMapper modelMapper) {
        this.emailService = emailService;
        this.userRepository = userRepository;
        this.countryRepository = countryRepository;
        this.stateRepository = stateRepository;
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean register(UserDto userDto) {

        Country country = countryRepository.findById(userDto.getCountryId()).orElseThrow();
        State state = stateRepository.findById(userDto.getStateId()).orElseThrow();
        City city = cityRepository.findById(userDto.getCityId()).orElseThrow();
        userDto.setPwd(generatePassword());
        userDto.setPwdUpdated("NO");
        User entity = mapToEntity(userDto);
        entity.setCountry(country.getCountryName());
        entity.setState(state.getStateName());
        entity.setCity(city.getCityName());
        User savedEntity = userRepository.save(entity);// Save user in DB
        emailService.sendRegistrationEmail(savedEntity.getEmail(), savedEntity.getUname());  // Send email
        return true;
    }

    public Map<Integer, String> getCitiesByState(Integer stateId) {

        Map<Integer, String> cityMap =  new HashMap<>();
        List<City> cityies =  cityRepository.findByStateStateId(stateId);  // Query based on stateName

        for(City city : cityies) {

            cityMap.put(city.getCityId(), city.getCityName());
        }
        return cityMap;
    }

    public Map<Integer, String> getAllCountry() {
        Map<Integer, String> countryMap =  new HashMap<>();
        List<Country> list = countryRepository.findAll();
        for(Country country : list) {

            countryMap.put(country.getCountryId(), country.getCountryName());
        }
        return countryMap;

    }

    @Override
    public Map<Integer, String> getStateByState(Integer countryId) {
        Map<Integer, String> stateMap =  new HashMap<>();
        List<State> list = stateRepository.findByCountryCountryId(countryId);
        for(State state : list) {

            stateMap.put(state.getStateId(), state.getStateName());
        }
        return stateMap;
    }


        public static String generatePassword() {
            String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
            int length = 10;  // Fixed length
            StringBuilder password = new StringBuilder();
            Random rand = new Random();

            for (int i = 0; i < length; i++) {
                int index = rand.nextInt(chars.length());
                password.append(chars.charAt(index));
            }

            return password.toString();
        }


    @Override
    public UserDto login(String email, String pwd) {
        User byEmailAndPwd = userRepository.findByEmailAndPwd(email, pwd);
        return mapToDTO(byEmailAndPwd);
    }

    @Override
    public boolean resetPwd(ResetPwdForm formObj) {
        Optional<User> findById = userRepository.findById(formObj.getUserId());
        if (findById.isPresent()) {
            User user = findById.get();
            user.setPwd(formObj.getNewPwd());
            user.setPwdUpdated("YES");
            userRepository.save(user);
            return true;
        }
        return false;

    }

    @Override
    public QuoteApiResponceDto getQuote() {

        String url = "https://dummyjson.com/quotes/random";

        RestTemplate rt = new RestTemplate();
        ResponseEntity<QuoteApiResponceDto> response = rt.getForEntity(url, QuoteApiResponceDto.class);

        return response.getBody();
    }

    private UserDto mapToDTO(User user) {
        UserDto dto = modelMapper.map(user, UserDto.class);
        return dto;
    }


    private User mapToEntity(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        return user;
    }

}
