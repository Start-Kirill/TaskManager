package by.it_academy.task_manager_common.support.spring.converters;


import by.it_academy.task_manager_common.dto.CustomPage;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;

public class CustomPageConverter<T> implements Converter<Page<T>, CustomPage<T>> {
    
    @Override
    public CustomPage<T> convert(Page<T> source) {
        CustomPage<T> objectCustomPage = new CustomPage<>();

        objectCustomPage.setTotalPages(source.getTotalPages());
        objectCustomPage.setTotalElements(source.getTotalElements());
        if (source.isFirst()) {
            objectCustomPage.setFirst(true);
        } else {
            objectCustomPage.setFirst(false);
        }

        if (source.hasContent()) {
            objectCustomPage.setNumberOfElements(source.getContent().size());
        } else {
            objectCustomPage.setNumberOfElements(0);
        }

        if (source.hasNext()) {
            objectCustomPage.setLast(false);
        } else {
            objectCustomPage.setLast(true);
        }

        objectCustomPage.setContent(source.getContent());

        return objectCustomPage;
    }
}
