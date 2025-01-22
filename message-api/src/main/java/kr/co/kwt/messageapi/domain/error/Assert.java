package kr.co.kwt.messageapi.domain.error;

import org.springframework.lang.Contract;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;
import java.util.regex.Pattern;

public class Assert {

    /**
     * Assert a boolean expression, throwing an {@code DomainException}
     * if the expression evaluates to {@code false}.
     * <p>Call {@link #isTrue} if you wish to throw an {@code DomainException}
     * on an assertion failure.
     * <pre class="code">Assert.state(id == null, "The id property must not already be initialized");</pre>
     *
     * @param expression a boolean expression
     * @param message    the exception message to use if the assertion fails
     * @throws DomainException if {@code expression} is {@code false}
     */
    @Contract("false, _ -> fail")
    public static void state(boolean expression, String message) {
        if (!expression) {
            throw new DomainException(message);
        }
    }

    /**
     * Assert a boolean expression, throwing an {@code DomainException}
     * if the expression evaluates to {@code false}.
     * <p>Call {@link #isTrue} if you wish to throw an {@code DomainException}
     * on an assertion failure.
     * <pre class="code">
     * Assert.state(entity.getId() == null,
     *     () -&gt; "ID for entity " + entity.getName() + " must not already be initialized");
     * </pre>
     *
     * @param expression      a boolean expression
     * @param messageSupplier a supplier for the exception message to use if the
     *                        assertion fails
     * @throws DomainException if {@code expression} is {@code false}
     * @since 5.0
     */
    @Contract("false, _ -> fail")
    public static void state(boolean expression, Supplier<String> messageSupplier) {
        if (!expression) {
            throw new DomainException(nullSafeGet(messageSupplier));
        }
    }

    /**
     * Assert a boolean expression, throwing an {@code DomainException}
     * if the expression evaluates to {@code false}.
     * <pre class="code">Assert.isTrue(i &gt; 0, "The value must be greater than zero");</pre>
     *
     * @param expression a boolean expression
     * @param message    the exception message to use if the assertion fails
     * @throws DomainException if {@code expression} is {@code false}
     */
    @Contract("false, _ -> fail")
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new DomainException(message);
        }
    }

    /**
     * Assert a boolean expression, throwing an {@code DomainException}
     * if the expression evaluates to {@code false}.
     * <pre class="code">
     * Assert.isTrue(i &gt; 0, () -&gt; "The value '" + i + "' must be greater than zero");
     * </pre>
     *
     * @param expression      a boolean expression
     * @param messageSupplier a supplier for the exception message to use if the
     *                        assertion fails
     * @throws DomainException if {@code expression} is {@code false}
     * @since 5.0
     */
    @Contract("false, _ -> fail")
    public static void isTrue(boolean expression, Supplier<String> messageSupplier) {
        if (!expression) {
            throw new DomainException(nullSafeGet(messageSupplier));
        }
    }

    /**
     * Assert that an object is {@code null}.
     * <pre class="code">Assert.isNull(value, "The value must be null");</pre>
     *
     * @param object  the object to check
     * @param message the exception message to use if the assertion fails
     * @throws DomainException if the object is not {@code null}
     */
    @Contract("!null, _ -> fail")
    public static void isNull(@Nullable Object object, String message) {
        if (object != null) {
            throw new DomainException(message);
        }
    }

    /**
     * Assert that an object is {@code null}.
     * <pre class="code">
     * Assert.isNull(value, () -&gt; "The value '" + value + "' must be null");
     * </pre>
     *
     * @param object          the object to check
     * @param messageSupplier a supplier for the exception message to use if the
     *                        assertion fails
     * @throws DomainException if the object is not {@code null}
     * @since 5.0
     */
    @Contract("!null, _ -> fail")
    public static void isNull(@Nullable Object object, Supplier<String> messageSupplier) {
        if (object != null) {
            throw new DomainException(nullSafeGet(messageSupplier));
        }
    }

    /**
     * Assert that an object is not {@code null}.
     * <pre class="code">Assert.notNull(clazz, "The class must not be null");</pre>
     *
     * @param object  the object to check
     * @param message the exception message to use if the assertion fails
     * @throws DomainException if the object is {@code null}
     */
    @Contract("null, _ -> fail")
    public static void notNull(@Nullable Object object, String message) {
        if (object == null) {
            throw new DomainException(message);
        }
    }

    /**
     * Assert that an object is not {@code null}.
     * <pre class="code">
     * Assert.notNull(entity.getId(),
     *     () -&gt; "ID for entity " + entity.getName() + " must not be null");
     * </pre>
     *
     * @param object          the object to check
     * @param messageSupplier a supplier for the exception message to use if the
     *                        assertion fails
     * @throws DomainException if the object is {@code null}
     * @since 5.0
     */
    @Contract("null, _ -> fail")
    public static void notNull(@Nullable Object object, Supplier<String> messageSupplier) {
        if (object == null) {
            throw new DomainException(nullSafeGet(messageSupplier));
        }
    }

    /**
     * Assert that the given String is not empty; that is,
     * it must not be {@code null} and not the empty String.
     * <pre class="code">Assert.hasLength(name, "Name must not be empty");</pre>
     *
     * @param text    the String to check
     * @param message the exception message to use if the assertion fails
     * @throws DomainException if the text is empty
     * @see StringUtils#hasLength
     */
    @Contract("null, _ -> fail")
    public static void hasLength(@Nullable String text, String message) {
        if (!StringUtils.hasLength(text)) {
            throw new DomainException(message);
        }
    }

    /**
     * Assert that the given String is not empty; that is,
     * it must not be {@code null} and not the empty String.
     * <pre class="code">
     * Assert.hasLength(account.getName(),
     *     () -&gt; "Name for account '" + account.getId() + "' must not be empty");
     * </pre>
     *
     * @param text            the String to check
     * @param messageSupplier a supplier for the exception message to use if the
     *                        assertion fails
     * @throws DomainException if the text is empty
     * @see StringUtils#hasLength
     * @since 5.0
     */
    @Contract("null, _ -> fail")
    public static void hasLength(@Nullable String text, Supplier<String> messageSupplier) {
        if (!StringUtils.hasLength(text)) {
            throw new DomainException(nullSafeGet(messageSupplier));
        }
    }

    /**
     * Assert that the given String contains valid text content; that is, it must not
     * be {@code null} and must contain at least one non-whitespace character.
     * <pre class="code">Assert.hasText(name, "'name' must not be empty");</pre>
     *
     * @param text    the String to check
     * @param message the exception message to use if the assertion fails
     * @throws DomainException if the text does not contain valid text content
     * @see StringUtils#hasText
     */
    @Contract("null, _ -> fail")
    public static void hasText(@Nullable String text, String message) {
        if (!StringUtils.hasText(text)) {
            throw new DomainException(message);
        }
    }

    /**
     * Assert that the given String contains valid text content; that is, it must not
     * be {@code null} and must contain at least one non-whitespace character.
     * <pre class="code">
     * Assert.hasText(account.getName(),
     *     () -&gt; "Name for account '" + account.getId() + "' must not be empty");
     * </pre>
     *
     * @param text            the String to check
     * @param messageSupplier a supplier for the exception message to use if the
     *                        assertion fails
     * @throws DomainException if the text does not contain valid text content
     * @see StringUtils#hasText
     * @since 5.0
     */
    @Contract("null, _ -> fail")
    public static void hasText(@Nullable String text, Supplier<String> messageSupplier) {
        if (!StringUtils.hasText(text)) {
            throw new DomainException(nullSafeGet(messageSupplier));
        }
    }

    /**
     * Assert that the given text does not contain the given substring.
     * <pre class="code">Assert.doesNotContain(name, "rod", "Name must not contain 'rod'");</pre>
     *
     * @param textToSearch the text to search
     * @param substring    the substring to find within the text
     * @param message      the exception message to use if the assertion fails
     * @throws DomainException if the text contains the substring
     */
    public static void doesNotContain(@Nullable String textToSearch, String substring, String message) {
        if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring) &&
                textToSearch.contains(substring)) {
            throw new DomainException(message);
        }
    }

    /**
     * Assert that the given text does not contain the given substring.
     * <pre class="code">
     * Assert.doesNotContain(name, forbidden, () -&gt; "Name must not contain '" + forbidden + "'");
     * </pre>
     *
     * @param textToSearch    the text to search
     * @param substring       the substring to find within the text
     * @param messageSupplier a supplier for the exception message to use if the
     *                        assertion fails
     * @throws DomainException if the text contains the substring
     * @since 5.0
     */
    public static void doesNotContain(@Nullable String textToSearch, String substring, Supplier<String> messageSupplier) {
        if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring) &&
                textToSearch.contains(substring)) {
            throw new DomainException(nullSafeGet(messageSupplier));
        }
    }

    /**
     * Assert that an array contains elements; that is, it must not be
     * {@code null} and must contain at least one element.
     * <pre class="code">Assert.notEmpty(array, "The array must contain elements");</pre>
     *
     * @param array   the array to check
     * @param message the exception message to use if the assertion fails
     * @throws DomainException if the object array is {@code null} or contains no elements
     */
    @Contract("null, _ -> fail")
    public static void notEmpty(@Nullable Object[] array, String message) {
        if (ObjectUtils.isEmpty(array)) {
            throw new DomainException(message);
        }
    }

    /**
     * Assert that an array contains elements; that is, it must not be
     * {@code null} and must contain at least one element.
     * <pre class="code">
     * Assert.notEmpty(array, () -&gt; "The " + arrayType + " array must contain elements");
     * </pre>
     *
     * @param array           the array to check
     * @param messageSupplier a supplier for the exception message to use if the
     *                        assertion fails
     * @throws DomainException if the object array is {@code null} or contains no elements
     * @since 5.0
     */
    @Contract("null, _ -> fail")
    public static void notEmpty(@Nullable Object[] array, Supplier<String> messageSupplier) {
        if (ObjectUtils.isEmpty(array)) {
            throw new DomainException(nullSafeGet(messageSupplier));
        }
    }

    /**
     * Assert that an array contains no {@code null} elements.
     * <p>Note: Does not complain if the array is empty!
     * <pre class="code">Assert.noNullElements(array, "The array must contain non-null elements");</pre>
     *
     * @param array   the array to check
     * @param message the exception message to use if the assertion fails
     * @throws DomainException if the object array contains a {@code null} element
     */
    public static void noNullElements(@Nullable Object[] array, String message) {
        if (array != null) {
            for (Object element : array) {
                if (element == null) {
                    throw new DomainException(message);
                }
            }
        }
    }

    /**
     * Assert that an array contains no {@code null} elements.
     * <p>Note: Does not complain if the array is empty!
     * <pre class="code">
     * Assert.noNullElements(array, () -&gt; "The " + arrayType + " array must contain non-null elements");
     * </pre>
     *
     * @param array           the array to check
     * @param messageSupplier a supplier for the exception message to use if the
     *                        assertion fails
     * @throws DomainException if the object array contains a {@code null} element
     * @since 5.0
     */
    public static void noNullElements(@Nullable Object[] array, Supplier<String> messageSupplier) {
        if (array != null) {
            for (Object element : array) {
                if (element == null) {
                    throw new DomainException(nullSafeGet(messageSupplier));
                }
            }
        }
    }

    /**
     * Assert that a collection contains elements; that is, it must not be
     * {@code null} and must contain at least one element.
     * <pre class="code">Assert.notEmpty(collection, "Collection must contain elements");</pre>
     *
     * @param collection the collection to check
     * @param message    the exception message to use if the assertion fails
     * @throws DomainException if the collection is {@code null} or
     *                         contains no elements
     */
    @Contract("null, _ -> fail")
    public static void notEmpty(@Nullable Collection<?> collection, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new DomainException(message);
        }
    }

    /**
     * Assert that a collection contains elements; that is, it must not be
     * {@code null} and must contain at least one element.
     * <pre class="code">
     * Assert.notEmpty(collection, () -&gt; "The " + collectionType + " collection must contain elements");
     * </pre>
     *
     * @param collection      the collection to check
     * @param messageSupplier a supplier for the exception message to use if the
     *                        assertion fails
     * @throws DomainException if the collection is {@code null} or
     *                         contains no elements
     * @since 5.0
     */
    @Contract("null, _ -> fail")
    public static void notEmpty(@Nullable Collection<?> collection, Supplier<String> messageSupplier) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new DomainException(nullSafeGet(messageSupplier));
        }
    }

    /**
     * Assert that a collection contains no {@code null} elements.
     * <p>Note: Does not complain if the collection is empty!
     * <pre class="code">Assert.noNullElements(collection, "Collection must contain non-null elements");</pre>
     *
     * @param collection the collection to check
     * @param message    the exception message to use if the assertion fails
     * @throws DomainException if the collection contains a {@code null} element
     * @since 5.2
     */
    public static void noNullElements(@Nullable Collection<?> collection, String message) {
        if (collection != null) {
            for (Object element : collection) {
                if (element == null) {
                    throw new DomainException(message);
                }
            }
        }
    }

    /**
     * Assert that a collection contains no {@code null} elements.
     * <p>Note: Does not complain if the collection is empty!
     * <pre class="code">
     * Assert.noNullElements(collection, () -&gt; "Collection " + collectionName + " must contain non-null elements");
     * </pre>
     *
     * @param collection      the collection to check
     * @param messageSupplier a supplier for the exception message to use if the
     *                        assertion fails
     * @throws DomainException if the collection contains a {@code null} element
     * @since 5.2
     */
    public static void noNullElements(@Nullable Collection<?> collection, Supplier<String> messageSupplier) {
        if (collection != null) {
            for (Object element : collection) {
                if (element == null) {
                    throw new DomainException(nullSafeGet(messageSupplier));
                }
            }
        }
    }

    /**
     * Assert that a Map contains entries; that is, it must not be {@code null}
     * and must contain at least one entry.
     * <pre class="code">Assert.notEmpty(map, "Map must contain entries");</pre>
     *
     * @param map     the map to check
     * @param message the exception message to use if the assertion fails
     * @throws DomainException if the map is {@code null} or contains no entries
     */
    @Contract("null, _ -> fail")
    public static void notEmpty(@Nullable Map<?, ?> map, String message) {
        if (CollectionUtils.isEmpty(map)) {
            throw new DomainException(message);
        }
    }

    /**
     * Assert that a Map contains entries; that is, it must not be {@code null}
     * and must contain at least one entry.
     * <pre class="code">
     * Assert.notEmpty(map, () -&gt; "The " + mapType + " map must contain entries");
     * </pre>
     *
     * @param map             the map to check
     * @param messageSupplier a supplier for the exception message to use if the
     *                        assertion fails
     * @throws DomainException if the map is {@code null} or contains no entries
     * @since 5.0
     */
    @Contract("null, _ -> fail")
    public static void notEmpty(@Nullable Map<?, ?> map, Supplier<String> messageSupplier) {
        if (CollectionUtils.isEmpty(map)) {
            throw new DomainException(nullSafeGet(messageSupplier));
        }
    }

    /**
     * Assert that the provided object is an instance of the provided class.
     * <pre class="code">Assert.instanceOf(Foo.class, foo, "Foo expected");</pre>
     *
     * @param type    the type to check against
     * @param obj     the object to check
     * @param message a message which will be prepended to provide further context.
     *                If it is empty or ends in ":" or ";" or "," or ".", a full exception message
     *                will be appended. If it ends in a space, the name of the offending object's
     *                type will be appended. In any other case, a ":" with a space and the name
     *                of the offending object's type will be appended.
     * @throws DomainException if the object is not an instance of type
     */
    @Contract("_, null, _ -> fail")
    public static void isInstanceOf(Class<?> type, @Nullable Object obj, String message) {
        notNull(type, "Type to check against must not be null");
        if (!type.isInstance(obj)) {
            instanceCheckFailed(type, obj, message);
        }
    }

    /**
     * Assert that the provided object is an instance of the provided class.
     * <pre class="code">
     * Assert.instanceOf(Foo.class, foo, () -&gt; "Processing " + Foo.class.getSimpleName() + ":");
     * </pre>
     *
     * @param type            the type to check against
     * @param obj             the object to check
     * @param messageSupplier a supplier for the exception message to use if the
     *                        assertion fails. See {@link #isInstanceOf(Class, Object, String)} for details.
     * @throws DomainException if the object is not an instance of type
     * @since 5.0
     */
    @Contract("_, null, _ -> fail")
    public static void isInstanceOf(Class<?> type, @Nullable Object obj, Supplier<String> messageSupplier) {
        notNull(type, "Type to check against must not be null");
        if (!type.isInstance(obj)) {
            instanceCheckFailed(type, obj, nullSafeGet(messageSupplier));
        }
    }

    /**
     * Assert that the provided object is an instance of the provided class.
     * <pre class="code">Assert.instanceOf(Foo.class, foo);</pre>
     *
     * @param type the type to check against
     * @param obj  the object to check
     * @throws DomainException if the object is not an instance of type
     */
    @Contract("_, null -> fail")
    public static void isInstanceOf(Class<?> type, @Nullable Object obj) {
        isInstanceOf(type, obj, "");
    }

    /**
     * Assert that {@code superType.isAssignableFrom(subType)} is {@code true}.
     * <pre class="code">Assert.isAssignable(Number.class, myClass, "Number expected");</pre>
     *
     * @param superType the supertype to check against
     * @param subType   the subtype to check
     * @param message   a message which will be prepended to provide further context.
     *                  If it is empty or ends in ":" or ";" or "," or ".", a full exception message
     *                  will be appended. If it ends in a space, the name of the offending subtype
     *                  will be appended. In any other case, a ":" with a space and the name of the
     *                  offending subtype will be appended.
     * @throws DomainException if the classes are not assignable
     */
    @Contract("_, null, _ -> fail")
    public static void isAssignable(Class<?> superType, @Nullable Class<?> subType, String message) {
        notNull(superType, "Supertype to check against must not be null");
        if (subType == null || !superType.isAssignableFrom(subType)) {
            assignableCheckFailed(superType, subType, message);
        }
    }

    /**
     * Assert that {@code superType.isAssignableFrom(subType)} is {@code true}.
     * <pre class="code">
     * Assert.isAssignable(Number.class, myClass, () -&gt; "Processing " + myAttributeName + ":");
     * </pre>
     *
     * @param superType       the supertype to check against
     * @param subType         the subtype to check
     * @param messageSupplier a supplier for the exception message to use if the
     *                        assertion fails. See {@link #isAssignable(Class, Class, String)} for details.
     * @throws DomainException if the classes are not assignable
     * @since 5.0
     */
    @Contract("_, null, _ -> fail")
    public static void isAssignable(Class<?> superType, @Nullable Class<?> subType, Supplier<String> messageSupplier) {
        notNull(superType, "Supertype to check against must not be null");
        if (subType == null || !superType.isAssignableFrom(subType)) {
            assignableCheckFailed(superType, subType, nullSafeGet(messageSupplier));
        }
    }

    /**
     * Assert that {@code superType.isAssignableFrom(subType)} is {@code true}.
     * <pre class="code">Assert.isAssignable(Number.class, myClass);</pre>
     *
     * @param superType the supertype to check
     * @param subType   the subtype to check
     * @throws DomainException if the classes are not assignable
     */
    @Contract("_, null -> fail")
    public static void isAssignable(Class<?> superType, @Nullable Class<?> subType) {
        isAssignable(superType, subType, "");
    }


    private static void instanceCheckFailed(Class<?> type, @Nullable Object obj, @Nullable String msg) {
        String className = (obj != null ? obj.getClass().getName() : "null");
        String result = "";
        boolean defaultMessage = true;
        if (StringUtils.hasLength(msg)) {
            if (endsWithSeparator(msg)) {
                result = msg + " ";
            }
            else {
                result = messageWithTypeName(msg, className);
                defaultMessage = false;
            }
        }
        if (defaultMessage) {
            result = result + ("Object of class [" + className + "] must be an instance of " + type);
        }
        throw new DomainException(result);
    }

    private static void assignableCheckFailed(Class<?> superType, @Nullable Class<?> subType, @Nullable String msg) {
        String result = "";
        boolean defaultMessage = true;
        if (StringUtils.hasLength(msg)) {
            if (endsWithSeparator(msg)) {
                result = msg + " ";
            }
            else {
                result = messageWithTypeName(msg, subType);
                defaultMessage = false;
            }
        }
        if (defaultMessage) {
            result = result + (subType + " is not assignable to " + superType);
        }
        throw new DomainException(result);
    }

    private static boolean endsWithSeparator(String msg) {
        return (msg.endsWith(":") || msg.endsWith(";") || msg.endsWith(",") || msg.endsWith("."));
    }

    private static String messageWithTypeName(String msg, @Nullable Object typeName) {
        return msg + (msg.endsWith(" ") ? "" : ": ") + typeName;
    }

    @Nullable
    private static String nullSafeGet(@Nullable Supplier<String> messageSupplier) {
        return (messageSupplier != null ? messageSupplier.get() : null);
    }

    // === Basic Assertions ===
    public static void isTrue(boolean expression, DomainException.ErrorCode errorCode, Object... args) {
        if (!expression) {
            throw new DomainException(errorCode, args);
        }
    }

    // === State Assertions ===
    public static void state(boolean expression, DomainException.ErrorCode errorCode, Object... args) {
        if (!expression) {
            throw new DomainException(errorCode, args);
        }
    }

    // === Null/NotNull Assertions ===
    public static void notNull(@Nullable Object object, DomainException.ErrorCode errorCode, Object... args) {
        if (object == null) {
            throw new DomainException(errorCode, args);
        }
    }

    public static void isNull(@Nullable Object object, DomainException.ErrorCode errorCode, Object... args) {
        if (object != null) {
            throw new DomainException(errorCode, args);
        }
    }

    // === String Assertions ===
    public static void hasText(@Nullable String text, DomainException.ErrorCode errorCode, Object... args) {
        if (text == null || text.trim().isEmpty()) {
            throw new DomainException(errorCode, args);
        }
    }

    public static void hasLength(@Nullable String text, DomainException.ErrorCode errorCode, Object... args) {
        if (text == null || text.isEmpty()) {
            throw new DomainException(errorCode, args);
        }
    }

    public static void doesNotContain(@Nullable String textToSearch, String substring, DomainException.ErrorCode errorCode, Object... args) {
        if (textToSearch != null && !textToSearch.isEmpty() &&
                textToSearch.contains(substring)) {
            throw new DomainException(errorCode, args);
        }
    }

    // === Collection/Map Assertions ===
    public static void notEmpty(@Nullable Collection<?> collection, DomainException.ErrorCode errorCode, Object... args) {
        if (collection == null || collection.isEmpty()) {
            throw new DomainException(errorCode, args);
        }
    }

    public static void notEmpty(@Nullable Map<?, ?> map, DomainException.ErrorCode errorCode, Object... args) {
        if (map == null || map.isEmpty()) {
            throw new DomainException(errorCode, args);
        }
    }

    // === Type Assertions ===
    public static void isInstanceOf(Class<?> type, @Nullable Object obj, DomainException.ErrorCode errorCode, Object... args) {
        notNull(type, errorCode, args);
        if (!type.isInstance(obj)) {
            throw new DomainException(errorCode, args);
        }
    }

    // === Domain Specific Assertions ===
    public static void isValidLength(String text, int min, int max, DomainException.ErrorCode errorCode, Object... args) {
        if (text == null || text.length() < min || text.length() > max) {
            throw new DomainException(errorCode, args);
        }
    }

    public static void matchesPattern(String text, String regex, DomainException.ErrorCode errorCode, Object... args) {
        if (!Pattern.matches(regex, text)) {
            throw new DomainException(errorCode, args);
        }
    }

    public static void isValidOptionCount(Collection<?> options, int maxCount, DomainException.ErrorCode errorCode, Object... args) {
        if (options != null && options.size() > maxCount) {
            throw new DomainException(errorCode, args);
        }
    }

    public static void isModifiableStatus(boolean isModifiable, DomainException.ErrorCode errorCode, Object... args) {
        if (!isModifiable) {
            throw new DomainException(errorCode, args);
        }
    }
}
