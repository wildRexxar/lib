package by.softclub;

import by.softclub.model.Wrapper;
import by.softclub.model.dto.IBDocumentValidationRulesDtoTest;
import by.softclub.service.JsonMappingService;
import by.softclub.service.JsonMappingServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;

public class Main {
    public static void main(String[] args) throws Exception {
        JsonMappingService jsonMappingService = new JsonMappingServiceImpl();

        var map = jsonMappingService.getValidations("mtb",  new TypeReference<Wrapper<IBDocumentValidationRulesDtoTest>>() {
        }, "2");

        System.out.println(map.toString());
    }
}