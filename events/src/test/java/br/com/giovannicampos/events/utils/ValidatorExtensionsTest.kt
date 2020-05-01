package br.com.giovannicampos.events.utils

import org.junit.Assert
import org.junit.Test

class ValidatorExtensionsTest {

    @Test
    fun `Is valid name, when it is passed a valid name, then returns true`() {
        // ARRANGE
        val expectedName = "John Doe"

        // ACT
        val isValid = expectedName.isValidName()

        // ASSERT
        Assert.assertTrue(isValid)
    }

    @Test
    fun `Is valid name, when it is passed an empty name, then returns false`() {
        // ARRANGE
        val expectedName = ""

        // ACT
        val isValid = expectedName.isValidName()

        // ASSERT
        Assert.assertFalse(isValid)
    }

    @Test
    fun `Is valid name, when it is passed an name with two characters, then returns false`() {
        // ARRANGE
        val expectedName = "Hi"

        // ACT
        val isValid = expectedName.isValidName()

        // ASSERT
        Assert.assertFalse(isValid)
    }

    @Test
    fun `Is valid email, when it is passed a valid e-mail, then returns true`() {
        // ARRANGE
        val expectedEmail = "test@gmail.com"

        // ACT
        val isValid = expectedEmail.isValidEmail()

        // ASSERT
        Assert.assertTrue(isValid)
    }

    @Test
    fun `Is valid email, when it is passed an empty e-mail, then returns false`() {
        // ARRANGE
        val expectedEmail = ""

        // ACT
        val isValid = expectedEmail.isValidEmail()

        // ASSERT
        Assert.assertFalse(isValid)
    }

    @Test
    fun `Is valid email, when it is passed a invalid e-mail, then returns false`() {
        // ARRANGE
        val expectedEmail = "invalidMail"

        // ACT
        val isValid = expectedEmail.isValidEmail()

        // ASSERT
        Assert.assertFalse(isValid)
    }
}