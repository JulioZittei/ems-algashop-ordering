package com.algaworks.algashop.ordering.domain.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class DocumentTest {

    @Test
    void given_validValue_shouldGenerateDocument() {
        Document document = new Document("000-00-0000");
        Assertions.assertThat(document.value()).isEqualTo("000-00-0000");
        Assertions.assertThat(document.toString()).hasToString("000-00-0000");
    }

    @Test
    void given_validValueWithSpaces_shouldGenerateDocument() {
        Document document = new Document("000-00-0000    ");
        Assertions.assertThat(document.value()).isEqualTo("000-00-0000");
        Assertions.assertThat(document.toString()).hasToString("000-00-0000");
    }

    @Test
    void given_nullValue_shouldGenerateException() {
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(()-> new Document(null));
    }

    @Test
    void given_blankValue_shouldGenerateException() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(()-> new Document(""));
    }
}