package lims.api.config.xss;

import com.nhncorp.lucy.security.xss.XssPreventer;
import org.springframework.core.convert.converter.Converter;

public class XSSStringConverter implements Converter<String, String> {

    @Override
    public String convert(String source) {
        return XssPreventer.escape(source);
    }

}