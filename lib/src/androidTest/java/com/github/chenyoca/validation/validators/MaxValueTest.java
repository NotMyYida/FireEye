package com.github.chenyoca.validation.validators;

import com.github.chenyoca.validation.Type;
import com.github.chenyoca.validation.supports.AbstractValidator;

/**
 * Created by YooJia.Chen
 * YooJia.Chen@gmail.com
 * 2014-07-21
 */
public class MaxValueTest extends GroupTester {

    @Override
    protected AbstractValidator validator() {
        return new MaxValueValidator(Type.MaxValue, null);
    }

    @Override
    protected long[] longValues() {
        return new long[]{20};
    }

    @Override
    protected String[] thisShouldAllAssertTrue() {
        return new String[]{
                "-30",
                "-20",
                "-19",
                "-1",
                "0",
                "5",
                "10",
                "19",
                "20",
        };
    }

    @Override
    protected String[] thisShouldAllAssertFalse() {
        return new String[]{
                "20.1",
                "20.2",
                "21",
                "29",
                "29999",
        };
    }
}
