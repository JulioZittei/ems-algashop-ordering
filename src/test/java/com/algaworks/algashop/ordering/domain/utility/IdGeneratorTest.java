package com.algaworks.algashop.ordering.domain.utility;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class IdGeneratorTest {

    @Test
    void givenPrivateConstructor_shouldNotBeInstantiable() throws Exception {
        Constructor<IdGenerator> constructor = IdGenerator.class.getDeclaredConstructor();
        assertThat(Modifier.isPrivate(constructor.getModifiers())).isTrue();

        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @RepeatedTest(10)
    void givenGenerateTimeBasedUUID_shouldGenerateValidUUID() {
        UUID uuid = IdGenerator.generateTimeBasedUUID();

        assertThat(uuid).isNotNull();
        assertThat(uuid.version()).isEqualTo(7);
        assertThat(uuid.variant()).isEqualTo(2);
    }

    @RepeatedTest(10)
    void givenMultipleCalls_shouldGenerateDifferentUUIDs() {
        Set<UUID> uuids = new HashSet<>();

        for (int i = 0; i < 10; i++) {
            uuids.add(IdGenerator.generateTimeBasedUUID());
        }

        assertThat(uuids).hasSize(10);
    }

    @Test
    void testUUIDVariantIsRFC4122() {
        UUID uuid = IdGenerator.generateTimeBasedUUID();

        // RFC 4122 variant bits: 10xxxxxx
        int variant = uuid.variant();
        assertThat(variant).isEqualTo(2);
    }

    @RepeatedTest(5)
    void testGenerateTimeBasedUUIDPerformance() {
        long startTime = System.nanoTime();

        for (int i = 0; i < 1000; i++) {
            IdGenerator.generateTimeBasedUUID();
        }

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000;

        assertThat(duration).isLessThan(1000);
    }
}