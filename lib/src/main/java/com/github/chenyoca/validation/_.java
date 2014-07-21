package com.github.chenyoca.validation;

import android.content.Context;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.widget.EditText;

import com.github.chenyoca.validation.supports.AbstractValidator;
import com.github.chenyoca.validation.validators.ValidatorFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YooJia.Chen
 * YooJia.Chen@gmail.com
 * 2014-07-18
 */
class _ {

    final MessageDisplay display;
    final EditText field;
    List<AbstractValidator> runners = new ArrayList<AbstractValidator>(1);

    _(MessageDisplay display, EditText field, AbstractValidator validator, Type type) {
        this.display = display;
        this.field = field;
        setValues(validator, type);
        add(validator);
    }

    TestResult performTest(){
        String value = String.valueOf(field.getText().toString());
        display.dismiss(field);
        String message;
        AbstractValidator first = runners.get(0);
        boolean required = false;
        if (first != null && Type.Required.equals(first.testType)){
            required = true;
            boolean passed = first.perform(value);
            message = first.getMessage();
            if ( ! passed){
                display.show(field, message);
                return new TestResult(false, message, null);
            }
        }else if (TextUtils.isEmpty(value)){
            return new TestResult(true, "NO_VALUE_NOT_REQUIRED", value);
        }

        final int size = runners.size();
        for (int i = required ? 1 : 0;i<size;i++){
            AbstractValidator r = runners.get(i);
            boolean passed = r.perform(value);
            message = r.getMessage();
            if ( !passed){
                display.show(field, message);
                return new TestResult(false, message, value);
            }
        }
        return new TestResult(true, "TEST_PASSED", value);
    }

    void performInputType(){
        int inputType = field.getInputType();
        for (AbstractValidator r : runners){
            switch (r.testType){
                case MobilePhone:
                case Numeric:
                case Digits:
                case MaxValue:
                case MinValue:
                case RangeValue:
                case IPv4:
                case CreditCard:
                    inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL;
                    break;
                case Email:
                    inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
                    field.setSingleLine(true);
                    break;
                case URL:
                case Host:
                    inputType = InputType.TYPE_TEXT_VARIATION_URI;
                    break;
                case MaxLength:
                case RangeLength:
                    int index = Type.MaxLength.equals(r.testType) ? 0 : 1;
                    field.setFilters(new InputFilter[]{
                            new InputFilter.LengthFilter((int)r.extraLong[index])} );
                    break;
            }
        }
        field.setInputType(inputType);
    }

    void add(Context c, Type type){
        AbstractValidator r = ValidatorFactory.build(c, type);
        setValues(r, type);
        add(r);
    }

    void add(AbstractValidator v){
        if (Type.Required.equals(v.testType)){
            runners.add(0, v);
        }else{
            runners.add(v);
        }
        v.verifyValues();
    }

    void setValues(AbstractValidator v, Type type){
        switch (type){
            case CreditCard:
            case Email:
            case Host:
            case URL:
            case IPv4:
            case MobilePhone:
            case NotBlank:
            case Numeric:
            case Required:
                //No need values
                break;
            case MaxLength:
            case MinLength:
            case RangeLength:
                //Required values
                v.setIfNeedValues(type.longValues, null, null);
                break;
            case MinValue:
            case MaxValue:
            case RangeValue:
                v.setRequiredValues(type.longValues, type.stringValues, type.floatValues);
                break;
            default:
                v.setIfNeedValues(type.longValues, type.stringValues, type.floatValues);
                break;
        }
        if (type.message != null) v.setMessage(type.message);
        if (type.valuesLoader != null) v.setValuesLoader(type.valuesLoader);
    }
}
