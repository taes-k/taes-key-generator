package com.taes.key.generator.util;

import com.taes.key.generator.exception.ApiErrorCode;
import com.taes.key.generator.exception.ApiException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.math.NumberUtils;

@Log4j2
public class ObjectUtil
{
    private ObjectUtil()
    {
        throw new IllegalStateException("Utility class");
    }

    public static Long toLong(Object obj)
    {
        if (obj == null)
            return null;

        if (obj instanceof Long)
            return (Long) obj;

        if (obj instanceof Number)
            return NumberUtils.toLong(obj.toString());

        if (obj instanceof String)
            return NumberUtils.toLong((String) obj);

        log.error("toLong convert error : {} {}", obj, obj.getClass());
        throw new ApiException(ApiErrorCode.ARGUMENT_ERROR);
    }
}
