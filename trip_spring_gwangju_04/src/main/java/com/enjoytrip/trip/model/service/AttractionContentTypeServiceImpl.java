//package com.enjoytrip.trip.model.service;
//
//import java.util.List;
//
//import com.enjoytrip.trip.model.AttractionContentTypeDto;
//import com.enjoytrip.trip.model.dao.AttractionContentTypeDaoImpl;
//
//public class AttractionContentTypeServiceImpl implements AttractionContentTypeService {
//
//    // Single pattern 적용
//    private static AttractionContentTypeService attractionContentTypeService = new AttractionContentTypeServiceImpl();
//    
//    private AttractionContentTypeServiceImpl() { }
//    
//    public static AttractionContentTypeService getAttractionContentTypeService() {
//        
//        if(attractionContentTypeService == null) attractionContentTypeService = new AttractionContentTypeServiceImpl();
//        
//        return attractionContentTypeService;
//    }
//    
//    // 입력한 지역(시군구)에 해당하는 관광지 정보 조회
//    @Override
//    public List<AttractionContentTypeDto> searchAttractionBySigunguContentTypeName
//        (String sidoName, String gugunName, String contentType) {
//        
//        return  AttractionContentTypeDaoImpl.getAttractionContentTypeDao()
//                .searchAttractionBySigunguContentTypeName(sidoName, gugunName, contentType);
//    }
//}
//
