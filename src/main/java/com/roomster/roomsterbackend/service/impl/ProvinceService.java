package com.roomster.roomsterbackend.service.impl;

import com.roomster.roomsterbackend.dto.ProvinceDto;
import com.roomster.roomsterbackend.dto.ProvinceDtoWithImage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProvinceService {
    public List<ProvinceDtoWithImage> setImages(List<ProvinceDto> provinceDtos) {
        List<ProvinceDtoWithImage> dtoList = new ArrayList<>();
        for (ProvinceDto item : provinceDtos) {
            ProvinceDtoWithImage provinceDtoWithImage = new ProvinceDtoWithImage();
             provinceDtoWithImage.setProvinceName(item.getProvinceName());
             provinceDtoWithImage.setTotalPosts(item.getTotalPosts());
             provinceDtoWithImage.setImage(getImageUrlForProvince(item.getProvinceName()));

             dtoList.add(provinceDtoWithImage);
        }
        return dtoList;
    }
    private String getImageUrlForProvince(String provinceName) {
        // Logic to determine the image URL based on provinceName
        switch (provinceName){
            case "Thành phố Hà Nội" -> {
                return "https://media.vietravel.com/images/News/ha-noi-1.jpg";
            }
            case "Thành Phố Hồ Chí Minh" -> {
                return "https://hnm.1cdn.vn/2023/01/06/hanoimoi.com.vn-uploads-images-lequyen-2023-01-_1-3-.jpg";
            }
            case "Tỉnh Quảng Trị" -> {
                return "https://bcp.cdnchinhphu.vn/Uploaded/nguyendieuhuong/2021_02_08/ANH3.jpg";
            }
            case "Tỉnh Cao Bằng" -> {
                return "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRGiMpkDIFRHlkvu4ivVRsyZu2AukZ36UQkBA&usqp=CAU";
            }
            case "Tỉnh Hà Giang" -> {
                return "https://media.vietravel.com/images/News/ha-noi-1.jpg";
            }
            case "Tỉnh Bắc Kạn" -> {
                return "https://hnm.1cdn.vn/2023/01/06/hanoimoi.com.vn-uploads-images-lequyen-2023-01-_1-3-.jpg";
            }
            case "Tỉnh Tuyên Quang" -> {
                return "https://bcp.cdnchinhphu.vn/Uploaded/nguyendieuhuong/2021_02_08/ANH3.jpg";
            }
            case "Tỉnh Lào Cai" -> {
                return "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRGiMpkDIFRHlkvu4ivVRsyZu2AukZ36UQkBA&usqp=CAU";
            }
            case "Tỉnh Điện Biên" ->{
                return "";
            }
            case "Tỉnh Lai Châu" ->{
                return "";
            }
            case "Tỉnh Sơn La" ->{
                return "";
            }
            case "Tỉnh Yên Bái" ->{
                return "";
            }
            case "Tỉnh Hòa Bình" ->{
                return "";
            }
            case "Tỉnh Thái Nguyên" ->{
                return "";
            }
            case "Tỉnh Lạng Sơn" ->{
                return "";
            }
            case "Tỉnh Quảng Ninh" ->{
                return "";
            }
            case "Tỉnh Bắc Giang" ->{

            }
            case "Tỉnh Phú Thọ" ->{

            }
            case "Tỉnh Vĩnh Phúc" ->{

            }
            case "Tỉnh Bắc Ning" ->{

            }
            case "Tỉnh Hải Dương" ->{

            }
            case "Thành Phố Hải Phòng" ->{

            }
            case "Tỉnh Hưng Yên" ->{

            }case "Tỉnh Thái Bình" ->{

            }
            case "Tỉnh Hà Nam" ->{

            }
            case "Tỉnh Nam Định" ->{

            }
            case "Tỉnh Ninh Bình" ->{

            }
            case "Tỉnh Thanh Hóa" ->{

            }case "Tỉnh Nghệ An" ->{

            }
            case "Tỉnh Hà Tỉnh" ->{

            }
            case "Tỉnh Quảng Bình" ->{

            }case "Tỉnh " ->{

            }


        }
        // Example: return a default URL for illustration purposes
        return "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQbUA62btBX_L6Q8oZsLIe0M8Kb9cftDaMhkWTlaKKrXRU4XB04rBK1iyv0KRJ6BenEoNA&usqp=CAU";
    }
}
