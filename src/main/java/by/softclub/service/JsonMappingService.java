package by.softclub.service;

import by.softclub.model.Wrapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;
import java.util.Map;

public interface JsonMappingService<V> {

    Map<String, List<V>> getValidations(String path, TypeReference<Wrapper<V>> typeReference, String type);
}
