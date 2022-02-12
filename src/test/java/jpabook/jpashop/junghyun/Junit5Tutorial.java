package jpabook.jpashop.junghyun;

import static java.time.Duration.ofMillis;
import static java.time.Duration.ofSeconds;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.EnumSource.Mode.EXCLUDE;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
// 테스트 결과를 출력할 때 테스트 메소드의 언더바를 공백으로 치환
// yml 파일에서도 설정 가능
@Tag("junghyun's junit5Tutorial class")
public class Junit5Tutorial {

    @BeforeEach
    void beforeEach() {
        System.out.println("각 테스트 메소드가 실행되기 전에 실행(테스트 메소드 '개수만큼' 실행)");
    }

    @AfterEach
    void afterEach() {
        System.out.println("각 테스트 메소드가 실행된 후에 실행(테스트 메소드 '개수만큼' 실행)");
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("테스트 메소드가 실행되기 전에 실행('한번만' 실행)");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("테스트 메소드가 실행된 후에 실행('한번만' 실행)");
    }

    @Test
    private void this_test_method_will_not_be_executed() {
    }

    @Test
    @DisplayName("test name is replaced by @DisplayName")
    void test_display_name_annotation() {
    }

    @Test
    @Tag("junghyun's junit5Tutorial method")
    void removed_underbar_to_blank() {
    }

    @Test
    @Tag("junghyun's junit5Tutorial method")
    @Disabled("description")
    void disabled_test_class() {
    }

    @MemberTest
    void custom_test_tag() {
    }

    @Test
    @Timeout(value = 10, unit = MILLISECONDS)
    void timeout_test() throws InterruptedException {
        MILLISECONDS.sleep(1);
    }

    @Test
    void assertTimeout_test() {
        assertTimeout(ofMillis(10), () -> MILLISECONDS.sleep(1));
    }

    @Test
    void assertTimeoutPreemptively_test() {
        assertTimeoutPreemptively(ofMillis(2), () -> MILLISECONDS.sleep(1));
    }

    @Test
    void assertTimeout_result_test() {
        final String result = assertTimeout(ofSeconds(2), () -> {
            TimeUnit.SECONDS.sleep(1);
            return "success";
        });

        assertEquals(result, "success");
    }

    @Nested
    @DisplayName("테스트의 이름(method name과 별개)")
    class NestedTestClass {

        @Test
        void nested_test_method() {
        }
    }

    @Test
    void custom_exception_message() {
        assertEquals(2, 2, "custom exception message");
    }

    @Test
    void custom_exception_message_lazy_execution() {
        assertEquals(2, 2, () -> {
            System.out.println("lambda method is executed");
            return "custom exception message";
        });
    }

    @Test
    void assert_all_test() {
        String person = "person";
        assertAll("assertAll heading",
                () -> assertEquals("person", person),
                () -> assertEquals(6, person.length()));
    }

    @Test
    @Disabled
    void exceptionGetMessageTest() {
        String nullString = null;

        Exception exception = assertThrows(NullPointerException.class, () ->
                nullString.length());

        assertEquals("Cannot invoke \"String.length()\" because \"nullString\" is null",
                exception.getMessage());
    }

    @Test
    @EnabledOnOs(value = OS.MAC, disabledReason = "disabledReason")
    void test_in_mac() {
    }

    @Test
    @DisabledOnOs(value = OS.MAC, disabledReason = "disabledReason")
    void test_not_in_mac() {
    }

    @Test
    @EnabledForJreRange(min = JRE.JAVA_8, max = JRE.JAVA_18)
    void test_EnabledForJreRange() {

    }

    @Test
    @EnabledIfEnvironmentVariable(named = "test1", matches = "true")
    void test_EnabledIfEnvironmentVariable() {

    }

    @Test
    @EnabledIfSystemProperty(named = "test2", matches = "true")
    void test_EnabledIfSystemProperty() {

    }

    @Test
    @DisplayName("testinfo_test DisplayName")
    void testinfo_test(TestInfo testInfo) {
        assertEquals("testinfo_test DisplayName", testInfo.getDisplayName());
    }

    @Test
    @ExtendWith(CurrentLocalDateTimeParametersExtension.class)
    void currentLocalDateTimeParametersExtension_test(@CurrentLocalDateTimeParametersExtension.CurrentLocalDateTime LocalDateTime localDateTime) {
        assertEquals(LocalDateTime.now().getDayOfWeek(), localDateTime.getDayOfWeek());
    }

    @RepeatedTest(10)
    void repeated_test() {
        System.out.println("repeated_test executed");
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void valueSource_test(int intParam, TestInfo testInfo) {
        final int[] valueSourceInts = ((ValueSource) testInfo.getTestMethod().get().getDeclaredAnnotations()[1]).ints();
        assertTrue(contains(valueSourceInts, intParam));
    }

    private static boolean contains(final int[] arr, final int key) {
        return Arrays.stream(arr).anyMatch(i -> i == key);
    }

    @ParameterizedTest
    @NullSource
    @EmptySource
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n"})
    void nullAndEmptySource_test(String text) {
        assertTrue(text == null || text.trim().isEmpty());
    }

    @ParameterizedTest
    @EnumSource(ChronoUnit.class)
    void enumSource_test(TemporalUnit temporalUnit) {
        System.out.println(temporalUnit);
    }

    @ParameterizedTest
    @EnumSource(mode = EXCLUDE, names = { "ERAS", "FOREVER" })
    void enumSource_exclude_test(ChronoUnit chronoUnit) {
        System.out.println(chronoUnit);
    }

}
