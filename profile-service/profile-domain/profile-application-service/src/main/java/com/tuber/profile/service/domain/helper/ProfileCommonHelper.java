package com.tuber.profile.service.domain.helper;

import com.tuber.profile.service.domain.constant.ProfileResponseCode;
import com.tuber.profile.service.domain.entity.UserProfile;
import com.tuber.profile.service.domain.exception.ProfileDomainException;
import com.tuber.profile.service.domain.ports.output.repository.UserProfileRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProfileCommonHelper {
    List<String> DUMMY_PROVINCES_LIST =  List.of(
        "Hà Nội",
        "Hồ Chí Minh",
        "Đà Nẵng",
        "Hải Phòng",
        "Cần Thơ",
        "Huế",
        "Nha Trang",
        "Vũng Tàu",
        "Biên Hòa",
        "Buôn Ma Thuột",
        "Đà Lạt",
        "Quy Nhơn",
        "Hạ Long",
        "Thanh Hóa",
        "Vinh"
    );

    UserProfileRepository userProfileRepository;

    public List<String> getProvincesList() {
        //TODO: Get provinces list from api or database instead
        return DUMMY_PROVINCES_LIST;
    }

    public void checkCityExists(String city) {
        List<String> provinces = getProvincesList();
        if (!provinces.contains(city)) {
            log.warn("Could not find city with name: {}", city);
            throw new ProfileDomainException(ProfileResponseCode.CITY_NOT_FOUND, HttpStatus.NOT_FOUND.value(), city);
        }
    }

    public void verifyIfUserIdExisted(UserProfile userProfile) {
        Optional<UserProfile> savedUserProfile = userProfileRepository.findByUserId(userProfile.getUserId());
        if(savedUserProfile.isPresent()) {
            log.warn("User profile with user id: {} is already existed!", userProfile.getUserId());
            throw new ProfileDomainException(ProfileResponseCode.USER_ID_ALREADY_EXISTS, HttpStatus.BAD_REQUEST.value(), userProfile.getUserId());
        }
    }
}
