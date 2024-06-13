package by.softclub.service;

import by.softclub.model.Wrapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import org.jboss.vfs.VirtualFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor
public class JsonMappingServiceImpl<V> implements JsonMappingService<V> {

    private Map<String, List<V>> validations = new HashMap<>();

    private Wrapper<V> wrapper = new Wrapper<>();

    private static String path = "/resources/";


    @Override
    public Map<String, List<V>> getValidations(String postfix, TypeReference<Wrapper<V>> typeReference, String type) {
        parseJsonToValidation(postfix, typeReference, type);
        return validations;
    }

    private void parseJsonToValidation(String postfix, TypeReference<Wrapper<V>> typeReference, String type) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        for (String fileName : resources(type, postfix)) {
            try(InputStream inputStream = getClass().getResourceAsStream(path + postfix + "/" + fileName)){
                byte[] bytes = inputStream.readAllBytes();
                String validationJsonBody = new String(bytes, StandardCharsets.UTF_8);
                wrapper = mapper.readValue(validationJsonBody, typeReference);
                validations.put(fileName.replace(".json", ""), wrapper.getDocumentValidationRule());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private List<String> resources(String type, String postfix) {
        List<String> fileNames;
        if (type.equalsIgnoreCase("1")) {
            fileNames = getResourcesForServicesSrc(postfix);
        } else {
            fileNames = getResourcesForServiceAdmSrc(postfix);
        }
        return fileNames;
    }

    private List<String> getResourcesForServicesSrc(String postfix) {
        try {
            List<VirtualFile> virtualFileList = ((VirtualFile) getClass().getClassLoader().getResources(path + postfix).nextElement().getContent()).getChildren();
            List<String> fileNames = virtualFileList.stream().map(VirtualFile::getName).collect(Collectors.toList());
            return fileNames;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> getResourcesForServiceAdmSrc(String postfix) {
        String scannerPackage = path + postfix + "/*";
        PathMatchingResourcePatternResolver scanner = new PathMatchingResourcePatternResolver();
        try {
            List<String> files = new ArrayList<>();
            Resource[] resources = scanner.getResources(scannerPackage);
            for (Resource resource : resources) {
                files.add(resource.getFilename());
            }
            return files;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}