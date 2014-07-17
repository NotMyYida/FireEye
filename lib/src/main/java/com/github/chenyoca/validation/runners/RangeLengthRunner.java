package com.github.chenyoca.validation.runners;

import com.github.chenyoca.validation.Type;

/**
 * User: chenyoca@gmail.com
 * Date: 2014-06-26
 * Length in range
 */
public class RangeLengthRunner extends TestRunner{

    public RangeLengthRunner(Type testType, String message){
        super(testType, message);
    }

    @Override
    public boolean test(String inputValue) {
        int length = inputValue.length();
        return extraInt[0] <= length && length <= extraInt[1];
    }

    @Override
    public void onAdded() {
        checkIntValues("RangeLength Test");
    }
}
