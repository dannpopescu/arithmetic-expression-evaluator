package expressionevaluator.expression;

import expressionevaluator.tokens.Tokenizer;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;

class StringConverter implements ArgumentConverter {
    public Object convert(Object var1, ParameterContext var2) throws ArgumentConversionException {
        return Tokenizer.tokenize((String) var1);
    }
}
