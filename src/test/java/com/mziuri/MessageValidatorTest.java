package com.mziuri;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MessageValidatorTest {

    @Test
    public void isValidShouldReturnTrueForValidMessageWithoutBadWord() {
        MessageValidator validator = MessageValidator.getInstance();
        validator.setMessage("this is valid");
        assertTrue(validator.isValid());
    }

    @Test
    public void isValidShouldReturnFalseForMessageContainingBadWord() {
        MessageValidator validator = MessageValidator.getInstance();
        validator.setMessage("this is not valid fuck");
        assertFalse(validator.isValid());
    }

    @Test
    public void isValidShouldReturnTrueForValidMessageWithoutNewLines() {
        MessageValidator validator = MessageValidator.getInstance();
        validator.setMessage("this is valid");
        assertTrue(validator.isValid());
    }

    @Test
    public void isValidShouldReturnFalseForMessageContainingNewLines() {
        MessageValidator validator = MessageValidator.getInstance();
        validator.setMessage("this is \n not valid");
        assertFalse(validator.isValid());
    }
}