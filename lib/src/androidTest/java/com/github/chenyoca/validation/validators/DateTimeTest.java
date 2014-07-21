package com.github.chenyoca.validation.validators;

import com.github.chenyoca.validation.Type;
import com.github.chenyoca.validation.supports.AbstractValidator;

/**
 * Created by YooJia.Chen
 * YooJia.Chen@gmail.com
 * 2014-07-21
 */
public class DateTimeTest extends GroupTester {

    @Override
    protected AbstractValidator validator() {
        return new DateTimeValidator(Type.IsDateTime, null);
    }

    @Override
    protected String[] thisShouldAllAssertTrue() {
        return new String[]{
                "2010-01-01 00:00:00",
                "2014-1-1 0:0:0",
                "2014-1-1 1:1:0",
                "2014-1-1 1:1:0",
                "2014-1-1 1:1:1",
                "2014-12-12 12:12:12",
                "2014-11-31 24:59:59",
        };
    }

    @Override
    protected String[] thisShouldAllAssertFalse() {
        return new String[]{
                "a",
                "yyyy-MM-dd HH:mm:ss",
                "2014-07-20+21:00:00",
                "2014-07-20 25:00:00",
                "2014-07-20 24:60:00",
                "2014-07-20 24:59:60",
        };
    }
}
