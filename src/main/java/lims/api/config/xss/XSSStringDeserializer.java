package lims.api.config.xss;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import com.nhncorp.lucy.security.xss.XssPreventer;

import java.io.IOException;

public class XSSStringDeserializer extends StringDeserializer {

    @Override
    public String deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        return XssPreventer.escape(super.deserialize(parser, context));
    }
}