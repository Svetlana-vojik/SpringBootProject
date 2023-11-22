package by.teachmeskills.springbootproject;

import lombok.Getter;

@Getter
public enum RequestParamsEnum {
    USER("user"),
    USER_ID("user_id"),
    NAME("name"),
    SURNAME("surname"),
    BIRTHDAY("birthday"),
    EMAIL("email"),
    LOGIN("email"),
    PASSWORD("password"),
    CATEGORIES("categories"),
    CATEGORY("category"),
    CATEGORY_ID("category_id"),
    PRODUCTS("products"),
    PRODUCT("product"),
    INFO("info"),
    PAGE_NUMBER("pageNumber"),
    PAGE_SIZE("pageSize"),
    SELECTED_PAGE_SIZE("selectedPageSize"),
    TOTAL_PAGES("totalPages");

    private final String value;

    RequestParamsEnum(String value) {
        this.value = value;
    }
}