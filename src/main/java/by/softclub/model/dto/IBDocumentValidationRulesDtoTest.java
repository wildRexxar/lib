package by.softclub.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IBDocumentValidationRulesDtoTest {

    private Long validationOrder;
    private String fieldName;
    private String regexp;
    private Long maxLength;
    private Long minLength;
    private String maxValue;
    private String minValue;
    private String valueType;
    private Boolean required;
    private Boolean enabled;
    private Boolean readonly;
    private Boolean onlyUI;
    private Long docType;
    private String fieldNameRus;
    private String permittedCharacterSet;
    private List<IBValidationMessageDtoTest> ibValidationMessageList;
    private List<IBValidationPredicateDtoTest> ibValidationPredicateList;
    private List<IBComplexValidationDtoTest> ibComplexValidationList;
    private List<IBValidationFieldNameLangDtoTest> ibValidationFieldNameLangList;
    private Boolean absent;
    private Boolean hideOnUi;
    private String defaultValue;
}