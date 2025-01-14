package com.tuber.profile.service.domain.helper;

import com.tuber.profile.service.domain.constant.ProfileResponseCode;
import com.tuber.profile.service.domain.exception.ProfileDomainException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.util.List;

@Slf4j
public class CommonHelper {
    public List<String> getProvincesList() {
        return List.of(
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
    }

    public void checkCityExists(String city) {
        List<String> provinces = getProvincesList();
        if (!provinces.contains(city)) {
            log.warn("Could not find city with name: {}", city);
            throw new ProfileDomainException(ProfileResponseCode.CITY_NOT_FOUND, HttpStatus.NOT_FOUND.value(), city);
        }
    }
}
